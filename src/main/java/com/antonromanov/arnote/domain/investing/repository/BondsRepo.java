package com.antonromanov.arnote.domain.investing.repository;

import com.antonromanov.arnote.domain.investing.dto.common.Bond;
import com.antonromanov.arnote.sex.model.ArNoteUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface BondsRepo /*extends JpaRepository<Bond, Long>*/{

	/*List<Bond> findAllByUser(ArNoteUser user);
	Optional<Bond> findBondByUserAndTicker(ArNoteUser user, String ticker);*/

}