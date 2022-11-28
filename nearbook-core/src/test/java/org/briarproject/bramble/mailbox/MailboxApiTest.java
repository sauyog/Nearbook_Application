package org.briarproject.bramble.mailbox;

import static org.briarproject.bramble.api.mailbox.MailboxConstants.CLIENT_SUPPORTS;
import static org.briarproject.bramble.mailbox.MailboxTestUtils.createHttpClientProvider;
import static org.briarproject.bramble.test.TestUtils.getContactId;
import static org.briarproject.bramble.test.TestUtils.getMailboxProperties;
import static org.briarproject.bramble.test.TestUtils.getRandomBytes;
import static org.briarproject.bramble.test.TestUtils.getRandomId;
import static org.briarproject.bramble.test.TestUtils.mailboxPropertiesEqual;
import static org.briarproject.bramble.test.TestUtils.readBytes;
import static org.briarproject.bramble.test.TestUtils.writeBytes;
import static org.briarproject.bramble.util.StringUtils.getRandomString;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static java.util.Collections.singletonList;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.briarproject.bramble.api.WeakSingletonProvider;
import org.briarproject.bramble.api.contact.ContactId;
import org.briarproject.bramble.api.mailbox.MailboxAuthToken;
import org.briarproject.bramble.api.mailbox.MailboxFileId;
import org.briarproject.bramble.api.mailbox.MailboxFolderId;
import org.briarproject.bramble.api.mailbox.MailboxId;
import org.briarproject.bramble.api.mailbox.MailboxProperties;
import org.briarproject.bramble.api.mailbox.MailboxVersion;
import org.briarproject.bramble.mailbox.MailboxApi.ApiException;
import org.briarproject.bramble.mailbox.MailboxApi.MailboxContact;
import org.briarproject.bramble.mailbox.MailboxApi.MailboxFile;
import org.briarproject.bramble.mailbox.MailboxApi.TolerableFailureException;
import org.briarproject.bramble.test.BrambleTestCase;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import okio.Buffer;

public class MailboxApiTest extends BrambleTestCase {

    private final WeakSingletonProvider<OkHttpClient> httpClientProvider =
            createHttpClientProvider();
    // We aren't using a real onion address, so use the given address verbatim
    private final UrlConverter urlConverter = onion -> onion;
    private final MailboxApiImpl api = new MailboxApiImpl(httpClientProvider,
            urlConverter);
    private final MailboxAuthToken token = new MailboxAuthToken(getRandomId());
    private final MailboxAuthToken token2 = new MailboxAuthToken(getRandomId());
    private final ContactId contactId = getContactId();
    private final MailboxAuthToken contactToken =
            new MailboxAuthToken(getRandomId());
    private final MailboxFolderId contactInboxId =
            new MailboxFolderId(getRandomId());
    private final MailboxFolderId contactOutboxId =
            new MailboxFolderId(getRandomId());
    private final MailboxContact mailboxContact = new MailboxContact(
            contactId, contactToken, contactInboxId, contactOutboxId);
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void testGetServerSupports() throws Exception {
        String validVersions = "[ {\"major\":1,\"minor\":0} ]";
        String validResponse = makeVersionsResponse(validVersions);
        String invalidResponse = "{\"foo\":\"bar\"}";
        String invalidVersionsResponse = makeVersionsResponse("42");
        String invalidVersionsResponse2 = makeVersionsResponse("[ 1,0 ]");
        String invalidVersionsResponse3 =
                makeVersionsResponse("[ {\"major\":1, \"minor\":-1} ]");

        MockWebServer server = new MockWebServer();
        server.enqueue(new MockResponse().setBody(validResponse));
        server.enqueue(new MockResponse().setBody(invalidResponse));
        server.enqueue(new MockResponse().setResponseCode(401));
        server.enqueue(new MockResponse().setResponseCode(500));
        server.enqueue(new MockResponse().setBody(invalidVersionsResponse));
        server.enqueue(new MockResponse().setBody(invalidVersionsResponse2));
        server.enqueue(new MockResponse().setBody(invalidVersionsResponse3));
        server.start();
        String baseUrl = getBaseUrl(server);
        List<MailboxVersion> versions = singletonList(new MailboxVersion(1, 0));
        MailboxProperties properties =
                new MailboxProperties(baseUrl, token, new ArrayList<>());
        MailboxProperties properties2 =
                new MailboxProperties(baseUrl, token2, new ArrayList<>());

        RecordedRequest request;

        // valid response with valid token
        assertEquals(versions, api.getServerSupports(properties));
        request = server.takeRequest();
        assertEquals("/versions", request.getPath());
        assertEquals("GET", request.getMethod());
        assertToken(request, token);

        // invalid response
        assertThrows(ApiException.class,
                () -> api.getServerSupports(properties));
        request = server.takeRequest();
        assertEquals("/versions", request.getPath());
        assertEquals("GET", request.getMethod());
        assertToken(request, token);

        // 401 response
        assertThrows(ApiException.class,
                () -> api.getServerSupports(properties2));
        request = server.takeRequest();
        assertEquals("/versions", request.getPath());
        assertEquals("GET", request.getMethod());
        assertToken(request, token2);

        // 500 response
        assertThrows(ApiException.class,
                () -> api.getServerSupports(properties));
        request = server.takeRequest();
        assertEquals("/versions", request.getPath());
        assertEquals("GET", request.getMethod());
        assertToken(request, token);

        // invalid non-array serverSupports response
        assertThrows(ApiException.class,
                () -> api.getServerSupports(properties));
        request = server.takeRequest();
        assertEquals("/versions", request.getPath());
        assertEquals("GET", request.getMethod());
        assertToken(request, token);

        // invalid non-object in serverSupports array response
        assertThrows(ApiException.class,
                () -> api.getServerSupports(properties));
        request = server.takeRequest();
        assertEquals("/versions", request.getPath());
        assertEquals("GET", request.getMethod());
        assertToken(request, token);

        // invalid negative minor version in serverSupports response
        assertThrows(ApiException.class,
                () -> api.getServerSupports(properties));
        request = server.takeRequest();
        assertEquals("/versions", request.getPath());
        assertEquals("GET", request.getMethod());
        assertToken(request, token);
    }

