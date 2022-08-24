package com.antonromanov.arnote.sex.repositoty;

import com.antonromanov.arnote.sex.model.ArNoteUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

public interface UsersRepo extends JpaRepository<ArNoteUser, Integer> {

	Optional<ArNoteUser> findByLogin(String login);

	Optional<ArNoteUser> findById(Long id);

	@Transactional
	void deleteById(Long id);

}
