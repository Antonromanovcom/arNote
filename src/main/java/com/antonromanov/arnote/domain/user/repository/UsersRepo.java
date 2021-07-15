package com.antonromanov.arnote.domain.user.repository;

import com.antonromanov.arnote.domain.user.dto.ArNoteUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

public interface UsersRepo extends JpaRepository<ArNoteUser, Integer> {

	Optional<ArNoteUser> findByLogin(String login);

	Optional<ArNoteUser> findByEmail(String email);

	Optional<ArNoteUser> findById(Long id);

	@Transactional
	void deleteById(Long id);

}