    private String makeVersionsResponse(String versions) {
        return "{\"serverSupports\":" + versions + "}";
    }

    @Test
    public void testSetup() throws Exception {
        String validVersions = "[ {\"major\":1,\"minor\":0} ]";
        String validResponse = makeSetupResponse(
                "\"" + token2 + "\"", validVersions);
        String invalidResponse = "{\"foo\":\"bar\"}";
        String invalidTokenResponse = makeSetupResponse(
                "{\"foo\":\"bar\"}", validVersions);
        String invalidTokenResponse2 = makeSetupResponse(
                "\"" + getRandomString(64) + "\"", validVersions);
        String invalidVersionsResponse = makeSetupResponse(
                "\"" + token2 + "\"", "42");
        String invalidVersionsResponse2 = makeSetupResponse(
                "\"" + token2 + "\"", "[ 1,0 ]");
        String invalidVersionsResponse3 = makeSetupResponse(
                "\"" + token2 + "\"", "[ {\"major\":1, \"minor\":-1} ]");

        MockWebServer server = new MockWebServer();
        server.enqueue(new MockResponse().setBody(validResponse));
        server.enqueue(new MockResponse());
        server.enqueue(new MockResponse().setBody(invalidResponse));
        server.enqueue(new MockResponse().setResponseCode(401));
        server.enqueue(new MockResponse().setResponseCode(500));
        server.enqueue(new MockResponse().setBody(invalidTokenResponse));
        server.enqueue(new MockResponse().setBody(invalidTokenResponse2));
        server.enqueue(new MockResponse().setBody(invalidVersionsResponse));
        server.enqueue(new MockResponse().setBody(invalidVersionsResponse2));
        server.enqueue(new MockResponse().setBody(invalidVersionsResponse3));
        server.start();
        String baseUrl = getBaseUrl(server);
        MailboxProperties properties =
                new MailboxProperties(baseUrl, token, new ArrayList<>());
        MailboxProperties properties2 =
                new MailboxProperties(baseUrl, token2, new ArrayList<>());

        // valid response with valid token
        mailboxPropertiesEqual(properties2, api.setup(properties));
        RecordedRequest request1 = server.takeRequest();
        assertEquals("/setup", request1.getPath());
        assertEquals("PUT", request1.getMethod());
        assertToken(request1, token);

        // empty body
        assertThrows(ApiException.class, () -> api.setup(properties));
        RecordedRequest request2 = server.takeRequest();
        assertEquals("/setup", request2.getPath());
        assertEquals("PUT", request2.getMethod());
        assertToken(request2, token);

        // invalid response
        assertThrows(ApiException.class, () -> api.setup(properties));
        RecordedRequest request3 = server.takeRequest();
        assertEquals("/setup", request3.getPath());
        assertEquals("PUT", request3.getMethod());
        assertToken(request3, token);

        // 401 response
        assertThrows(ApiException.class, () -> api.setup(properties2));
        RecordedRequest request4 = server.takeRequest();
        assertEquals("/setup", request4.getPath());
        assertEquals("PUT", request4.getMethod());
        assertToken(request4, token2);

        // 500 response
        assertThrows(ApiException.class, () -> api.setup(properties));
        RecordedRequest request5 = server.takeRequest();
        assertEquals("/setup", request5.getPath());
        assertEquals("PUT", request5.getMethod());
        assertToken(request5, token);

        // invalid json dict token response
        assertThrows(ApiException.class, () -> api.setup(properties));
        RecordedRequest request6 = server.takeRequest();
        assertEquals("/setup", request6.getPath());
        assertEquals("PUT", request6.getMethod());
        assertToken(request6, token);

        // invalid non-hex string token response
        assertThrows(ApiException.class, () -> api.setup(properties));
        RecordedRequest request7 = server.takeRequest();
        assertEquals("/setup", request7.getPath());
        assertEquals("PUT", request7.getMethod());
        assertToken(request7, token);

        // invalid non-array serverSupports response
        assertThrows(ApiException.class, () -> api.setup(properties));
        RecordedRequest request8 = server.takeRequest();
        assertEquals("/setup", request8.getPath());
        assertEquals("PUT", request8.getMethod());
        assertToken(request8, token);

        // invalid non-object in serverSupports array response
        assertThrows(ApiException.class, () -> api.setup(properties));
        RecordedRequest request9 = server.takeRequest();
        assertEquals("/setup", request9.getPath());
        assertEquals("PUT", request9.getMethod());
        assertToken(request9, token);

        // invalid negative minor version in serverSupports response
        assertThrows(ApiException.class, () -> api.setup(properties));
        RecordedRequest request10 = server.takeRequest();
        assertEquals("/setup", request10.getPath());
        assertEquals("PUT", request10.getMethod());
        assertToken(request10, token);
    }

