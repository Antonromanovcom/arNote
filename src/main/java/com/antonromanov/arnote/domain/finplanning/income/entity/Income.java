package com.antonromanov.arnote.domain.finplanning.income.entity;

import com.antonromanov.arnote.old.model.ArNoteUser;
import lombok.*;
import javax.persistence.*;
import java.util.Date;


/**
 * Доход.
 */
@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "id")
@EqualsAndHashCode
@Table(name = "income")
public class Income {

	@Id
	@Column(name="id", nullable = false)
	/*@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "income_seq_gen")
	@SequenceGenerator(name = "income_seq_gen", sequenceName ="income_id_seq", allocationSize = 1)*/
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "income", nullable = true)
	private Integer income; // Сколько заработали в данный месяц

	@Column(name = "income_date", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date incomeDate; // Дата для понимания месяца и года прихода

	@Column(name = "is_bonus", nullable = false, columnDefinition = "boolean default false")
	private Boolean isBonus; // Это годовая премия?

	@Column(name = "description", nullable = true)
	private String description; // Описание

	@ManyToOne(cascade = CascadeType.REFRESH)
	private ArNoteUser user;
}
