package org.smartbear.zephyrscale.service.impl;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.smartbear.zephyrscale.service.TimeService;
import org.smartbear.zephyrscale.service.converter.TimeToWordsConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Slf4j
public class TimeServiceImpl implements TimeService {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm");

    private final TimeToWordsConverter timeToWordsConverter;

    @Override
    public Optional<String> convertToWords(@Nonnull String time) {
        return parse(time)
                .map(timeToWordsConverter::convert);
    }

    private Optional<LocalTime> parse(String time) {
        try {
            return Optional.of(LocalTime.parse(time, formatter));
        } catch (DateTimeParseException e) {
            log.error("Failed to convert provided input to time!");
            traceException(e);
        }

        return Optional.empty();
    }

    private void traceException(Exception e) {
         Arrays.stream(e.getStackTrace())
                .map(StackTraceElement::toString)
                .forEach(log::trace);
    }
}
