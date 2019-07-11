package com.antonromanov.arnote.model;

import lombok.*;
import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "id")
@EqualsAndHashCode
@Table(name = "salary", schema = "arnote", catalog = "postgres")
public class Salary {

	@Id
	@Column(name="id", nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "salary_seq_gen")
	@SequenceGenerator(name = "salary_seq_gen", sequenceName ="arnote.salary_id_seq", allocationSize = 1)
	private long id;

	@Column(name = "fullslary", nullable = true)
	private Integer fullSlary;

	@Column(name = "residualsalary", nullable = true)
	private Integer residualSalary;

	@Column(name = "date", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date salarydate;

	public Salary(Integer fullSlary, Integer residualSalary) {
		this.fullSlary = fullSlary;
		this.residualSalary = residualSalary;
	}
}
