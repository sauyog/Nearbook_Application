package org.briarproject.masterproject.android.attachment.media;

import static org.briarproject.bramble.test.TestUtils.isOptionalTestEnabled;
import static org.briarproject.nullsafety.NullSafety.requireNonNull;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeTrue;
import static java.util.logging.Logger.getLogger;

import androidx.test.filters.LargeTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@LargeTest
@RunWith(Parameterized.class)
public class PngSuiteImageCompressorTest
        extends AbstractImageCompressorTest {

    private static final Logger LOG =
            getLogger(PngSuiteImageCompressorTest.class.getName());
    private final String filename;

    public PngSuiteImageCompressorTest(String filename) {
        this.filename = filename;
    }

    @Parameters
    public static Iterable<String> data() throws IOException {
        List<String> data = new ArrayList<>();
        String[] files = requireNonNull(getAssetManager().list("PngSuite"));
        for (String filename : files)
            if (filename.endsWith(".png")) data.add(filename);
        return data;
    }

    @Override
    protected void inject(AbstractImageCompressorComponent component) {
        component.inject(this);
    }

    @Test
    public void testPngSuiteCompress() throws Exception {
        assumeTrue(isOptionalTestEnabled(
                PngSuiteImageCompressorTest.class));
        LOG.info("Testing " + filename);
        if (filename.startsWith("x")) {
            try {
                testCompress("PngSuite/" + filename, "image/png");
                fail();
            } catch (IOException expected) {
                // Expected
            }
        } else {
            testCompress("PngSuite/" + filename, "image/png");
        }
    }
}
