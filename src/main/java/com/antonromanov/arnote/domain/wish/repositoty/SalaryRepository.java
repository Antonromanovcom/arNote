package com.antonromanov.arnote.domain.wish.repositoty;

//@Repository
public interface SalaryRepository /*extends JpaRepository<Salary, Integer>*/{
	/*@Query(value="select s from Salary s where s.user = :user and s.salaryTimeStamp <> null order by s.salaryTimeStamp DESC")
	List<Salary> getLastSalary(@Param("user") ArNoteUser user);*/
}
