package com.lunatech.training.quarkus;

import org.testcontainers.containers.BindMode;
import org.testcontainers.containers.PostgreSQLContainer;
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;

import java.util.HashMap;
import java.util.Map;

public class PostgresResource implements QuarkusTestResourceLifecycleManager {
    static PostgreSQLContainer<?> db = new PostgreSQLContainer<>("postgres:13.1-alpine")
            .withDatabaseName("mydb")
            .withUsername("developer")
            .withPassword("developer")
            .withClasspathResourceMapping("import.sql",
                    "/docker-entrypoint-importdb.d/import.sql",
                    BindMode.READ_WRITE);;

    @Override
    public Map<String, String> start() {
        db.start();

        final Map<String, String> conf = new HashMap<>();
        conf.put("%test.quarkus.datasource.reactive.url", db.getJdbcUrl().replace("jdbc:", ""));
        conf.put("%test.quarkus.datasource.username", "developer");
        conf.put("%test.quarkus.datasource.password", "developer");
        conf.put("%test.quarkus.datasource.db-kind", "postgresql");
        return conf;
    }

    @Override
    public void stop() {
        db.stop();
    }
}