    private String makeSetupResponse(String token, String versions) {
        return "{\"token\":" + token + "," +
                "\"serverSupports\":" + versions + "}";
    }

    @Test
    public void testSetupOnlyForOwner() {
        MailboxProperties properties =
                getMailboxProperties(false, CLIENT_SUPPORTS);
        assertThrows(
                IllegalArgumentException.class,
                () -> api.setup(properties)
        );
    }

    @Test
    public void testStatus() throws Exception {
        MockWebServer server = new MockWebServer();
        server.enqueue(new MockResponse());
        server.enqueue(new MockResponse().setResponseCode(401));
        server.enqueue(new MockResponse().setResponseCode(500));
        server.start();
        String baseUrl = getBaseUrl(server);
        MailboxProperties properties =
                new MailboxProperties(baseUrl, token, new ArrayList<>());
        MailboxProperties properties2 =
                new MailboxProperties(baseUrl, token2, new ArrayList<>());

        assertTrue(api.checkStatus(properties));
        RecordedRequest request1 = server.takeRequest();
        assertEquals("/status", request1.getPath());
        assertToken(request1, token);

        assertThrows(ApiException.class, () -> api.checkStatus(properties2));
        RecordedRequest request2 = server.takeRequest();
        assertEquals("/status", request2.getPath());
        assertToken(request2, token2);

        assertFalse(api.checkStatus(properties));
        RecordedRequest request3 = server.takeRequest();
        assertEquals("/status", request3.getPath());
        assertToken(request3, token);
    }

    @Test
    public void testWipe() throws Exception {
        MockWebServer server = new MockWebServer();
        server.enqueue(new MockResponse().setResponseCode(204));
        server.enqueue(new MockResponse().setResponseCode(200));
        server.enqueue(new MockResponse().setResponseCode(401));
        server.enqueue(new MockResponse().setResponseCode(500));
        server.start();
        String baseUrl = getBaseUrl(server);
        MailboxProperties properties =
                new MailboxProperties(baseUrl, token, new ArrayList<>());
        MailboxProperties properties2 =
                new MailboxProperties(baseUrl, token2, new ArrayList<>());

        api.wipeMailbox(properties);
        RecordedRequest request1 = server.takeRequest();
        assertEquals("/", request1.getPath());
        assertEquals("DELETE", request1.getMethod());
        assertToken(request1, token);

        assertThrows(ApiException.class, () -> api.wipeMailbox(properties2));
        RecordedRequest request2 = server.takeRequest();
        assertEquals("/", request2.getPath());
        assertEquals("DELETE", request2.getMethod());
        assertToken(request2, token2);

        assertThrows(ApiException.class, () -> api.wipeMailbox(properties));
        RecordedRequest request3 = server.takeRequest();
        assertEquals("/", request3.getPath());
        assertEquals("DELETE", request3.getMethod());
        assertToken(request3, token);

        assertThrows(ApiException.class, () -> api.wipeMailbox(properties));
        RecordedRequest request4 = server.takeRequest();
        assertEquals("/", request4.getPath());
        assertEquals("DELETE", request4.getMethod());
        assertToken(request4, token);
    }

