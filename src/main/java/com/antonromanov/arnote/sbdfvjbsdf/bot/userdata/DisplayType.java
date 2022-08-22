package com.antonromanov.arnote.sbdfvjbsdf.bot.userdata;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DisplayType {

    MOBILE(15), DESKTOP(35);

    private final Integer maxWidth;
}
