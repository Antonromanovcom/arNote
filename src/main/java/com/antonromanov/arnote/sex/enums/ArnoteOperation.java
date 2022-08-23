package com.antonromanov.arnote.sex.enums;

import com.antonromanov.arnote.entity.Wish;
import com.antonromanov.arnote.sex.services.MainService;

public interface ArnoteOperation {
     Wish move(Wish wish, MainService ms);
}
