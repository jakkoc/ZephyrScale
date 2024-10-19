package org.smartbear.zephyrscale.service.converter.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.allegro.finance.tradukisto.ValueConverters;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class BritishTimeToWordsConverterTest {
    @Autowired
    private BritishTimeToWordsConverter britishTimeToWordsConverter;

    @ParameterizedTest
    @ValueSource(strings = {"01:00", "05:00", "10:00"})
    @DisplayName("Given full hour is provided When convert is called Then o'clock is appended")
    void givenFullHourIsProvided_whenConvertIsCalled_thenOClockIsAppended(String time) {
        // given
        LocalTime input = LocalTime.parse(time);
        String expected = ValueConverters.ENGLISH_INTEGER.asWords(input.getHour()) + " o'clock";

        // when
        String actual = britishTimeToWordsConverter.convert(input);

        // then
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @ValueSource(strings = {"01:01", "05:25", "06:26", "10:29"})
    @DisplayName("Given time before half hour is provide When convert is called Then past is appended")
    void givenTimeBeforeHalfHourIsProvided_whenConvertIsCalled_thenPastIsAppended(String time) {
        // given
        LocalTime input = LocalTime.parse(time);
        String expected = ValueConverters.ENGLISH_INTEGER.asWords(input.getMinute()) + " past " + ValueConverters.ENGLISH_INTEGER.asWords(input.getHour());

        // when
        String actual = britishTimeToWordsConverter.convert(input);

        // then
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @ValueSource(strings = {"01:31", "05:35", "06:40", "10:50"})
    @DisplayName("Given time after half hour is provide When convert is called Then to is appended")
    void givenTimeAfterHalfHourIsProvided_whenConvertIsCalled_thenToIsAppended(String time) {
        // given
        LocalTime input = LocalTime.parse(time);
        String expected = ValueConverters.ENGLISH_INTEGER.asWords(60 - input.getMinute()) + " to " + ValueConverters.ENGLISH_INTEGER.asWords(input.getHour() + 1);

        // when
        String actual = britishTimeToWordsConverter.convert(input);

        // then
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Given midnight is provided When convert is called Then midnight is returned")
    void givenMidnightIsProvided_whenConvertIsCalled_thenMidnightIsReturned() {
        // given
        LocalTime input = LocalTime.MIDNIGHT;
        String expected = "midnight";

        // when
        String actual = britishTimeToWordsConverter.convert(input);

        // then
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Given noon is provided When convert is called Then noon is returned")
    void givenNoonIsProvided_whenConvertIsCalled_thenNoonIsReturned() {
        // given
        LocalTime input = LocalTime.NOON;
        String expected = "noon";

        // when
        String actual = britishTimeToWordsConverter.convert(input);

        // then
        assertEquals(expected, actual);
    }
}
