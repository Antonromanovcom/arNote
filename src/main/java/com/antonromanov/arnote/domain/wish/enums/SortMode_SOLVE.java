package com.antonromanov.arnote.domain.wish.enums;

/**
 * Режим сортировки.
 */
/*@AllArgsConstructor
@Getter*/
    //todo: разобраться
public enum SortMode_SOLVE { // todo: тут надо или сделать общий интерфейс, или везде отдавать WishResponse, или там где используется первый компаратор - использовать второй, а потом конвертить.
   /* NAME(Comparator.comparing(WishResponse::getWish), Comparator.comparing(Wish::getWish)),
    PRICE_ASC(Comparator.comparing(WishResponse::getPrice), Comparator.comparing(Wish::getPrice)),
    PRICE_DESC(Comparator.comparing(WishResponse::getPrice).reversed(), Comparator.comparing(Wish::getPrice).reversed()),
    ALL(Comparator.comparing(WishResponse::getId), Comparator.comparing(Wish::getId)),*/
  //  DEFAULT(null, null);
  //  DEFAULT( null);

  // private final Comparator<WishResponse> wishResponseComparator;
  // private final Comparator<Wish> wishComparator;
}
