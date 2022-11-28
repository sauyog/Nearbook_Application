package org.briarproject.bramble.db;

import static org.briarproject.bramble.test.TestUtils.isCryptoStrengthUnlimited;
import static org.junit.Assume.assumeTrue;

import org.briarproject.bramble.api.db.DatabaseConfig;
import org.briarproject.bramble.api.sync.MessageFactory;
import org.briarproject.bramble.api.system.Clock;
import org.junit.Before;

public class HyperSqlDatabaseTest extends JdbcDatabaseTest {

    @Before
    public void setUp() {
        assumeTrue(isCryptoStrengthUnlimited());
    }

    @Override
    protected JdbcDatabase createDatabase(DatabaseConfig config,
                                          MessageFactory messageFactory, Clock clock) {
        return new HyperSqlDatabase(config, messageFactory, clock);
    }
}
