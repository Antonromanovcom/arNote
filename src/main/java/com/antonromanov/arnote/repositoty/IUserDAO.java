package com.antonromanov.arnote.repositoty;

import com.antonromanov.arnote.model.SearchCriteria;
import com.antonromanov.arnote.model.Wish;
import java.util.List;

public interface IUserDAO {
	List<Wish> searchWish(List<SearchCriteria> params);
}
