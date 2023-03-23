package com.antonromanov.arnote.domain.salary.entity;

import com.antonromanov.arnote.domain.user.entity.ArNoteUser;
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

}
