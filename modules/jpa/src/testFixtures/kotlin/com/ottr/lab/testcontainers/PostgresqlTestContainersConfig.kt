package com.ottr.lab.testcontainers

import org.springframework.context.annotation.Configuration
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName

@Configuration
class PostgresqlTestContainersConfig {
    companion object {
        private val postgresContainer: PostgreSQLContainer<*> =
            PostgreSQLContainer(DockerImageName.parse("postgres:16-alpine"))
                .apply {
                    withDatabaseName("ottr")
                    withUsername("test")
                    withPassword("test")
                    withExposedPorts(5432)
                    start()
                }

        init {
            val jdbcUrl = postgresContainer.let { "jdbc:postgresql://${it.host}:${it.firstMappedPort}/${it.databaseName}" }
            System.setProperty("datasource.postgresql-jpa.main.jdbc-url", jdbcUrl)
            System.setProperty("datasource.postgresql-jpa.main.username", postgresContainer.username)
            System.setProperty("datasource.postgresql-jpa.main.password", postgresContainer.password)
        }
    }
}
