package com.amadeomartell.WordStreamTranslator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TranslationResponseDto {
    private UUID requestId;
    private String sourceLang;
    private String targetLang;
    private Instant createdAt;
    private List<TranslationItemDto> items;
}
