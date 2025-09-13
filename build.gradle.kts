plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.5.5"
    id("io.spring.dependency-management") version "1.1.7"
    id("nu.studer.jooq") version "9.0"
    id("org.openapi.generator") version "7.8.0"
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

    // OpenAPI
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.5")
    implementation("org.springdoc:springdoc-openapi-starter-common:2.8.5")

    // OpenAPI генерация кода
    implementation("org.openapitools:openapi-generator-gradle-plugin:7.8.0")
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

openApiGenerate {
    generatorName.set("kotlin-spring")
    inputSpec.set("$projectDir/src/main/resources/api/openapi.json")
    outputDir.set("$projectDir/src/main/kotlin")
    apiPackage.set("ru.rusile.socialnetwork.api")
    modelPackage.set("ru.rusile.socialnetwork.model")
    invokerPackage.set("ru.rusile.socialnetwork.invoker")
    configOptions.set(mapOf(
        "dateLibrary" to "java8",
        "interfaceOnly" to "true",        // Только интерфейсы
        "useTags" to "true",
        "useSpringBoot3" to "true",
        "useBeanValidation" to "false",
        "skipDefaultInterface" to "true", // Не генерировать реализации по умолчанию
        "documentationProvider" to "none" // Не генерировать документацию
    ))
    globalProperties.set(mapOf(
        "apis" to "",
        "models" to "",
        "supportingFiles" to "" // Отключаем генерацию вспомогательных файлов
    ))
}

// Зависимости компиляции
tasks.compileKotlin {
    dependsOn(tasks.openApiGenerate)
}

