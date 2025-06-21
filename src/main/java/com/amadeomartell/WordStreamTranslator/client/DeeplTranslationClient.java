package com.amadeomartell.WordStreamTranslator.client;

import com.amadeomartell.WordStreamTranslator.dto.DeeplResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
@Slf4j
@RequiredArgsConstructor
public class DeeplTranslationClient implements TranslationClient {

    private final RestTemplate restTemplate;

    @Value("${translator.external.api-url}")
    private String apiUrl;

    @Value("${translator.external.api-key}")
    private String apiKey;

    @Override
    public String translate(String word, String sourceLang, String targetLang) {
        if (word == null || word.isBlank()) {
            throw new IllegalArgumentException("Word cannot be null or blank");
        }

        URI uri = UriComponentsBuilder.fromUriString(apiUrl)
                .queryParam("auth_key", apiKey)
                .queryParam("text", word)
                .queryParam("source_lang", sourceLang.toUpperCase())
                .queryParam("target_lang", targetLang.toUpperCase())
                .build()
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        try {
            ResponseEntity<DeeplResponseDto> response = restTemplate.exchange(
                    uri,
                    HttpMethod.POST,
                    new HttpEntity<>(headers),
                    DeeplResponseDto.class
            );

            return response.getBody()
                    .getTranslations()
                    .stream()
                    .findFirst()
                    .map(DeeplResponseDto.Translation::getText)
                    .orElse(word);

        } catch (Exception ex) {
            log.error("Failed to translate word '{}': {}", word, ex.getMessage());
            return word;
        }
    }
}
