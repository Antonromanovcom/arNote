package com.antonromanov.arnote.service.investment;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalTime;

@AllArgsConstructor
@Data
public class Record {
    private final int id;
    private final int count;
    private final LocalTime creationTime;
}
