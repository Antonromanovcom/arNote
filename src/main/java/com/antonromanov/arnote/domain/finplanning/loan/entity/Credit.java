package com.antonromanov.arnote.domain.finplanning.loan.entity;

import javax.persistence.*;
import com.antonromanov.arnote.old.model.ArNoteUser;
import lombok.*;
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
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "credit_seq_gen")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // @SequenceGenerator(name = "credit_seq_gen", sequenceName = "credit_pkey", allocationSize = 1)
    private Long id;

    @Column(name = "start_amount")
    private Integer startAmount; // Общая сумма кредита

    @Column(name = "full_month_pay")
    private Integer fullPayPerMonth; // Общий платеж по кредиту

    @Column(name = "real_month_pay")
    private Integer realPayPerMonth; // Сколько уходит на погашение кредита после вычета процентов

    @Column(name = "credit_number")
    private Integer creditNumber; // Номер кредита. Разрешено всего 5

    @Column(name = "start_date")
    @Temporal(TemporalType.DATE)
    private Date startDate; // Дата взятия кредита

    @ManyToOne(cascade = CascadeType.REFRESH)
    private ArNoteUser user;

    @Column(name = "credit_desc")
    private String description;


    //todo: убрать
   /*

    public static Credit $toDbEntityWithCheck(CreditRq newLoan, Credit oldLoan, ArNoteUser user) {
        return Credit.builder()
                .startAmount(newLoan.getStartAmount() == null ? oldLoan.getStartAmount() : newLoan.getStartAmount())
                .fullPayPerMonth(newLoan.getFullPayPerMonth() == null ? oldLoan.getFullPayPerMonth() : newLoan.getFullPayPerMonth())
                .realPayPerMonth(newLoan.getRealPayPerMonth() == null ? oldLoan.getRealPayPerMonth() : newLoan.getRealPayPerMonth())
                .creditNumber(newLoan.getSlotNumber() == null ? oldLoan.getCreditNumber() : newLoan.getSlotNumber())
                .startDate(newLoan.getStartDate() == null ? oldLoan.getStartDate() : newLoan.getStartDate())
                .user(user)
                .description(newLoan.getDesc() == null ? oldLoan.getDescription() : newLoan.getDesc())
                .id(oldLoan.getId())
                .build();
    }*/
}
