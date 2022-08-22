package com.antonromanov.arnote.repositoty;

import com.antonromanov.arnote.model.wish.SearchCriteria;
import com.antonromanov.arnote.model.wish.Wish;
import java.util.List;

public interface IUserDAO { //todo: на хера это нужно???? может удалим?
	List<Wish> searchWish(List<SearchCriteria> params);
}
