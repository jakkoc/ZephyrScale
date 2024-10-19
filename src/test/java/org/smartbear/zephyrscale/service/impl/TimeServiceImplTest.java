package org.smartbear.zephyrscale.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class TimeServiceImplTest {
    @Autowired
    private TimeServiceImpl timeService;

    @Test
    @DisplayName("Given time input is empty When convertToWords Then empty optional is returned")
    void givenTimeInputIsEmpty_whenConvertToWords_thenEmptyOptionalIsReturned() {
        // given
        String input = "";

        // when
        Optional<String> actual = timeService.convertToWords(input);

        // then
        assertTrue(actual.isEmpty());
    }

    @ParameterizedTest
    @ValueSource(strings = {"21.15","12/37","4.56.0"})
    @DisplayName("Given time input has incorrect format When convertToWords Then empty optional is returned")
    void givenTimeInputHasIncorrectFormat_whenConvertToWords_thenEmptyOptionalIsReturned(String input) {
        // when
        Optional<String> actual = timeService.convertToWords(input);

        // then
        assertTrue(actual.isEmpty());
    }

    @ParameterizedTest
    @MethodSource("timeValuesProvider")
    @DisplayName("Given time input is valid When convertToWords Then optional with time in words is returned")
    void givenTimeInputIsValid_whenConvertToWords_thenOptionalWithTimeInWordsIsReturned(String input, String expected) {
        // when
        Optional<String> actual = timeService.convertToWords(input);

        // then
        assertTrue(actual.isPresent());
        assertTrue(actual.get().contains(expected));
    }

    private static Stream<Arguments> timeValuesProvider() {
        return Stream.of(
                Arguments.of("1:00", "one o'clock"),
                Arguments.of("2:05", "five past two"),
                Arguments.of("3:10", "ten past three"),
                Arguments.of("4:15", "quarter past four"),
                Arguments.of("5:20", "twenty past five"),
                Arguments.of("6:25", "twenty-five past six"),
                Arguments.of("6:32", "twenty-eight to seven"),
                Arguments.of("7:30", "half past seven"),
                Arguments.of("7:35", "twenty-five to eight"),
                Arguments.of("8:40", "twenty to nine"),
                Arguments.of("9:45", "quarter to ten"),
                Arguments.of("10:50", "ten to eleven"),
                Arguments.of("11:55", "five to noon"),
                Arguments.of("00:00", "midnight"),
                Arguments.of("12:00", "noon")
        );
    }
}
