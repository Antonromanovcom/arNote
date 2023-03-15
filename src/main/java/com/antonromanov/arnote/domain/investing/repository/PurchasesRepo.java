package com.antonromanov.arnote.domain.investing.repository;

import com.antonromanov.arnote.domain.investing.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchasesRepo extends JpaRepository<Purchase, Long> {
}
