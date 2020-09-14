package com.antonromanov.arnote.dto.response;

import com.antonromanov.arnote.entity.Wish;
import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class DTO { //todo: ПЕРЕИМЕНОВАТЬ!!!!!
    private List<Wish> list;
}
