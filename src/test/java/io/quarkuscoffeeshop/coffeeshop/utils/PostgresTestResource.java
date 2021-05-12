package io.quarkuscoffeeshop.coffeeshop.utils;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.testcontainers.containers.BindMode;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.Collections;
import java.util.Map;

/**
 * QuarkusTestResource managing a PostgreSQL database
 * Borrowed/stolen from: https://www.morling.dev/blog/quarkus-and-testcontainers/
 */
public class PostgresTestResource implements QuarkusTestResourceLifecycleManager {

    static PostgreSQLContainer<?> db =
            new PostgreSQLContainer<>("postgres:13")
                    .withDatabaseName("coffeeshopdb")
                    .withUsername("coffeeshopuser")
                    .withPassword("redhat-21")
                    .withClasspathResourceMapping("init-postgresql.sql",
                            "/docker-entrypoint-initdb.d/init.sql",
                            BindMode.READ_ONLY);;

    @Override
    public Map<String, String> start() {
        db.start();
        return Collections.singletonMap(
                "quarkus.datasource.jdbc.url", db.getJdbcUrl()
        );
    }

    @Override
    public void stop() {
        db.stop();
    }
}
