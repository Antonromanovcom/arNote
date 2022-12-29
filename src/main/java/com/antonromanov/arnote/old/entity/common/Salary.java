package com.antonromanov.arnote.old.entity.common;

import com.antonromanov.arnote.old.model.ArNoteUser;
import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "salary")
public class Salary {

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter
	@Setter
	private long id;

	@Column(name = "fullslary", nullable = true)
	private Integer fullSalary; // полная зарплата //todo: переименовать

	@Column(name = "residualsalary", nullable = true)
	private Integer residualSalary; // зарплата после трат различных

	@Column(name = "living_expenses", nullable = true)
	private Integer livingExpenses; // траты на жизнь: расходы на еду, транспорт и прочее

	@Column(name = "date", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date salarydate; //todo: переименовать

	@Column
	private LocalDateTime salaryTimeStamp;

	@ManyToOne(cascade = CascadeType.REFRESH)
	private ArNoteUser user;

	/*public static Salary $toDbEntityWithCheck(SalaryRq payload, Salary salaryFromDb, ArNoteUser arNoteUser) { //todo: все это закаменченное в маппер и убрать отседова

		return Salary.builder()
				.fullSalary(payload.getFullSalary() == null ? salaryFromDb.getFullSalary() : payload.getFullSalary())
				.id(salaryFromDb.getId())
				.livingExpenses(payload.getLivingExpenses() == null ? salaryFromDb.getLivingExpenses() : payload.getLivingExpenses())
				.residualSalary(payload.getResidualSalary() == null ? salaryFromDb.getResidualSalary() : payload.getResidualSalary())
				.salaryTimeStamp(payload.getSalaryDate() == null ? salaryFromDb.getSalaryTimeStamp() : (payload.getSalaryDate()).toInstant()
						.atZone(ZoneId.systemDefault())
						.toLocalDateTime())
				.user(arNoteUser)
				.build();
	}

	public static Salary $toDbEntity(SalaryRq payload, ArNoteUser arNoteUser) {
		return Salary.builder()
				.fullSalary(payload.getFullSalary())
				.livingExpenses(payload.getLivingExpenses())
				.residualSalary(payload.getResidualSalary())
				.salaryTimeStamp((payload.getSalaryDate()).toInstant()
						.atZone(ZoneId.systemDefault())
						.toLocalDateTime())
				.user(arNoteUser)
				.build();
	}*/
}
