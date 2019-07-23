package com.antonromanov.arnote.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnTransformer;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "users", schema = "arnote", catalog = "postgres")
public class LocalUser {

	public LocalUser(String login, Role userRole, String pwd, boolean usercryptomode) {

		this.login = login;
		this.pwd = pwd;
		this.userCryptoMode = usercryptomode;
		this.userRole = userRole;

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
	//@ColumnTransformer(read = "pgp_sym_decrypt(creditCardNumber, ‘mySecretKey’)”, write = “pgp_sym_encrypt(?, ‘mySecretKey’)")
	private String pwd;

	@Enumerated(EnumType.STRING)
	private Role userRole = Role.USER;

	@Column(nullable = false, columnDefinition = "boolean default false")
	private Boolean userCryptoMode;

	@Column
	@Temporal(TemporalType.DATE)
	private Date creationDate;


	public LocalUser(String login, String pw) {
		this.login = login;
		this.pwd = pw;
		this.userCryptoMode = false;
		this.userRole = Role.USER;
	}

	public LocalUser() {
	}
}
