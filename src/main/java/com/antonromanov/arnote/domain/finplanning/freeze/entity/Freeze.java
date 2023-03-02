package com.antonromanov.arnote.domain.finplanning.freeze.entity;

import com.antonromanov.arnote.old.model.ArNoteUser;
import lombok.*;
import javax.persistence.*;
import java.util.Date;

/**
 * Фриз - это когда нам не надо рассчитывать баланс по месяцу - просто задаем жестко-хардкодно итог по месяцу и храним.
 */
@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "id")
@EqualsAndHashCode
@Table(name = "freeze_month")
public class Freeze {

	@Id
	@Column(name="id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	/*@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "freeze_seq_gen")
	@SequenceGenerator(name = "freeze_seq_gen", sequenceName ="freeze_id_seq", allocationSize = 1)*/
	private Long id;

	@Column(name = "amount", nullable = true)
	private Integer amount; // Значение фриза

	@Column(name = "start_date", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date startDate; // Дата фриза.

	@ManyToOne(cascade = CascadeType.REFRESH)
	private ArNoteUser user;
}
