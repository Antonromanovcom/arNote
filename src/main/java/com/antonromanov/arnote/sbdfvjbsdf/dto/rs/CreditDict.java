package com.antonromanov.arnote.sbdfvjbsdf.dto.rs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum CreditDict {
    FIRST_CREDIT(1),
    SECOND_CREDIT(2),
    THIRD_CREDIT(3),
    FOURTH_CREDIT(4),
    FIFTH_CREDIT(5);

    private final Integer num;

   public static CreditDict getValByNumber(Integer number){
       return Stream.of(CreditDict.values())
               .filter(v->v.getNum().equals(number))
               .findFirst()
               .orElse(CreditDict.FIRST_CREDIT);
   }
}
