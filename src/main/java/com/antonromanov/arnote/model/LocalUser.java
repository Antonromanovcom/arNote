package com.antonromanov.arnote.model;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "users", schema = "arnote", catalog = "postgres")
public class LocalUser {

	public LocalUser(String login, Role userRole, String pwd, boolean usercryptomode, String email, String fullname) {
		this.login = login;
		this.pwd = pwd;
		this.userCryptoMode = usercryptomode;
		this.userRole = userRole;
		this.email = email;
		this.fullname = fullname;
	}

	public enum Role { USER, ADMIN }

	@Id
	@Column(name="id", nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_seq_gen")
	@SequenceGenerator(name = "users_seq_gen", sequenceName ="arnote.users_id_seq", allocationSize = 1)
	private long id;

	@Column
	private String login;

	@Column
	private String pwd;

	@Enumerated(EnumType.STRING)
	private Role userRole = Role.USER;

	@Column(nullable = false, columnDefinition = "boolean default false")
	private Boolean userCryptoMode;

	@Column
	@Temporal(TemporalType.DATE)
	private Date creationDate;

	@Column
	private String email;

	@Column
	private String fullname;

	@Column
	private String viewMode;


	@Column
	private LocalDateTime lastOperationTime;

	@Column
	private String lastOperation;

	@Override
	public String toString() {
		return "LocalUser{" +
				"id=" + id +
				", login='" + login + '\'' +
				", pwd='" + pwd + '\'' +
				", userRole=" + userRole +
				", userCryptoMode=" + userCryptoMode +
				", creationDate=" + creationDate +
				", email='" + email + '\'' +
				", fullname='" + fullname + '\'' +
				'}';
	}

	public LocalUser(String login, String pw) {
		this.login = login;
		this.pwd = pw;
		this.userCryptoMode = false;
		this.userRole = Role.USER;
	}

	public LocalUser() {
	}
}
