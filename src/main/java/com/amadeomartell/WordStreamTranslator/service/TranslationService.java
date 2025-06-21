package com.amadeomartell.WordStreamTranslator.service;

import com.amadeomartell.WordStreamTranslator.client.TranslationClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.*;

@Service
@RequiredArgsConstructor
public class TranslationService {

    private final TranslationClient translationClient;

    private final ExecutorService executorService;

    public List<String> translate(List<String> words, String sourceLang, String targetLang) {
        List<Future<String>> futures = words.stream()
                .map(word -> executorService.submit(() -> translationClient.translate(word, sourceLang, targetLang)))
                .toList();

        return futures.stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (Exception e) {
                        return "";
                    }
                })
                .toList();
    }
}
