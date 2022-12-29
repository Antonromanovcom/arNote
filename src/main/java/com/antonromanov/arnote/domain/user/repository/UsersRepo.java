package com.antonromanov.arnote.domain.user.repository;

import com.antonromanov.arnote.old.model.ArNoteUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepo extends JpaRepository<ArNoteUser, Integer> {

	 Optional<ArNoteUser> findByLogin(String login);

	Optional<ArNoteUser> findById(Long id);
}
