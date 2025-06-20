package com.amadeomartell.WordStreamTranslator.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TranslationRequest {
    private UUID id;
    private UUID apiKeyId;
    private String ipAddress;
    private String sourceLang;
    private String targetLang;
    private String inputText;
    private Instant createdAt;
}
