package com.antonromanov.arnote.repositoty;

import com.antonromanov.arnote.model.Wish;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

@Repository
public interface WishRepository extends JpaRepository<Wish, Integer>{

	@Query(value="select w from Wish w where w.ac = false order by w.priority ASC ")
	List<Wish> getAllNotInArchive();

	@Query(value="select w from Wish w where w.ac = false and w.priority = 1 order by w.priority ASC ")
	List<Wish> getAllNotWithPriority1();

}
