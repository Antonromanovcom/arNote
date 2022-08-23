package com.antonromanov.arnote.sex.security;

import com.antonromanov.arnote.sex.model.ArNoteUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UserPrinciple implements UserDetails {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String name;

	private String username;

	private String email;

	@JsonIgnore
	private String password;

	private Collection<? extends GrantedAuthority> authorities;

	public UserPrinciple(Long id, String name,
	                     String username, String email, String password,
	                     Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.name = name;
		this.username = username;
		this.email = email;
		this.password = password;
		this.authorities = authorities;
	}

	public static UserPrinciple build(ArNoteUser usr) {
		List<GrantedAuthority> authorities = Stream.of(ArNoteUser.Role.values()).map(role ->
				new SimpleGrantedAuthority(role.name())
		).collect(Collectors.toList());

		/*return new UserPrinciple(
				usr.getId()
				user.getId(),
				user.getFullname(),
				user.getLogin(),
				user.getEmail(),
				user.getPwd(),
				authorities
		);*/

		/*return new UserPrinciple(
				*//*usr.getId(),
				null,
				null,
				null,
				null,
				authorities*//*
		);*/

		return null;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true; // todo: чо за хуйня?? Разобраться!
	}

	@Override
	public boolean isAccountNonLocked() {
		return true; // todo: чо за хуйня?? Разобраться!
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true; // todo: чо за хуйня?? Разобраться!
	}

	@Override
	public boolean isEnabled() {
		return true; // todo: чо за хуйня?? Разобраться!
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		UserPrinciple user = (UserPrinciple) o;
		return Objects.equals(id, user.id);
	}

}
