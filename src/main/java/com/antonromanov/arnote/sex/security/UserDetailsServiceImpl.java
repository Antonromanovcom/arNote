package com.antonromanov.arnote.sex.security;

import com.antonromanov.arnote.sex.model.ArNoteUser;
import com.antonromanov.arnote.sex.repositoty.UsersRepo;
import com.antonromanov.arnote.sex.security.UserPrinciple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UsersRepo userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		ArNoteUser user = userRepository.findByLogin(username)
				.orElseThrow(() ->
						new UsernameNotFoundException("User Not Found with -> username or email : " + username));

		return UserPrinciple.build(user);
	}


}