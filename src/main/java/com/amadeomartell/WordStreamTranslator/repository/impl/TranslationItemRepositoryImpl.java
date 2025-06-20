package com.amadeomartell.WordStreamTranslator.repository.impl;

import com.amadeomartell.WordStreamTranslator.model.TranslationItem;
import com.amadeomartell.WordStreamTranslator.repository.TranslationItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@SuppressWarnings("FieldCanBeLocal")
@Repository
@RequiredArgsConstructor
public class TranslationItemRepositoryImpl implements TranslationItemRepository {

    private final JdbcTemplate jdbcTemplate;
    private final String SAVE_ALL_SQL = """
            INSERT INTO translation_items
              (id, request_id, word_index, source_word, translated_word)
            VALUES (?, ?, ?, ?, ?)
        """;

    @Override
    public void saveAll(List<TranslationItem> items) {
        jdbcTemplate.batchUpdate(SAVE_ALL_SQL, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(@NonNull PreparedStatement ps, int i) throws SQLException {
                TranslationItem item = items.get(i);
                ps.setObject(1, item.getId() != null ? item.getId() : UUID.randomUUID());
                ps.setObject(2, item.getRequestId());
                ps.setInt   (3, item.getWordIndex());
                ps.setString(4, item.getSourceWord());
                ps.setString(5, item.getTranslatedWord());
            }
            @Override
            public int getBatchSize() {
                return items.size();
            }
        });
    }
}
