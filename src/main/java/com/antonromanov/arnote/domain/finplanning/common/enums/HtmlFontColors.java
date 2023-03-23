package com.antonromanov.arnote.domain.finplanning.common.enums;

import com.antonromanov.arnote.domain.finplanning.exceptions.FinPlanningException;
import com.antonromanov.arnote.common.exceptions.enums.ErrorCodes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum HtmlFontColors {

    REPAYMENT_FONT_HTML_COLOR("#7b10b1", true),
    FUTURE_FONT_HTML_COLOR("#020008", false);

    private final String hexValue;
    private final boolean isRepayment;


    public static String calculateColor(boolean isRepayment) {

            return Arrays.stream(HtmlFontColors.values())
                    .filter(e -> e.isRepayment)
                    .findFirst()
                    .orElseThrow(() -> new FinPlanningException(ErrorCodes.ERR_12)).hexValue;

    }
}
