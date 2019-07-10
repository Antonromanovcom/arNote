package com.antonromanov.arnote.repositoty;

import com.antonromanov.arnote.model.Salary;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

@Repository
public interface SalaryRepository extends JpaRepository<Salary, Integer>{
	@Query(value="select s from Salary s order by s.salarydate DESC")
	List<Salary> getLastSalary(PageRequest date);
}
