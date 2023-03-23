package com.antonromanov.arnote.domain.user.entity;

import com.antonromanov.arnote.domain.wish.enums.SortMode;
import com.antonromanov.arnote.old.model.investing.InvestingSortMode;
import com.antonromanov.arnote.old.model.wish.enums.DeltaMode;
import com.antonromanov.arnote.domain.wish.enums.FilterMode;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@Entity
@Data
@Table(name = "users")
@NoArgsConstructor
public class ArNoteUser {

	public enum Role { USER, ADMIN } //todo: во внешний енум!

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
	@Column(nullable = true, columnDefinition = "boolean default false")
	private Boolean userCryptoMode; //todo: удалить

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
	private String fullname; //todo: нормально переименовать*/

	/**
	 * Режим отображения - таблица или помесячная группировка.
	 */
	@Column
	private String viewMode; // todo: поменять на com.antonromanov.arnote.domain.wish.dto.rq.ToggleUserModeRq.class

	/**
	 * Какую дельту отдаем пользователю. Варианта два:
	 *
	 * 1) tinkoffDelta = (сумма покупок * текущую цену рынка) - (Сумма(лот * цену по каждой покупке))
	 * 2) candleDayDelta = (цена текущая - цена закрытия вчера) * кол-во акций в портфеле
	 *
	 * Какой отображаем?
	 */
	@Enumerated(EnumType.STRING)
	private DeltaMode deltaMode;

	/**
	 * Режим сортировки
	 */
	@Enumerated(EnumType.STRING)
	@Column()
	private SortMode sortMode = SortMode.ALL;

	/**
	 * Режим фильтрации.
	 */
	@Enumerated(EnumType.STRING)
	private FilterMode filterMode = FilterMode.NONE;


	@Column
	private LocalDateTime lastOperationTime;

	@Column
	private String lastOperation;

	/**
	 * Режим сортировки для ценных бумаг
	 */
	@Enumerated(EnumType.STRING)
	private InvestingSortMode investingSortMode;

	/**
	 * Режим фильтрации для ценных бумаг
	 */
	@Type(type = "jsonb")
	@Column(columnDefinition = "jsonb")
	private Map<String, String> investingFilterMode = new HashMap<>();
}
