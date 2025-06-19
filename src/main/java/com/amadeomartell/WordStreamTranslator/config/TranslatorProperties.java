package com.amadeomartell.WordStreamTranslator.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "translator.external")
@Getter
@Setter
public class TranslatorProperties {
    private String apiUrl;
    private String apiKey;
}
