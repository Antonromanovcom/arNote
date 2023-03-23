package com.antonromanov.arnote.domain.investing.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table
public class Purchase {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private Double price;
    @Column
    private Integer lot;
    @Column
    private LocalDate purchaseDate;

}
