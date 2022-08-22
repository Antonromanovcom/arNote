package com.antonromanov.arnote.sbdfvjbsdf.dto.response;

import com.antonromanov.arnote.entity.Wish;
import lombok.Builder;
import lombok.Data;
import java.util.List;

/**
 * DTO со списком желаний, который отдаем на фронт.
 */
@Data
@Builder
public class WishListResponse {
    private List<Wish> list;
}
