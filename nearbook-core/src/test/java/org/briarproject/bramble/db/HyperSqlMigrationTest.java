package org.briarproject.bramble.db;

import static org.briarproject.bramble.test.TestUtils.isCryptoStrengthUnlimited;
import static org.junit.Assume.assumeTrue;

import org.briarproject.nullsafety.NotNullByDefault;
import org.junit.Before;

import java.sql.Connection;
import java.util.List;

@NotNullByDefault
public class HyperSqlMigrationTest extends DatabaseMigrationTest {

    @Before
    public void setUp() {
        assumeTrue(isCryptoStrengthUnlimited());
    }

    @Override
    Database<Connection> createDatabase(
            List<Migration<Connection>> migrations) {
        return new HyperSqlDatabase(config, messageFactory, clock) {
            @Override
            List<Migration<Connection>> getMigrations() {
                return migrations;
            }
        };
    }
}
