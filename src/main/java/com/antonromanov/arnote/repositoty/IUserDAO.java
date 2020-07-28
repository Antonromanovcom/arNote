package com.antonromanov.arnote.repositoty;

import com.antonromanov.arnote.model.SearchCriteria;
import com.antonromanov.arnote.model.Wish;
import java.util.List;

public interface IUserDAO { //todo: на хера это нужно???? может удалим?
	List<Wish> searchWish(List<SearchCriteria> params);
}
