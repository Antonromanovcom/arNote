package com.antonromanov.arnote.entity.finplan;

import com.antonromanov.arnote.dto.rq.CreditRq;
import com.antonromanov.arnote.model.ArNoteUser;
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
@Table(name = "credit")
@Builder
public class Credit {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "credit_seq_gen")
    @SequenceGenerator(name = "credit_seq_gen", sequenceName = "credit_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "start_amount", nullable = true)
    private Integer startAmount; // Общая сумма кредита

    @Column(name = "full_month_pay", nullable = true)
    private Integer fullPayPerMonth; // Общий платеж по кредиту

    @Column(name = "real_month_pay", nullable = true)
    private Integer realPayPerMonth; // Сколько уходит на погашение кредита после вычета процентов

    @Column(name = "credit_number", nullable = true)
    private Integer creditNumber; // Номер кредита. Разрешено всего 5

    @Column(name = "start_date", nullable = true)
    @Temporal(TemporalType.DATE)
    private Date startDate; // Дата взятия кредита

    @ManyToOne(cascade = CascadeType.REFRESH)
    private ArNoteUser user;

    @Column(name = "credit_desc", nullable = true)
    private String description;

    public static Credit $toDbEntity(CreditRq rq, int nextLoanNumber, ArNoteUser user) {
        return Credit.builder()
                .startAmount(rq.getStartAmount())
                .fullPayPerMonth(rq.getFullPayPerMonth())
                .realPayPerMonth(rq.getRealPayPerMonth())
                .creditNumber(nextLoanNumber)
                .startDate(rq.getStartDate())
                .user(user)
                .description(rq.getDesc())
                .id(rq.getId())
                .build();
    }

    public static Credit $toDbEntityWithCheck(CreditRq newLoan, Credit oldLoan, ArNoteUser user) {
        return Credit.builder()
                .startAmount(newLoan.getStartAmount() == null ? oldLoan.getStartAmount() : newLoan.getStartAmount())
                .fullPayPerMonth(newLoan.getFullPayPerMonth() == null ? oldLoan.getFullPayPerMonth() : newLoan.getFullPayPerMonth())
                .realPayPerMonth(newLoan.getRealPayPerMonth() == null ? oldLoan.getRealPayPerMonth() : newLoan.getRealPayPerMonth())
                .creditNumber(oldLoan.getCreditNumber())
                .startDate(newLoan.getStartDate() == null ? oldLoan.getStartDate() : newLoan.getStartDate())
                .user(user)
                .description(newLoan.getDesc() == null ? oldLoan.getDescription() : newLoan.getDesc())
                .id(oldLoan.getId())
                .build();
    }

}
