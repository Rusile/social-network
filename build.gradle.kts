plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.5.5"
    id("io.spring.dependency-management") version "1.1.7"
    id("nu.studer.jooq") version "9.0"
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
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

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
            generateSchemaSourceOnCompilation.set(false) // важно!

            jooqConfiguration.apply {
                jdbc.apply {
                    driver = "org.postgresql.Driver"
                    url = System.getenv("JDBC_URL") ?: "jdbc:postgresql://localhost:5432/social"
                    user = System.getenv("DB_USER") ?: "user"
                    password = System.getenv("DB_PASSWORD") ?: "password"
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

