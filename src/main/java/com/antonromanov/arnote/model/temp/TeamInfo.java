package com.antonromanov.arnote.model.temp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TeamInfo {
    private String employeeId;
    private Boolean favoritFlg;
    private String fio;
    private Boolean primaryFlg;
    private String sbrfAccountRole;

}
