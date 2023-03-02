package com.antonromanov.arnote.domain.finplanning.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BackGroundColors {
    EARLIER_MONTH_HTML_COLOR("#6c796c"),
    CURRENT_MONTH_HTML_COLOR("#f5e7c8"),
    FUTURE_MONTH_HTML_COLOR("#9abacd");

    private final String hexValue;
}
