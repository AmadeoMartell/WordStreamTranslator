package com.amadeomartell.WordStreamTranslator.repository;

import com.amadeomartell.WordStreamTranslator.model.TranslationRequest;

import java.util.UUID;

public interface TranslationRequestRepository {
    UUID save(TranslationRequest request);
}
