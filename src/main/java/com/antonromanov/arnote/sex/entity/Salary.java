package com.antonromanov.arnote.sex.entity;

/*@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "id")
@EqualsAndHashCode
@Table(name = "salary")*/
public class Salary {

	/*@Id
	@Column(name="id", nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "salary_seq_gen")
	@SequenceGenerator(name = "salary_seq_gen", sequenceName ="salary_id_seq", allocationSize = 1)
	private long id;

	@Column(name = "fullslary", nullable = true)
	private Integer fullSlary; //todo: переименовать

	@Column(name = "residualsalary", nullable = true)
	private Integer residualSalary;

	@Column(name = "date", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date salarydate; //todo: переименовать

	@Column
	private LocalDateTime salaryTimeStamp;

	@ManyToOne(cascade = CascadeType.ALL)
	private com.antonromanov.arnote.sex.model.ArNoteUser user;

	public Salary(Integer fullSlary, Integer residualSalary) {
		this.fullSlary = fullSlary;
		this.residualSalary = residualSalary;
	}

	public Salary(NewSalaryRq newSalaryRq, com.antonromanov.arnote.sex.model.ArNoteUser user) {
		this.fullSlary = newSalaryRq.getFullSalary();
		this.residualSalary = newSalaryRq.getResidualSalary();
		this.user = user;
		Date currentDate = new Date();
		this.setSalarydate(currentDate);
	}*/
}
