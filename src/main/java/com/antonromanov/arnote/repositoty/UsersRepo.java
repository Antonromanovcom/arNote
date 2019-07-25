package com.antonromanov.arnote.repositoty;

import com.antonromanov.arnote.model.LocalUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

public interface UsersRepo extends JpaRepository<LocalUser, Integer> {

	Optional<LocalUser> findByLogin(String login);

	Optional<LocalUser> findByEmail(String email);

	Optional<LocalUser> findById(Long id);

	@Transactional
	void deleteById(Long id);

}
