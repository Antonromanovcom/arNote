package com.antonromanov.arnote.sex.model;

import com.antonromanov.arnote.sex.model.investing.InvestingSortMode;
import com.antonromanov.arnote.sex.model.wish.SortMode;
import com.antonromanov.arnote.sex.model.wish.enums.DeltaMode;
import com.antonromanov.arnote.sex.model.wish.enums.FilterMode;
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
	private SortMode sortMode;

	/**
	 * Режим фильтрации.
	 */
	@Enumerated(EnumType.STRING)
	private FilterMode filterMode;


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
