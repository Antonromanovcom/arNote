package com.antonromanov.arnote.repositoty;

import com.antonromanov.arnote.model.Wish;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface WishRepository extends JpaRepository<Wish, Integer>{

	@Query(value="select w from Wish w where w.ac = false order by w.priority ASC ")
	List<Wish> getAllNotInArchive();

	@Query(value="select w from Wish w where w.ac = false and w.priority = 1 order by w.priority ASC ")
	List<Wish> getAllWithPriority1();

//

	@Query(value="select w from Wish w where w.wish = ?1")
	Optional<List<Wish>> getWishesByName(@Param("wish") String wish);

	@Modifying
	@Transactional
	@Query("delete from Wish w where w.id = ?1")
	void deleteByLongId(Long entityId);

}
