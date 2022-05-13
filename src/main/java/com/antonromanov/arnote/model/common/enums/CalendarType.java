package com.antonromanov.arnote.model.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.Arrays;


@AllArgsConstructor
@Getter
public enum CalendarType {
    WEEKEND(1, "Выходной"),
    WORK (2, "рабочий и сокращенный"),
    WORK_WEEKEND (2, "рабочий день (суббота/воскресенье)");

    private final Integer id;
    private final String description;

    public static CalendarType searchByIdType(String id){
        return Arrays.stream(CalendarType.values())
                .filter(e->e.id == Integer.parseInt(id))
                .findFirst()
                .orElse(null);
    }


}
