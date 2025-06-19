package com.amadeomartell.WordStreamTranslator.repository.impl;

import com.amadeomartell.WordStreamTranslator.repository.ApiKeyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ApiKeyRepositoryImpl implements ApiKeyRepository {
    private final JdbcTemplate jdbcTemplate;
    private static final String SQL = "SELECT COUNT(1) FROM api_keys WHERE api_key = ?";

    @Override
    @Cacheable(value = "apiKeys", key = "#apiKey")
    public boolean existsByKey(String apiKey) {
        Integer count = jdbcTemplate.queryForObject(SQL, Integer.class, apiKey);
        return count != null && count > 0;
    }
}
