package com.pulsar.habitica.util;


import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

public class LocalUtilTest {

    @Test
    void successIfStringIsLocale() {
        List<String> locales = List.of("ru_RU", "en_US", "de_DE", "fr_FR");

        assertAll("Checking locales...",
                locales.stream()
                        .map(locale ->
                                () -> assertThat(LocaleUtil.isLocale(locale))
                                        .as(() -> "Checking locale %s".formatted(locale))
                                        .isTrue())
        );
    }

    @Test
    void failIfStringIsNotLocale() {
        List<String> dummyLocales = List.of("dum_dummy", "US_en", "DUMMY_dum", "");

        assertAll("Checking locales...",
                dummyLocales.stream()
                        .map(locale ->
                                () -> assertThat(LocaleUtil.isLocale(locale))
                                        .as(() -> "Checking locale %s".formatted(locale))
                                        .isFalse())
        );
    }

    @Test
    void testGettingLanguageFromString() {
        Map<String, String> locales = Map.of(
                "ru_RU", "ru",
                "en_US", "en",
                "de_DE", "de",
                "fr_FR", "fr");

        assertAll("Checking for language acquisition...",
                locales.entrySet().stream()
                        .map(entry ->
                                () -> assertThat(LocaleUtil.getLanguage(entry.getKey()))
                                        .as(() -> "Checking locale %s".formatted(entry.getKey()))
                                        .isEqualTo(entry.getValue()))
        );
    }

    @Test
    void testGettingCountryFromString() {
        Map<String, String> locales = Map.of(
                "ru_RU", "RU",
                "en_US", "US",
                "de_DE", "DE",
                "fr_FR", "FR");

        assertAll("Checking for country acquisition...",
                locales.entrySet().stream()
                        .map(entry ->
                                () -> assertThat(LocaleUtil.getCountry(entry.getKey()))
                                        .as(() -> "Checking locale %s".formatted(entry.getKey()))
                                        .isEqualTo(entry.getValue()))
        );
    }
}
