package com.antonromanov.arnote.repositoty;

import com.antonromanov.arnote.model.ArNoteUser;
import com.antonromanov.arnote.model.investing.Bond;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BondsRepo extends JpaRepository<Bond, Long>{

	List<Bond> findAllByUser(ArNoteUser user);
	Optional<Bond> findBondByUserAndTicker(ArNoteUser user, String ticker);

}
