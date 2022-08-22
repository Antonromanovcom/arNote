package com.antonromanov.arnote.entity.finplan;

import com.antonromanov.arnote.sbdfvjbsdf.dto.rq.GoalRq;
import com.antonromanov.arnote.model.ArNoteUser;
import lombok.*;
import javax.persistence.*;
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
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "goal_seq_gen")
	@SequenceGenerator(name = "goal_seq_gen", sequenceName ="goal_id_seq", allocationSize = 1)
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

	public static Goal $toDbEntity(GoalRq rq, ArNoteUser user){
		return Goal.builder()
				.id(rq.getId())
				.description(rq.getDescription())
				.price(rq.getPrice())
				.startDate(rq.getStartDate())
				.repayment(rq.getRepayment())
				.user(user)
				.build();
	}

	public static Goal $toDbEntityWithCheck(GoalRq rq, Goal existGoal, ArNoteUser user){
		return Goal.builder()
				.id(rq.getId())
				.description(rq.getDescription() == null ? existGoal.description : rq.getDescription())
				.price(rq.getPrice() == null ? existGoal.price : rq.getPrice())
				.startDate(rq.getStartDate() == null ? existGoal.startDate : rq.getStartDate())
				.repayment(existGoal.repayment)
				.user(user)
				.build();
	}
}
