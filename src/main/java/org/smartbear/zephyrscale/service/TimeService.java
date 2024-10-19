package org.smartbear.zephyrscale.service;

import java.util.Optional;

public interface TimeService {
    Optional<String> convertToWords(String time);
}
