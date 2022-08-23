package com.antonromanov.arnote.sex.repositoty;


import org.springframework.stereotype.Repository;

@Repository
public interface SalaryRepository /*extends JpaRepository<Salary, Integer>*/{
	/*@Query(value="select s from Salary s where s.user = :user and s.salaryTimeStamp <> null order by s.salaryTimeStamp DESC")
	List<Salary> getLastSalaryListByUserDesc(@Param("user") ArNoteUser user);

	@Query(value="select s from Salary s where s.user = :user and " +
			"s.salaryTimeStamp <> null and " +
			"EXTRACT(YEAR from s.salaryTimeStamp) = :year and " +
			"EXTRACT(MONTH from s.salaryTimeStamp) = :month")
	List<Salary> findAllByUserAndMonthAndYear(@Param("user") ArNoteUser user, @Param("year") int year,
											  @Param("month") int month);

	Optional<Salary> findSalaryById(Long id);*/
}
