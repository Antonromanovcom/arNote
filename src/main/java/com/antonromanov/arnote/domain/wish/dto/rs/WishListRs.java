package com.antonromanov.arnote.domain.wish.dto.rs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.util.List;

/**
 * Список желаний
 */
/*@Data
@Builder
@AllArgsConstructor*/
public class WishListRs {
    private List<WishRs> list;


    public WishListRs(List<WishRs> list) {
        this.list = list;
    }


    public List<WishRs> getList() {
        return list;
    }

    public void setList(List<WishRs> list) {
        this.list = list;
    }
}
