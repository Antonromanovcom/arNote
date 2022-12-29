package com.antonromanov.arnote.domain.salary.repository;

import com.antonromanov.arnote.old.entity.common.Salary;
import com.antonromanov.arnote.old.model.ArNoteUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SalaryRepository extends JpaRepository<Salary, Long> {
	@Query(value="select s from Salary s where s.user = :user and s.salaryTimeStamp <> null order by s.salaryTimeStamp DESC")
	List<Salary> getLastSalaryListByUserDesc(@Param("user") ArNoteUser user);

	@Query(value="select s from Salary s where s.user = :user and " +
			"s.salaryTimeStamp <> null and " +
			"EXTRACT(YEAR from s.salaryTimeStamp) = :year and " +
			"EXTRACT(MONTH from s.salaryTimeStamp) = :month")
	List<Salary> findAllByUserAndMonthAndYear(@Param("user") ArNoteUser user, @Param("year") int year,
											  @Param("month") int month);

	/*Optional<Salary> findSalaryById(Long id);*/
}
