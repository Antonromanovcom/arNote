package com.antonromanov.arnote.dto.response;

import lombok.Builder;
import lombok.Data;
import java.util.List;


@Builder //todo: где геттеры и сеттры, почему поля не приватные??? Что это за пиздец???
@Data
public class WishList { //todo: переименовать

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

