package com.amadeomartell.WordStreamTranslator.repository;

import com.amadeomartell.WordStreamTranslator.model.TranslationItem;

import java.util.List;

public interface TranslationItemRepository {
    void saveAll(List<TranslationItem> items);
}
