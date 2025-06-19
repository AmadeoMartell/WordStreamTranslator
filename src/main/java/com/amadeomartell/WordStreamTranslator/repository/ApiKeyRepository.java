package com.amadeomartell.WordStreamTranslator.repository;

public interface ApiKeyRepository {
    boolean existsByKey(String apiKey);
}
