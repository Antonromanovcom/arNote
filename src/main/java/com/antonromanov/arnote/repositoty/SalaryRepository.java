package com.antonromanov.arnote.repositoty;

import com.antonromanov.arnote.model.LocalUser;
import com.antonromanov.arnote.model.Salary;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

@Repository
public interface SalaryRepository extends JpaRepository<Salary, Integer>{
	@Query(value="select s from Salary s where s.user = :user and s.salaryTimeStamp <> null order by s.salaryTimeStamp DESC")
	List<Salary> getLastSalary(@Param("user") LocalUser user);
}
