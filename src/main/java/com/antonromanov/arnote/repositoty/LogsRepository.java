package com.antonromanov.arnote.repositoty;

import com.antonromanov.arnote.model.Wish;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface LogsRepository extends JpaRepository<Wish, Integer>{
}
