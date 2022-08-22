package com.antonromanov.arnote.enums;

import com.antonromanov.arnote.entity.Wish;
import com.antonromanov.arnote.service.MainService;

public interface ArnoteOperation {
     Wish move(Wish wish, MainService ms);
}
