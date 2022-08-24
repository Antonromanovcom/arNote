package com.antonromanov.arnote.sex.enums;

import com.antonromanov.arnote.domain.wish.service.WishService;
import com.antonromanov.arnote.sex.model.wish.Wish;

public interface ArnoteOperation {
     Wish move(Wish wish, WishService ms);
}