    @Test
    public void testWipeOnlyForOwner() {
        MailboxProperties properties =
                getMailboxProperties(false, CLIENT_SUPPORTS);
        assertThrows(IllegalArgumentException.class, () ->
                api.wipeMailbox(properties));
    }

    @Test
    public void testAddContact() throws Exception {
        MockWebServer server = new MockWebServer();
        server.enqueue(new MockResponse());
        server.enqueue(new MockResponse().setResponseCode(401));
        server.enqueue(new MockResponse().setResponseCode(409));
        server.start();
        String baseUrl = getBaseUrl(server);
        MailboxProperties properties =
                new MailboxProperties(baseUrl, token, new ArrayList<>());

        // contact gets added as expected
        api.addContact(properties, mailboxContact);
        RecordedRequest request1 = server.takeRequest();
        assertEquals("/contacts", request1.getPath());
        assertToken(request1, token);
        String expected = "{\"contactId\":" + contactId.getInt() +
                ",\"token\":\"" + contactToken +
                "\",\"inboxId\":\"" + contactInboxId +
                "\",\"outboxId\":\"" + contactOutboxId +
                "\"}";
        assertEquals(expected, request1.getBody().readUtf8());

        // request is not successful
        assertThrows(ApiException.class, () ->
                api.addContact(properties, mailboxContact));
        RecordedRequest request2 = server.takeRequest();
        assertEquals("/contacts", request2.getPath());
        assertToken(request2, token);

        // contact already exists
        assertThrows(TolerableFailureException.class, () ->
                api.addContact(properties, mailboxContact));
        RecordedRequest request3 = server.takeRequest();
        assertEquals("/contacts", request3.getPath());
        assertToken(request3, token);
    }

    @Test
    public void testAddContactOnlyForOwner() {
        MailboxProperties properties =
                getMailboxProperties(false, CLIENT_SUPPORTS);
        assertThrows(IllegalArgumentException.class, () ->
                api.addContact(properties, mailboxContact));
    }

    @Test
    public void testDeleteContact() throws Exception {
        MockWebServer server = new MockWebServer();
        server.enqueue(new MockResponse());
        server.enqueue(new MockResponse().setResponseCode(205));
        server.enqueue(new MockResponse().setResponseCode(401));
        server.enqueue(new MockResponse().setResponseCode(404));
        server.start();
        String baseUrl = getBaseUrl(server);
        MailboxProperties properties =
                new MailboxProperties(baseUrl, token, new ArrayList<>());

        // contact gets deleted as expected
        api.deleteContact(properties, contactId);
        RecordedRequest request1 = server.takeRequest();
        assertEquals("DELETE", request1.getMethod());
        assertEquals("/contacts/" + contactId.getInt(), request1.getPath());
        assertToken(request1, token);

        // request is not returning 200
        assertThrows(ApiException.class, () ->
                api.deleteContact(properties, contactId));
        RecordedRequest request2 = server.takeRequest();
        assertEquals("DELETE", request2.getMethod());
        assertEquals("/contacts/" + contactId.getInt(), request2.getPath());
        assertToken(request2, token);

        // request is not authorized
        assertThrows(ApiException.class, () ->
                api.deleteContact(properties, contactId));
        RecordedRequest request3 = server.takeRequest();
        assertEquals("DELETE", request3.getMethod());
        assertEquals("/contacts/" + contactId.getInt(), request3.getPath());
        assertToken(request3, token);

        // tolerable 404 not found error
        assertThrows(TolerableFailureException.class,
                () -> api.deleteContact(properties, contactId));
        RecordedRequest request4 = server.takeRequest();
        assertEquals("/contacts/" + contactId.getInt(), request4.getPath());
        assertEquals("DELETE", request4.getMethod());
        assertToken(request4, token);
    }

    @Test
    public void testDeleteContactOnlyForOwner() {
        MailboxProperties properties =
                getMailboxProperties(false, CLIENT_SUPPORTS);
        assertThrows(IllegalArgumentException.class, () ->
                api.deleteContact(properties, contactId));
    }

