package com.amadeomartell.WordStreamTranslator.repository.impl;

import com.amadeomartell.WordStreamTranslator.repository.ApiKeyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@SuppressWarnings("FieldCanBeLocal")
@Slf4j
public class ApiKeyRepositoryImpl implements ApiKeyRepository {
    private final JdbcTemplate jdbcTemplate;
    private static final String SQL = "SELECT COUNT(1) FROM api_keys WHERE api_key = ?";

    @Override
    @Cacheable(value = "apiKeys", key = "#apiKey")
    public boolean existsByKey(String apiKey) {
        Integer count = jdbcTemplate.queryForObject(SQL, Integer.class, apiKey);
        log.info("API key '{}' exists: {}, bool: {}", apiKey, count, count != null && count > 0);
        return count != null && count > 0;
    }
}
