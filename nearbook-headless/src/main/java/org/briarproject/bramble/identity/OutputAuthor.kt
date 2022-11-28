package org.briarproject.bramble.identity

import org.briarproject.bramble.api.identity.Author
import org.briarproject.briar.headless.json.JsonDict
import org.briarproject.masterproject.api.identity.AuthorInfo

fun Author.output() = JsonDict(
    "formatVersion" to formatVersion,
    "id" to id.bytes,
    "name" to name,
    "publicKey" to publicKey.encoded
)

fun AuthorInfo.Status.output() = name.toLowerCase()
