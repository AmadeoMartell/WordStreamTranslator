package com.amadeomartell.WordStreamTranslator.repository.impl;

import com.amadeomartell.WordStreamTranslator.model.TranslationRequest;
import com.amadeomartell.WordStreamTranslator.repository.TranslationRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.UUID;

@SuppressWarnings("FieldCanBeLocal")
@Repository
@RequiredArgsConstructor
public class TranslationRequestRepositoryImpl implements TranslationRequestRepository {

    private final JdbcTemplate jdbcTemplate;
    private final String SAVE_SQL = """
            INSERT INTO translation_requests
              (id, api_key_id, ip_address, source_lang, target_lang, input_text)
            VALUES (?, ?, ?, ?, ?, ?)
        """;
    private final String GET_TIMESTAMP_SQL = "SELECT created_at FROM translation_requests WHERE id = ?";

    @Override
    public UUID save(TranslationRequest req) {
        UUID id = UUID.randomUUID();

        jdbcTemplate.update(
                SAVE_SQL,
                id,
                req.getApiKeyId(),
                req.getIpAddress(),
                req.getSourceLang(),
                req.getTargetLang(),
                req.getInputText()
        );

        Timestamp ts = jdbcTemplate.queryForObject(
                GET_TIMESTAMP_SQL,
                Timestamp.class,
                id
        );
        req.setId(id);
        assert ts != null;
        req.setCreatedAt(ts.toInstant());
        return id;
    }
}
