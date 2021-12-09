package ru.sbt.subsidy.backend

import org.springframework.boot.test.context.SpringBootTest
import org.testcontainers.containers.GenericContainer
import org.testcontainers.spock.Testcontainers
import spock.lang.Specification
import ru.sbt.subsidy.dictionaries.SubsidyApp

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = [SubsidyApp.class])
abstract class AbstractIntegrationIT extends Specification {

    private static final int POSTGRES_PORT = 5432
    private static final String POSTGRES_USER = 'subsidy-db-owner'
    private static final String POSTGRES_PASSWORD = 'pgPassword'
    private static final String POSTGRES_CONTAINER = 'subsidy-bpm'
    private static final String POSTGRES_DB = 'subsidy-bpm'

    static {
        GenericContainer posgresContainer = new GenericContainer("library/postgres:11")
                .withNetworkAliases(POSTGRES_CONTAINER)
                .withExposedPorts(POSTGRES_PORT)
                .withEnv('POSTGRES_USER': POSTGRES_USER)
                .withEnv('POSTGRES_PASSWORD': POSTGRES_PASSWORD)
                .withEnv('POSTGRES_DB': POSTGRES_DB)
        posgresContainer.start()

        System.setProperty('spring.datasource.url', String.format('jdbc:postgresql://%s:%s/%s',
                posgresContainer.getContainerIpAddress(),
                posgresContainer.getFirstMappedPort(),
                POSTGRES_DB))
        System.setProperty("spring.datasource.username", POSTGRES_USER)
        System.setProperty("spring.datasource.password", POSTGRES_PASSWORD)
        System.setProperty("spring.datasource.driverClassName", "org.postgresql.Driver")
    }

}
