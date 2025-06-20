package com.amadeomartell.WordStreamTranslator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTranslationRequestDto {
    private String sourceLang;
    private String targetLang;
    private String inputText;
}
