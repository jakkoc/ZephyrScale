package org.smartbear.zephyrscale.controller;

import org.hamcrest.core.StringContains;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class TimeControllerTest {
    private static final String TIME_ENDPOINT = "/api/v1/time/convert?time=%s";

    @Autowired
    private MockMvc mockMvc;

    @ParameterizedTest
    @ValueSource(strings = {"abc", "25:00", "13.15", "09:67"})
    @DisplayName("given time is not in correct clock format when convertToWords is called then precondition failed status is returned")
    void givenTimeIsNotInCorrectClockFormat_whenConvertToWordsIsCalled_thenPreconditionFailedStatusIsReturned(String time) throws Exception {
        // when && then
        mockMvc.perform(get(String.format(TIME_ENDPOINT, time)))
                .andExpect(status().isPreconditionFailed())
                .andReturn();
    }

    @ParameterizedTest
    @ValueSource(strings = {"01:00", "05:00", "10:00"})
    @DisplayName("given full hour is provided when convertToWords is called then result contains o'clock")
    void givenFullHourIsProvided_whenConvertToWordsIsCalled_thenResultContainsOClock(String time) throws Exception {
        // when && then
        mockMvc.perform(get(String.format(TIME_ENDPOINT, time)))
                .andExpect(status().isOk())
                .andExpect(content().string(StringContains.containsString("o'clock")))
                .andReturn();
    }

    @ParameterizedTest
    @ValueSource(strings = {"01:01", "05:25", "06:26", "10:29"})
    @DisplayName("given time before half hour is provided when convertToWords is called then result contains past")
    void givenTimeBeforeHalfHourIsProvided_whenConvertToWordsIsCalled_thenResultContainsPast(String time) throws Exception {
        // when && then
        mockMvc.perform(get(String.format(TIME_ENDPOINT, time)))
                .andExpect(status().isOk())
                .andExpect(content().string(StringContains.containsString("past")))
                .andReturn();
    }

    @ParameterizedTest
    @ValueSource(strings = {"01:31", "05:35", "06:40", "10:50"})
    @DisplayName("given time after half hour is provided when convertToWords is called then result contains to")
    void givenTimeAfterHalfHourIsProvided_whenConvertToWordsIsCalled_thenResultContainsTo(String time) throws Exception {
        // when && then
        mockMvc.perform(get(String.format(TIME_ENDPOINT, time)))
                .andExpect(status().isOk())
                .andExpect(content().string(StringContains.containsString("to")))
                .andReturn();
    }

    @Test
    @DisplayName("given midnight is provided when convertToWords is called then result contains midnight")
    void givenMidnightIsProvided_whenConvertToWordsIsCalled_thenResultContainsMidnight() throws Exception {
        // when && then
        mockMvc.perform(get(String.format(TIME_ENDPOINT, "00:00")))
                .andExpect(status().isOk())
                .andExpect(content().string(StringContains.containsString("midnight")))
                .andReturn();
    }

    @Test
    @DisplayName("given noon is provided when convertToWords is called then result contains noon")
    void givenNoonIsProvided_whenConvertToWordsIsCalled_thenResultContainsNoon() throws Exception {
        // when && then
        mockMvc.perform(get(String.format(TIME_ENDPOINT, "12:00")))
                .andExpect(status().isOk())
                .andExpect(content().string(StringContains.containsString("noon")))
                .andReturn();
    }
}