    @Test
    public void testGetContacts() throws Exception {
        ContactId contactId2 = getContactId();
        String validResponse1 = "{\"contacts\": [" + contactId.getInt() + "] }";
        String validResponse2 = "{\"contacts\": [" + contactId.getInt() + ", " +
                contactId2.getInt() + "] }";
        String invalidResponse1 = "{\"foo\":\"bar\"}";
        String invalidResponse2 = "{\"contacts\":{\"foo\":\"bar\"}}";
        String invalidResponse3 = "{\"contacts\": [1, 2, \"foo\"] }";

        MockWebServer server = new MockWebServer();
        server.enqueue(new MockResponse().setBody(validResponse1));
        server.enqueue(new MockResponse().setBody(validResponse2));
        server.enqueue(new MockResponse());
        server.enqueue(new MockResponse().setBody(invalidResponse1));
        server.enqueue(new MockResponse().setBody(invalidResponse2));
        server.enqueue(new MockResponse().setBody(invalidResponse3));
        server.enqueue(new MockResponse().setResponseCode(401));
        server.enqueue(new MockResponse().setResponseCode(500));
        server.start();
        String baseUrl = getBaseUrl(server);
        MailboxProperties properties =
                new MailboxProperties(baseUrl, token, new ArrayList<>());

        // valid response with two contacts
        assertEquals(singletonList(contactId), api.getContacts(properties));
        RecordedRequest request1 = server.takeRequest();
        assertEquals("/contacts", request1.getPath());
        assertEquals("GET", request1.getMethod());
        assertToken(request1, token);

        // valid response with two contacts
        List<ContactId> contacts = new ArrayList<>();
        contacts.add(contactId);
        contacts.add(contactId2);
        assertEquals(contacts, api.getContacts(properties));
        RecordedRequest request2 = server.takeRequest();
        assertEquals("/contacts", request2.getPath());
        assertEquals("GET", request2.getMethod());
        assertToken(request2, token);

        // empty body
        assertThrows(ApiException.class, () -> api.getContacts(properties));
        RecordedRequest request3 = server.takeRequest();
        assertEquals("/contacts", request3.getPath());
        assertEquals("GET", request3.getMethod());
        assertToken(request3, token);

        // invalid response: no contacts key
        assertThrows(ApiException.class, () -> api.getContacts(properties));
        RecordedRequest request4 = server.takeRequest();
        assertEquals("/contacts", request4.getPath());
        assertEquals("GET", request4.getMethod());
        assertToken(request4, token);

        // invalid response: no list in contacts
        assertThrows(ApiException.class, () -> api.getContacts(properties));
        RecordedRequest request5 = server.takeRequest();
        assertEquals("/contacts", request5.getPath());
        assertEquals("GET", request5.getMethod());
        assertToken(request5, token);

        // invalid response: list with non-numbers
        assertThrows(ApiException.class, () -> api.getContacts(properties));
        RecordedRequest request6 = server.takeRequest();
        assertEquals("/contacts", request6.getPath());
        assertEquals("GET", request6.getMethod());
        assertToken(request6, token);

        // 401 not authorized
        assertThrows(ApiException.class, () -> api.getContacts(properties));
        RecordedRequest request7 = server.takeRequest();
        assertEquals("/contacts", request7.getPath());
        assertEquals("GET", request7.getMethod());
        assertToken(request7, token);

        // 500 internal server error
        assertThrows(ApiException.class, () -> api.getContacts(properties));
        RecordedRequest request8 = server.takeRequest();
        assertEquals("/contacts", request8.getPath());
        assertEquals("GET", request8.getMethod());
        assertToken(request8, token);
    }

    @Test
    public void testGetContactsOnlyForOwner() {
        MailboxProperties properties =
                getMailboxProperties(false, CLIENT_SUPPORTS);
        assertThrows(
                IllegalArgumentException.class,
                () -> api.getContacts(properties)
        );
    }

    @Test
    public void testAddFile() throws Exception {
        File file = folder.newFile();
        byte[] bytes = getRandomBytes(1337);
        writeBytes(file, bytes);

        MockWebServer server = new MockWebServer();
        server.enqueue(new MockResponse());
        server.enqueue(new MockResponse().setResponseCode(401));
        server.enqueue(new MockResponse().setResponseCode(500));
        server.start();
        String baseUrl = getBaseUrl(server);
        MailboxProperties properties =
                new MailboxProperties(baseUrl, token, new ArrayList<>());

        // file gets uploaded as expected
        api.addFile(properties, contactInboxId, file);
        RecordedRequest request1 = server.takeRequest();
        assertEquals("/files/" + contactInboxId, request1.getPath());
        assertEquals("POST", request1.getMethod());
        assertToken(request1, token);
        assertArrayEquals(bytes, request1.getBody().readByteArray());

        // request is not successful
        assertThrows(ApiException.class, () ->
                api.addFile(properties, contactInboxId, file));
        RecordedRequest request2 = server.takeRequest();
        assertEquals("/files/" + contactInboxId, request2.getPath());
        assertEquals("POST", request1.getMethod());
        assertToken(request2, token);

        // server error
        assertThrows(ApiException.class, () ->
                api.addFile(properties, contactInboxId, file));
        RecordedRequest request3 = server.takeRequest();
        assertEquals("/files/" + contactInboxId, request3.getPath());
        assertEquals("POST", request1.getMethod());
        assertToken(request3, token);
    }

