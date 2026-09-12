package com.danygi.docflow.conversion.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "docflow.libreoffice")
public record LibreOfficeProperties(
        String executable
) {
}
