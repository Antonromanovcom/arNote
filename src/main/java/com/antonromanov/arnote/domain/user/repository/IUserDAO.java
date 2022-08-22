package com.antonromanov.arnote.domain.user.repository;

import com.antonromanov.arnote.domain.wish.dto.SearchCriteria;
import com.antonromanov.arnote.domain.wish.dto.Wish;
import java.util.List;

public interface IUserDAO { //todo: на хера это нужно???? может удалим?
	List<Wish> searchWish(List<SearchCriteria> params);
}
