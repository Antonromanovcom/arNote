package com.antonromanov.arnote.bot.userdata;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public enum DisplayType {

    MOBILE(15), DESKTOP(35);

    private final Integer maxWidth;
}