    @Test
    public void testGetFiles() throws Exception {
        MailboxFile mailboxFile1 =
                new MailboxFile(new MailboxFileId(getRandomId()), 1337);
        MailboxFile mailboxFile2 =
                new MailboxFile(new MailboxFileId(getRandomId()),
                        System.currentTimeMillis());
        String fileResponse1 =
                new ObjectMapper().writeValueAsString(mailboxFile1);
        String fileResponse2 =
                new ObjectMapper().writeValueAsString(mailboxFile2);
        String validResponse1 = "{\"files\": [" + fileResponse1 + "] }";
        String validResponse2 = "{\"files\": [" + fileResponse1 + ", " +
                fileResponse2 + "] }";
        String invalidResponse1 = "{\"files\":\"bar\"}";
        String invalidResponse2 = "{\"files\":{\"foo\":\"bar\"}}";
        String invalidResponse3 = "{\"files\": [" + fileResponse1 + ", 1] }";
        String invalidResponse4 = "{\"contacts\": [ 1, 2 ] }";

        MockWebServer server = new MockWebServer();
        server.enqueue(new MockResponse().setBody(validResponse1));
        server.enqueue(new MockResponse().setBody(validResponse2));
        server.enqueue(new MockResponse());
        server.enqueue(new MockResponse().setBody(invalidResponse1));
        server.enqueue(new MockResponse().setBody(invalidResponse2));
        server.enqueue(new MockResponse().setBody(invalidResponse3));
        server.enqueue(new MockResponse().setBody(invalidResponse4));
        server.enqueue(new MockResponse().setResponseCode(401));
        server.enqueue(new MockResponse().setResponseCode(404));
        server.enqueue(new MockResponse().setResponseCode(500));
        server.start();
        String baseUrl = getBaseUrl(server);
        MailboxProperties properties =
                new MailboxProperties(baseUrl, token, new ArrayList<>());

        // valid response with one file
        List<MailboxFile> received1 = api.getFiles(properties, contactInboxId);
        assertEquals(1, received1.size());
        assertEquals(mailboxFile1.name, received1.get(0).name);
        assertEquals(mailboxFile1.time, received1.get(0).time);
        RecordedRequest request1 = server.takeRequest();
        assertEquals("/files/" + contactInboxId, request1.getPath());
        assertEquals("GET", request1.getMethod());
        assertToken(request1, token);

        // valid response with two files
        List<MailboxFile> received2 = api.getFiles(properties, contactInboxId);
        assertEquals(2, received2.size());
        assertEquals(mailboxFile1.name, received2.get(0).name);
        assertEquals(mailboxFile1.time, received2.get(0).time);
        assertEquals(mailboxFile2.name, received2.get(1).name);
        assertEquals(mailboxFile2.time, received2.get(1).time);
        RecordedRequest request2 = server.takeRequest();
        assertEquals("/files/" + contactInboxId, request1.getPath());
        assertEquals("GET", request2.getMethod());
        assertToken(request2, token);

        // empty body
        assertThrows(ApiException.class, () ->
                api.getFiles(properties, contactInboxId));
        RecordedRequest request3 = server.takeRequest();
        assertEquals("/files/" + contactInboxId, request3.getPath());
        assertEquals("GET", request3.getMethod());
        assertToken(request3, token);

        // invalid response: string instead of list
        assertThrows(ApiException.class, () ->
                api.getFiles(properties, contactInboxId));
        RecordedRequest request4 = server.takeRequest();
        assertEquals("/files/" + contactInboxId, request4.getPath());
        assertEquals("GET", request4.getMethod());
        assertToken(request4, token);

        // invalid response: object instead of list
        assertThrows(ApiException.class, () ->
                api.getFiles(properties, contactInboxId));
        RecordedRequest request5 = server.takeRequest();
        assertEquals("/files/" + contactInboxId, request5.getPath());
        assertEquals("GET", request5.getMethod());
        assertToken(request5, token);

        // invalid response: list with non-objects
        assertThrows(ApiException.class, () ->
                api.getFiles(properties, contactInboxId));
        RecordedRequest request6 = server.takeRequest();
        assertEquals("/files/" + contactInboxId, request6.getPath());
        assertEquals("GET", request6.getMethod());
        assertToken(request6, token);

        // no files key in root object
        assertThrows(ApiException.class, () ->
                api.getFiles(properties, contactInboxId));
        RecordedRequest request7 = server.takeRequest();
        assertEquals("/files/" + contactInboxId, request7.getPath());
        assertEquals("GET", request7.getMethod());
        assertToken(request7, token);

        // 401 not authorized
        assertThrows(ApiException.class, () ->
                api.getFiles(properties, contactInboxId));
        RecordedRequest request8 = server.takeRequest();
        assertEquals("/files/" + contactInboxId, request8.getPath());
        assertEquals("GET", request8.getMethod());
        assertToken(request8, token);

        // 404 not found
        assertThrows(TolerableFailureException.class, () ->
                api.getFiles(properties, contactInboxId));
        RecordedRequest request9 = server.takeRequest();
        assertEquals("/files/" + contactInboxId, request9.getPath());
        assertEquals("GET", request9.getMethod());
        assertToken(request9, token);

        // 500 internal server error
        assertThrows(ApiException.class,
                () -> api.getFiles(properties, contactInboxId));
        RecordedRequest request10 = server.takeRequest();
        assertEquals("/files/" + contactInboxId, request10.getPath());
        assertEquals("GET", request10.getMethod());
        assertToken(request10, token);
    }

