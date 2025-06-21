package com.amadeomartell.WordStreamTranslator.dto;

import lombok.Data;

import java.util.List;

@Data
public class DeeplResponseDto {
    private List<Translation> translations;

    @Data
    public static class Translation {
        private String text;
    }
}
