package com.antonromanov.arnote.domain.finplanning.goal.entity;

import javax.persistence.*;
import com.antonromanov.arnote.old.model.ArNoteUser;
import lombok.*;
import java.util.Date;

/**
 * Трата.
 *
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "id")
@EqualsAndHashCode
@Table(name = "goal")
public class Goal {

	@Id
	@Column(name="id", nullable = false)
	/*@Id
	@Column(name="id", nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "goal_seq_gen")
	@SequenceGenerator(name = "goal_seq_gen", sequenceName ="goal_id_seq", allocationSize = 1)*/
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "description", nullable = true)
	private String description; // Что за покупка вообще

	@Column(name = "price", nullable = true)
	private Integer price; // Общий платеж по кредиту

	@Column(name = "start_date", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date startDate; // Дата

	@Column(name = "repayment", nullable = true)
	private Long repayment; // Погашение кредита. Указывается ID кредита.

	@ManyToOne(cascade = CascadeType.REFRESH)
	private ArNoteUser user;
}