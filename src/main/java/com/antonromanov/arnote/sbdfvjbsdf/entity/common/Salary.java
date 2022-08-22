package com.antonromanov.arnote.entity.common;

import com.antonromanov.arnote.sbdfvjbsdf.dto.rq.SalaryRq;
import com.antonromanov.arnote.model.ArNoteUser;
import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "id")
@EqualsAndHashCode
@Table(name = "salary")
public class Salary {

	@Id
	@Column(name="id", nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "salary_seq_gen")
	@SequenceGenerator(name = "salary_seq_gen", sequenceName ="salary_id_seq", allocationSize = 1)
	private long id;

	@Column(name = "fullslary", nullable = true)
	private Integer fullSalary; // полная зарплата

	@Column(name = "residualsalary", nullable = true)
	private Integer residualSalary; // зарплата после трат различных

	@Column(name = "living_expenses", nullable = true)
	private Integer livingExpenses; // траты на жизнь: расходы на еду, транспорт и прочее

	@Column(name = "date", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date salarydate;

	@Column
	private LocalDateTime salaryTimeStamp;

	@ManyToOne(cascade = CascadeType.REFRESH)
	private ArNoteUser user;

	public Salary(Integer fullSalary, Integer residualSalary) {
		this.fullSalary = fullSalary;
		this.residualSalary = residualSalary;
	}

	public static Salary $toDbEntityWithCheck(SalaryRq payload, Salary salaryFromDb, ArNoteUser arNoteUser) {

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
	}
}
