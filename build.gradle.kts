plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.5.5"
    id("io.spring.dependency-management") version "1.1.7"
    id("nu.studer.jooq") version "9.0"
    id("org.liquibase.gradle") version "2.2.0"
}

group = "ru.rusile"
version = "0.0.1"
description = "social-network"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.20.0")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.6.3")

    // jooq
    implementation("org.springframework.boot:spring-boot-starter-jooq")
    implementation("org.jooq:jooq:3.19.8")
    implementation("org.jooq:jooq-kotlin:3.19.8")
    runtimeOnly("org.postgresql:postgresql:42.7.3")

    // JOOQ Generator
    jooqGenerator("org.postgresql:postgresql:42.7.3")
    jooqGenerator("org.jooq:jooq-meta-extensions:3.19.8")
    jooqGenerator("org.jooq:jooq-meta:3.19.8")
    jooqGenerator("org.jooq:jooq-codegen:3.19.8")

    // JWT
    implementation("io.jsonwebtoken:jjwt-api:0.12.5")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.5")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.5")

    // Liquibase
    implementation("org.liquibase:liquibase-core")
    liquibaseRuntime("org.liquibase:liquibase-core")
    liquibaseRuntime("org.postgresql:postgresql:42.7.3")
    liquibaseRuntime("info.picocli:picocli:4.7.6")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

jooq {
    version.set("3.19.8")
    configurations {
        create("main") {
            generateSchemaSourceOnCompilation.set(false)

            jooqConfiguration.apply {
                jdbc.apply {
                    driver = "org.postgresql.Driver"
                    url = System.getenv("JDBC_URL")
                    user = System.getenv("DB_USER")
                    password = System.getenv("DB_PASSWORD")
                }

                generator.apply {
                    name = "org.jooq.codegen.KotlinGenerator"

                    database.apply {
                        name = "org.jooq.meta.postgres.PostgresDatabase"
                        inputSchema = "public"
                    }

                    target.apply {
                        packageName = "ru.rusile.socialnetwork.jooq"
                        directory = "src/main/jooq"
                    }
                }
            }
        }
    }
}

liquibase {
    activities.register("main") {
        this.arguments = mapOf(
            "logLevel" to "info",
            "changelogFile" to "src/main/resources/db/db.changelog-master.xml",
            "url" to System.getenv("JDBC_URL"),
            "username" to System.getenv("DB_USER"),
            "password" to System.getenv("DB_PASSWORD")
        )
    }
    runList = "main"
}
