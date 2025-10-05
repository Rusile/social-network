package ru.rusile.socialnetwork.configuration

import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.conf.Settings
import org.jooq.impl.DefaultConfiguration
import org.jooq.impl.DefaultDSLContext
import org.springframework.boot.autoconfigure.jooq.SpringTransactionProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.sql.DataSource

@Configuration
@EnableTransactionManagement
class DatabaseConfig(
    private val dataSource: DataSource
) {

    @Bean
    fun transactionManager(): PlatformTransactionManager {
        return DataSourceTransactionManager(dataSource)
    }

    @Bean
    fun dslContext(): DSLContext {
        return DefaultDSLContext(configuration())
    }

    @Bean
    fun configuration(): org.jooq.Configuration {
        val settings = Settings()

        return DefaultConfiguration()
            .derive(dataSource)
            .derive(SQLDialect.POSTGRES)
            .derive(transactionProvider())
            .derive(settings)
    }

    @Bean
    fun transactionProvider(): SpringTransactionProvider {
        return SpringTransactionProvider(transactionManager())
    }
}
