package org.smartbear.zephyrscale.controller;

import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.smartbear.zephyrscale.service.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@RequestMapping("/api/v1/time")
@Validated
public class TimeController {
    private static final String INVALID_TIME_PATTERN_ERROR_MESSAGE = "Provided time input does not conform to 12-hour clock format";

    private final TimeService timeService;

    @GetMapping("/convert")
    public ResponseEntity<String> convertToWords(@Pattern(regexp="[0-1]?\\d:[0-5]\\d", message=INVALID_TIME_PATTERN_ERROR_MESSAGE)
                                                     @RequestParam("time") String time) {
        String convertedTime = timeService.convertToWords(time);

        return ResponseEntity.ok(convertedTime);
    }
}