    @Test
    public void testGetFile() throws Exception {
        MailboxFileId name = new MailboxFileId(getRandomId());
        File file1 = folder.newFile();
        File file2 = folder.newFile();
        File file3 = folder.newFile();
        byte[] bytes = getRandomBytes(1337);

        MockWebServer server = new MockWebServer();
        server.enqueue(new MockResponse().setBody(new Buffer().write(bytes)));
        server.enqueue(new MockResponse().setResponseCode(401));
        server.enqueue(new MockResponse().setResponseCode(500));
        server.start();
        String baseUrl = getBaseUrl(server);
        MailboxProperties properties =
                new MailboxProperties(baseUrl, token, new ArrayList<>());

        // file gets downloaded as expected
        api.getFile(properties, contactOutboxId, name, file1);
        RecordedRequest request1 = server.takeRequest();
        assertEquals("/files/" + contactOutboxId + "/" + name,
                request1.getPath());
        assertEquals("GET", request1.getMethod());
        assertToken(request1, token);
        assertArrayEquals(bytes, readBytes(file1));

        // request is not successful
        assertThrows(ApiException.class, () ->
                api.getFile(properties, contactOutboxId, name, file2));
        RecordedRequest request2 = server.takeRequest();
        assertEquals("/files/" + contactOutboxId + "/" + name,
                request2.getPath());
        assertEquals("GET", request1.getMethod());
        assertToken(request2, token);
        assertEquals(0, readBytes(file2).length);

        // server error
        assertThrows(ApiException.class, () ->
                api.getFile(properties, contactOutboxId, name, file3));
        RecordedRequest request3 = server.takeRequest();
        assertEquals("/files/" + contactOutboxId + "/" + name,
                request3.getPath());
        assertEquals("GET", request1.getMethod());
        assertToken(request3, token);
        assertEquals(0, readBytes(file3).length);
    }

    @Test
    public void testDeleteFile() throws Exception {
        MailboxFileId name = new MailboxFileId(getRandomId());

        MockWebServer server = new MockWebServer();
        server.enqueue(new MockResponse());
        server.enqueue(new MockResponse().setResponseCode(205));
        server.enqueue(new MockResponse().setResponseCode(401));
        server.enqueue(new MockResponse().setResponseCode(404));
        server.start();
        String baseUrl = getBaseUrl(server);
        MailboxProperties properties =
                new MailboxProperties(baseUrl, token, new ArrayList<>());

        // file gets deleted as expected
        api.deleteFile(properties, contactInboxId, name);
        RecordedRequest request1 = server.takeRequest();
        assertEquals("DELETE", request1.getMethod());
        assertEquals("/files/" + contactInboxId + "/" + name,
                request1.getPath());
        assertToken(request1, token);

        // request is not returning 200
        assertThrows(ApiException.class, () ->
                api.deleteFile(properties, contactInboxId, name));
        RecordedRequest request2 = server.takeRequest();
        assertEquals("DELETE", request2.getMethod());
        assertEquals("/files/" + contactInboxId + "/" + name,
                request2.getPath());
        assertToken(request2, token);

        // request is not authorized
        assertThrows(ApiException.class, () ->
                api.deleteFile(properties, contactInboxId, name));
        RecordedRequest request3 = server.takeRequest();
        assertEquals("DELETE", request3.getMethod());
        assertEquals("/files/" + contactInboxId + "/" + name,
                request3.getPath());
        assertToken(request3, token);

        // file not found is tolerable
        assertThrows(TolerableFailureException.class, () ->
                api.deleteFile(properties, contactInboxId, name));
        RecordedRequest request4 = server.takeRequest();
        assertEquals("DELETE", request4.getMethod());
        assertEquals("/files/" + contactInboxId + "/" + name,
                request4.getPath());
        assertToken(request4, token);
    }

