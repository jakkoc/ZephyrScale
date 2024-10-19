package org.smartbear.zephyrscale.service.converter.impl;

import lombok.NonNull;
import org.smartbear.zephyrscale.service.converter.TimeToWordsConverter;
import org.springframework.stereotype.Component;
import pl.allegro.finance.tradukisto.ValueConverters;

import java.time.LocalTime;

@Component
public class BritishTimeToWordsConverter implements TimeToWordsConverter {

    @Override
    public String convert(@NonNull LocalTime time) {
        int hour = time.getHour();
        int minute = time.getMinute();

        String minuteWord = minuteToWord(minute);
        String timeVerb = getTimeVerb(minute);
        String hourWord = hourToWord(hour, minute);

        if (minute == 0) {
            return hourWord + (hour % 12 == 0 ? "" : " o'clock");
        }

        return "%s %s %s".formatted(minuteWord, timeVerb, hourWord);
    }

    private String minuteToWord(int minute) {
        int roundedToHour = Math.min(minute, 60 - minute);

        if (roundedToHour == 15) {
            return "quarter";
        }

        if (roundedToHour == 30) {
            return "half";
        }

        return ValueConverters.ENGLISH_INTEGER.asWords(roundedToHour);
    }
    
    private String hourToWord(int hour, int minute) {
        if (minute > 30) {
            hour = hour + 1;
        }

        if (hour == 0) {
            return "midnight";
        }
        
        if (hour == 12) {
            return "noon";
        }
        
        return ValueConverters.ENGLISH_INTEGER.asWords(hour);
    }

    private String getTimeVerb(int minute){
        return minute > 30 ? "to" : "past";
    }
}
