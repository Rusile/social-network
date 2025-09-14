package ru.rusile.socialnetwork.configuration

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration
import ru.rusile.socialnetwork.security.JwtProperties

@Configuration
@EnableConfigurationProperties(value = [JwtProperties::class])
class ConfPropertiesConfiguration