package com.antonromanov.arnote.repositoty;

import com.antonromanov.arnote.model.LocalUser;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsersRepo extends JpaRepository<LocalUser, Integer> {

	Optional<LocalUser> findByLogin(String login);

}
