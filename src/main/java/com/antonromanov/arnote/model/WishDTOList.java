package com.antonromanov.arnote.model;

import lombok.*;
import java.util.List;


@Builder //todo: где геттеры и сеттры, почему поля не приватные??? Что это за пиздец???
public class WishDTOList {

    @Getter
    List<WishDTO> wishList;
    String monthName;
    String year;
    int monthNumber;
    int colspan;
    int sum;
    boolean overflow;
    String colorClass;
    boolean expanded;
    int balance; // набегающий баланс
}

