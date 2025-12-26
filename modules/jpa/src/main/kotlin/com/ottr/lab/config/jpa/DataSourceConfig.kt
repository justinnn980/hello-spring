package com.ottr.lab.config.jpa

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

@Configuration
class DataSourceConfig {
    @Bean
    @ConfigurationProperties(prefix = "datasource.postgresql-jpa.main")
    fun postgresqlMainHikariConfig(): HikariConfig =
        HikariConfig()

    @Primary
    @Bean
    fun postgresqlMainDataSource(@Qualifier("postgresqlMainHikariConfig") hikariConfig: HikariConfig) =
        HikariDataSource(hikariConfig)
}
