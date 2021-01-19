package com.antonromanov.arnote.model.investing;

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
    @GeneratedValue
    private Long id;
    @Column
    private Double price;
    @Column
    private Integer lot;
    @Column
    private LocalDate purchaseDate;
}
