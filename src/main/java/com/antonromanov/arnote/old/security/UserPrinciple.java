package com.antonromanov.arnote.old.security;

import com.antonromanov.arnote.old.model.ArNoteUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public class UserPrinciple implements UserDetails { //todo: разобраться с этим классом

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

		return new UserPrinciple(
				usr.getId(),
				usr.getFullname(),
				usr.getLogin(),
				usr.getEmail(),
				usr.getPwd(),
				authorities
		);
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
