package org.smartbear.zephyrscale.service.converter;

import java.time.LocalTime;

public interface TimeToWordsConverter {
    String convert(LocalTime time);
}
