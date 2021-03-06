package com.antonromanov.arnote.model.wish;

import com.antonromanov.arnote.model.ArNoteUser;
import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
	private Integer fullSlary;

	@Column(name = "residualsalary", nullable = true)
	private Integer residualSalary;

	@Column(name = "date", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date salarydate;

	@Column
//	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime salaryTimeStamp;

	@ManyToOne(cascade = CascadeType.ALL)
	private ArNoteUser user;

	public Salary(Integer fullSlary, Integer residualSalary) {
		this.fullSlary = fullSlary;
		this.residualSalary = residualSalary;
	}
}
