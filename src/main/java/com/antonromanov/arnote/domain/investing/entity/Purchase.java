package com.antonromanov.arnote.domain.investing.entity;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table
public class Purchase {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private Double price;
    @Column
    private Integer lot;
    @Column
    private LocalDate purchaseDate;
}
