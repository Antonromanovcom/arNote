package com.antonromanov.arnote.repositoty;

import com.antonromanov.arnote.model.LocalUser;
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


	@Query(value="select w from Wish w where w.ac = false and w.user = :user order by w.priorityGroup, w.priorityGroupOrder ASC ")
	List<Wish> getAllWithGroupOrder(@Param("user") LocalUser user);

	@Query(value="select w from Wish w where w.ac = false and w.user = :user order by w.priority ASC ")
	List<Wish> findAllByIdSorted(@Param("user") LocalUser user);



	@Query(value="select w from Wish w where w.ac = false and w.priority = 1 and  w.user = :user order by w.wish ASC ")
	List<Wish> getAllWithPriority1(@Param("user") LocalUser user);



	@Query(value="select w from Wish w where w.wish = ?1")
	Optional<List<Wish>> getWishesByName(@Param("wish") String wish);

	@Modifying
	@Transactional
	@Query("delete from Wish w where w.id = ?1")
	void deleteByLongId(Long entityId);

	Optional<Wish> findById(long l);


	@Query(value="select * from arnote.wishes w where w.wish like " +
			"(concat('%',:wish,'%')) and w.user_id = :userId order by w.wish", nativeQuery = true)
	Optional<List<Wish>> findAllByWishAndUser(String wish, long userId);

}
