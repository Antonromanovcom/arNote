package com.antonromanov.arnote.domain.wish.enums;

import com.antonromanov.arnote.domain.wish.service.WishService;
import com.antonromanov.arnote.domain.wish.entity.Wish;

public interface ArnoteOperation {
     Wish move(Wish wish, WishService ms);
}
