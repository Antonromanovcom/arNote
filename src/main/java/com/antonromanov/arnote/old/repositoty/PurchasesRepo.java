package com.antonromanov.arnote.repositoty;

import com.antonromanov.arnote.model.investing.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchasesRepo extends JpaRepository<Purchase, Long>{
}
