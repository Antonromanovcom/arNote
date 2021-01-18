package com.antonromanov.arnote.model.investing;

import com.antonromanov.arnote.model.LocalUser;
import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "id")
@EqualsAndHashCode
@Table(name = "bond")
public class Bond {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bonds_seq_gen")
    @SequenceGenerator(name = "bonds_seq_gen", sequenceName = "bonds_id_seq", allocationSize = 1)
    private long id;

    @Column
    private String ticker; // биржевой тикер

    @Column
    private String type; // тип бумаги - облигация, фонд, акция

    @Column
    private String description; //Развернутое описание

    /*@Column
    private List<Dividend> divSum; // Сумма дивов за предыдущий год*/

    @Column
    private String stockExchange; // Биржа

    @Column
    private Double price; // Цена / Номинал на момент покупки

    @Column
    private Integer lot; // Сколько акций в лоте

    @Column
    private Long fullPeriodDelta; // Рост за весь период

    @Column
    private Boolean isBought; // факт / План

    @Column
    private Long dayDelta; // рост/падение за сегодня

    @Column
    private Long deltaFromPurchaseDate; // рост/падение за сегодня

    @Column
    private LocalDateTime purchaseDate; // дата и время покупки

    @ManyToOne(cascade = CascadeType.ALL)
    private LocalUser user;
}

