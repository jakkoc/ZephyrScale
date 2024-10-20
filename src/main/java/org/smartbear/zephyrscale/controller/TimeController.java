package org.smartbear.zephyrscale.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.smartbear.zephyrscale.service.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@RequestMapping("/api/v1/time")
@Validated
@Tag(name = "Time API", description = "API for time related operations")
public class TimeController {
    private static final String INVALID_TIME_PATTERN_ERROR_MESSAGE = "Provided time input does not conform to 12-hour clock format";

    private final TimeService timeService;

    @Operation(
            summary = "Convert time to words",
            description = "Converts the given time to british english words"
    )
    @ApiResponse(responseCode = "200", description = "Time converted successfully",
            content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, examples = {@ExampleObject(value = "one o'clock")}))
    @ApiResponse(responseCode = "412", description = "Provided time input does not conform to 12-hour clock format",
            content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, examples={@ExampleObject(value = "Value must follow pattern [0-1]?\\d:[0-5]\\d")}))
    @ApiResponse(responseCode = "500", description = "Internal server error occurred", content = @Content)
    @GetMapping(value = "/convert", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> convertToWords(@Pattern(regexp="[0-1]?\\d:[0-5]\\d", message=INVALID_TIME_PATTERN_ERROR_MESSAGE)
                                                     @RequestParam("time") String time) {
        Optional<String> convertedTime = timeService.convertToWords(time);

        return convertedTime.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.internalServerError().build());
    }
}
