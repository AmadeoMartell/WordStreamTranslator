package com.amadeomartell.WordStreamTranslator.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TranslationItem {
    private UUID id;
    private UUID requestId;
    private int wordIndex;
    private String sourceWord;
    private String translatedWord;
}
