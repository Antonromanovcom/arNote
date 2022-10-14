package com.antonromanov.arnote.domain.wish.dto.rq;

import com.antonromanov.arnote.domain.wish.enums.UserViewMode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ToggleUserModeRq {
    private UserViewMode userViewMode; //todo: кидать ошибку если прислали не то. Короче нужна валидация входных данных
}

