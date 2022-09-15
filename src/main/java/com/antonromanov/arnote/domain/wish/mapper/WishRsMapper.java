package com.antonromanov.arnote.domain.wish.mapper;

import com.antonromanov.arnote.domain.wish.dto.rq.WishRq;
import com.antonromanov.arnote.domain.wish.dto.rs.WishRs;
import com.antonromanov.arnote.sex.model.wish.Wish;
import org.mapstruct.Mapper;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface WishRsMapper {


    WishRs mapWish (Wish source);

    default List<WishRs> mapWishList (List<Wish> wishList){
        return wishList.stream()
                .map(this::mapWish)
                .collect(Collectors.toList());
    }

    Wish map(WishRq wish);
}
