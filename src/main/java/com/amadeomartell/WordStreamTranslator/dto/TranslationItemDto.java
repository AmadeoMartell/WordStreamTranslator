package com.amadeomartell.WordStreamTranslator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TranslationItemDto {
    private int wordIndex;
    private String sourceWord;
    private String translatedWord;
}
