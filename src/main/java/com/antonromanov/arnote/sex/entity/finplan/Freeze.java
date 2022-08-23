package com.antonromanov.arnote.sex.entity.finplan;

/**
 * Фриз - это когда нам не надо рассчитывать баланс по месяцу - просто задаем жестко-хардкодно итог по месяцу и храним.
 */
/*@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "id")
@EqualsAndHashCode
@Table(name = "freeze_month")*/
public class Freeze {
/*
	@Id
	@Column(name="id", nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "freeze_seq_gen")
	@SequenceGenerator(name = "freeze_seq_gen", sequenceName ="freeze_id_seq", allocationSize = 1)
	private Long id;

	@Column(name = "amount", nullable = true)
	private Integer amount; // Значение фриза

	@Column(name = "start_date", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date startDate; // Дата фриза.

	@ManyToOne(cascade = CascadeType.REFRESH)
	private ArNoteUser user;

	public static Freeze $toDbEntity(FreezeRq rq, ArNoteUser user){
		return Freeze.builder()
				.amount(rq.getAmount())
				.startDate(localDateToDate(LocalDate.of(rq.getYear(), rq.getMonth(), 1)))
				.user(user)
				.build();
	}*/
}
