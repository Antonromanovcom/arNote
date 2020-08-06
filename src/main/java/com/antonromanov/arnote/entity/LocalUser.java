package com.antonromanov.arnote.entity;

import com.antonromanov.arnote.dto.request.UserDto;
import com.antonromanov.arnote.enums.SortMode;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "users")
public class LocalUser {

	public enum Role { USER, ADMIN }

	@Id
	@Column(name="id", nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_seq_gen")
	@SequenceGenerator(name = "users_seq_gen", sequenceName ="users_id_seq", allocationSize = 1)
	private long id;

	/**
	 * Логин пользователя.
	 */
	@Column
	private String login;

	/**
	 * Пароль пользователя.
	 */
	@Column
	private String pwd;

	/**
	 * Роль пользователя.
	 */
	@Enumerated(EnumType.STRING)
	private Role userRole = Role.USER;

	/**
	 * Режим шифрования на фронте.
	 */
	@Column(nullable = false, columnDefinition = "boolean default false")
	private Boolean userCryptoMode;

	/**
	 * Дата создания пользователя.
	 */
	@Column
	@Temporal(TemporalType.DATE)
	private Date creationDate;

	/**
	 * Email-адрес пользователя.
	 */
	@Column
	private String email;

	/**
	 * Полное имя пользователя.
	 */
	@Column
	private String fullname; //todo: нормально переименовать

	/**
	 * Режим отображения - таблица или помесячная группировка.
	 */
	@Column
	private String viewMode;

	/**
	 * Режим сортировки
	 */
	@Enumerated(EnumType.STRING)
	private SortMode sortMode;

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

	public LocalUser(String login, Role userRole, String pwd, boolean usercryptomode, String email, String fullname) { //todo: переименовать usercryptomode и fullname
		this.login = login;
		this.pwd = pwd;
		this.userCryptoMode = usercryptomode;
		this.userRole = userRole;
		this.email = email;
		this.fullname = fullname;
	}

	public LocalUser(String login, String pw) {
		this.login = login;
		this.pwd = pw;
		this.userCryptoMode = false;
		this.userRole = Role.USER;
	}

	public LocalUser() { //todo: ломбок
	}

	public LocalUser(UserDto newUser, String securePw) {
		this.login = newUser.getLogin();
		this.pwd = securePw;
		this.userCryptoMode = newUser.getUserCryptoMode();
		this.userRole = Role.USER;
		this.email = newUser.getEmail();
		this.fullname = newUser.getFullName();
		this.viewMode = "TABLE"; //todo: вынести в ЕНУМ
	}


}
