package com.antonromanov.arnote.repositoty;

import com.antonromanov.arnote.dto.SearchCriteria;
import com.antonromanov.arnote.entity.Wish;
import java.util.List;

public interface IUserDAO { //todo: на хера это нужно???? может удалим?
	List<Wish> searchWish(List<SearchCriteria> params);
}
