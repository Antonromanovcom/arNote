package com.antonromanov.arnote.repositoty;

import com.antonromanov.arnote.model.ArNoteUser;
import com.antonromanov.arnote.entity.common.Salary;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

@Repository
public interface SalaryRepository extends JpaRepository<Salary, Integer>{
	@Query(value="select s from Salary s where s.user = :user and s.salaryTimeStamp <> null order by s.salaryTimeStamp DESC")
	List<Salary> getLastSalaryListByUserDesc(@Param("user") ArNoteUser user);

	@Query(value="select s from Salary s where s.user = :user and " +
			"s.salaryTimeStamp <> null and " +
			"EXTRACT(YEAR from s.salaryTimeStamp) = :year and " +
			"EXTRACT(MONTH from s.salaryTimeStamp) = :month")
	List<Salary> findAllByUserAndMonthAndYear(@Param("user") ArNoteUser user, @Param("year") int year,
											  @Param("month") int month);

	Optional<Salary> findSalaryById(Long id);
}
