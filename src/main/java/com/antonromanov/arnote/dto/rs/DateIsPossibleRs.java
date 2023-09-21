package com.antonromanov.arnote.dto.rs;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;


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