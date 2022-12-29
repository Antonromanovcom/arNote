package com.antonromanov.arnote.old.dto.rs;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


/**
 * Информация, что на заданную дату возможно добавление бумаги
 *
 *
 */
@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.ALWAYS)
public class DateIsPossibleRs {
    Boolean isPossible;
}