    @Test
    public void testGetFolders() throws Exception {
        MailboxFolderId id1 = new MailboxFolderId(getRandomId());
        MailboxFolderId id2 = new MailboxFolderId(getRandomId());
        String validResponse1 = "{\"folders\": [ {\"id\": \"" + id1 + "\"} ] }";
        String validResponse2 = "{\"folders\": [ {\"id\": \"" + id1 + "\"}, " +
                "{ \"id\": \"" + id2 + "\"} ] }";
        String invalidResponse1 = "{\"folders\":\"bar\"}";
        String invalidResponse2 = "{\"folders\":{\"foo\":\"bar\"}}";
        String invalidResponse3 =
                "{\"folders\": [ {\"id\": \"" + id1 + "\", 1] }";
        String invalidResponse4 = "{\"files\": [ 1, 2 ] }";

        MockWebServer server = new MockWebServer();
        server.enqueue(new MockResponse().setBody(validResponse1));
        server.enqueue(new MockResponse().setBody(validResponse2));
        server.enqueue(new MockResponse());
        server.enqueue(new MockResponse().setBody(invalidResponse1));
        server.enqueue(new MockResponse().setBody(invalidResponse2));
        server.enqueue(new MockResponse().setBody(invalidResponse3));
        server.enqueue(new MockResponse().setBody(invalidResponse4));
        server.enqueue(new MockResponse().setResponseCode(401));
        server.enqueue(new MockResponse().setResponseCode(500));
        server.start();
        String baseUrl = getBaseUrl(server);
        MailboxProperties properties =
                new MailboxProperties(baseUrl, token, new ArrayList<>());

        // valid response with one folders
        assertEquals(singletonList(id1), api.getFolders(properties));
        RecordedRequest request1 = server.takeRequest();
        assertEquals("/folders", request1.getPath());
        assertEquals("GET", request1.getMethod());
        assertToken(request1, token);

        // valid response with two folders
        assertEquals(Arrays.asList(id1, id2), api.getFolders(properties));
        RecordedRequest request2 = server.takeRequest();
        assertEquals("/folders", request1.getPath());
        assertEquals("GET", request2.getMethod());
        assertToken(request2, token);

        // empty body
        assertThrows(ApiException.class, () -> api.getFolders(properties));
        RecordedRequest request3 = server.takeRequest();
        assertEquals("/folders", request3.getPath());
        assertEquals("GET", request3.getMethod());
        assertToken(request3, token);

        // invalid response: string instead of list
        assertThrows(ApiException.class, () -> api.getFolders(properties));
        RecordedRequest request4 = server.takeRequest();
        assertEquals("/folders", request4.getPath());
        assertEquals("GET", request4.getMethod());
        assertToken(request4, token);

        // invalid response: object instead of list
        assertThrows(ApiException.class, () -> api.getFolders(properties));
        RecordedRequest request5 = server.takeRequest();
        assertEquals("/folders", request5.getPath());
        assertEquals("GET", request5.getMethod());
        assertToken(request5, token);

        // invalid response: list with non-objects
        assertThrows(ApiException.class, () -> api.getFolders(properties));
        RecordedRequest request6 = server.takeRequest();
        assertEquals("/folders", request6.getPath());
        assertEquals("GET", request6.getMethod());
        assertToken(request6, token);

        // no folders key in root object
        assertThrows(ApiException.class, () -> api.getFolders(properties));
        RecordedRequest request7 = server.takeRequest();
        assertEquals("/folders", request7.getPath());
        assertEquals("GET", request7.getMethod());
        assertToken(request7, token);

        // 401 not authorized
        assertThrows(ApiException.class, () -> api.getFolders(properties));
        RecordedRequest request8 = server.takeRequest();
        assertEquals("/folders", request8.getPath());
        assertEquals("GET", request8.getMethod());
        assertToken(request8, token);

        // 500 internal server error
        assertThrows(ApiException.class, () -> api.getFolders(properties));
        RecordedRequest request9 = server.takeRequest();
        assertEquals("/folders", request9.getPath());
        assertEquals("GET", request9.getMethod());
        assertToken(request9, token);
    }

    @Test
    public void testGetFoldersOnlyForOwner() {
        MailboxProperties properties =
                getMailboxProperties(false, CLIENT_SUPPORTS);
        assertThrows(IllegalArgumentException.class, () ->
                api.getFolders(properties));
    }

    private String getBaseUrl(MockWebServer server) {
        String baseUrl = server.url("").toString();
        return baseUrl.substring(0, baseUrl.length() - 1);
    }

    private void assertToken(RecordedRequest request, MailboxId token) {
        assertNotNull(request.getHeader("Authorization"));
        assertEquals("Bearer " + token, request.getHeader("Authorization"));
    }

}
