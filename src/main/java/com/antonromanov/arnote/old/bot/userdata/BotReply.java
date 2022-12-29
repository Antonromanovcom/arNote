package com.antonromanov.arnote.old.bot.userdata;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class BotReply {
    private String text;
    private String command;
}
