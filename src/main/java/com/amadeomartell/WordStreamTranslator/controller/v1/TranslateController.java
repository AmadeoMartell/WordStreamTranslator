package com.amadeomartell.WordStreamTranslator.controller.v1;

import com.amadeomartell.WordStreamTranslator.service.TranslationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/translate")
@RequiredArgsConstructor
public class TranslateController {

    private final TranslationService translationService;

    @GetMapping
    public List<String> testTranslation(
            @RequestParam String text,
            @RequestParam String sourceLang,
            @RequestParam String targetLang
    ) {
        List<String> words = Arrays.stream(text.split("\\s+")).toList();
        return translationService.translate(words, sourceLang, targetLang);
    }

    @GetMapping("/test")
    public String test() {
        return "Hello World!";
    }
}
