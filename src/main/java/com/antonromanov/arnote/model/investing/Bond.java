package com.antonromanov.arnote.model.investing;

import com.antonromanov.arnote.model.LocalUser;
import com.antonromanov.arnote.model.investing.response.enums.StockExchange;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "bond")
public class Bond {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bonds_seq_gen")
    @SequenceGenerator(name = "bonds_seq_gen", sequenceName = "bonds_id_seq", allocationSize = 1)
    private long id;

    @Column
    private String ticker; // биржевой тикер

    @Column
    @Enumerated(EnumType.STRING)
    private BondType type; // тип бумаги - облигация, фонд, акция

    @Column
    @Enumerated(EnumType.STRING)
    private StockExchange stockExchange; // Биржа

    @Column
    private Boolean isBought; // факт / План

    @ManyToOne(cascade = CascadeType.ALL)
    private LocalUser user;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "post_id")
    private List<Purchase> purchaseList = new ArrayList<>();
}

