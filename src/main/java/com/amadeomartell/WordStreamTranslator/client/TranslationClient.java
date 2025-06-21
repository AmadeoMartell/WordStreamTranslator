package com.amadeomartell.WordStreamTranslator.client;

public interface TranslationClient {
    String translate(String word, String sourceLang, String targetLang);
}
