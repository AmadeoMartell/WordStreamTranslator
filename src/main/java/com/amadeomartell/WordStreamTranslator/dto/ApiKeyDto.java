package com.amadeomartell.WordStreamTranslator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiKeyDto {
    private UUID id;
    private String apiKey;
    private String description;
    private Instant createdAt;
}
