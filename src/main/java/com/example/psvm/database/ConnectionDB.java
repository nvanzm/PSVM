package com.example.psvm.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * The ConnectionDB class is responsible for managing database connections using
 * the HikariCP connection pooling library. It provides utility methods to acquire
 * and close connections to the database.
 */
public class ConnectionDB {

    private static HikariDataSource dataSource;

    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://82.165.51.200:3306/s47_psvm");
        config.setUsername("u47_WF9JyiiELC");
        config.setPassword("S!DWDylOeh8DO^EshXa2s1En");

        config.setMaximumPoolSize(10);
        config.setMinimumIdle(2);
        config.setIdleTimeout(30000);
        config.setConnectionTimeout(20000);
        config.setLeakDetectionThreshold(2000);

        dataSource = new HikariDataSource(config);
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static void closeDataSource() {
        if (dataSource != null) {
            dataSource.close();
        }
    }
}
