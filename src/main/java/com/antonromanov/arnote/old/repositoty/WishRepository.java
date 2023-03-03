package com.antonromanov.arnote.old.repositoty;

import com.antonromanov.arnote.old.model.ArNoteUser;
import com.antonromanov.arnote.domain.wish.entity.Wish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface WishRepository extends JpaRepository<Wish, Integer>{ //todo: почему Integer ????

	@Query(value="select w from Wish w where w.archive = false and (w.realized = false or w.realized is null) and w.user = :user order by w.priorityGroup, w.priorityGroupOrder ASC ")
	List<Wish> getAllWithGroupOrder(@Param("user") ArNoteUser user);

	@Query(value="select w from Wish w where w.archive = false and (w.realized = false or w.realized is null) and w.user = :user order by w.priority ASC ")
	List<Wish> findAllSortedByPriority(@Param("user") ArNoteUser user);

	@Query(value="select w from Wish w where w.archive = false and (w.realized = false or w.realized is null) and" +
			" w.priority = 1 and  w.user = :user order by w.wishName ASC ")
	List<Wish> getPriorityWishes(@Param("user") ArNoteUser user);

	/**
	 * Найти все желания по пользаку.
	 *
	 * @param user
	 * @return
	 */
	List<Wish> findAllByUser(ArNoteUser user);


	/**
	 * Метод для высчитывания среднего времени реализации.
	 *
	 * @param
	 * @return
	 */
	@Query(value="select w from Wish w where w.archive = false and w.realized = true and  w.user = :user order by w.wishName ASC ")
	List<Wish> getAllRealizedWishes(@Param("user") ArNoteUser user);



	/*@Query(value="select w from Wish w where w.wishName = ?1")
	Optional<List<Wish>> getWishesByName(@Param("wish") String wish);*/

	Optional<Wish> findById(long id);


	/**
	 * Запросить сумму всех реализованных пользователем желаний за все время.
 	 */
	@Query(value="select sum(p.price) from (select * from wishes w WHERE " +
			"(w.id NOT IN (311) " +
			"and w.user_id = :userId)) p" +
			" WHERE NOT p.archive AND (p.realized=true)", nativeQuery = true)
	Optional<Integer> getSumOfRealizedWishesForWholePeriod(long userId);

	/**
	 * Запросить сумму всех реализованных пользователем желаний за текущий месяц.
 	 */
	@Query(value="select sum(p.price) from (select * from wishes w WHERE " +
			"(w.id NOT IN (311) " +
			"and w.user_id = :userId " +
			"and extract(month FROM w.realization_date) = extract (month FROM CURRENT_DATE))) p" +
			" WHERE NOT p.archive AND (p.realized=true)", nativeQuery = true)
	Optional<Integer> getSumOfRealizedWishesForCurrentMonth(long userId);

}