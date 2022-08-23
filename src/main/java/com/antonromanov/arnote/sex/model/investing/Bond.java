package com.antonromanov.arnote.sex.model.investing;

import com.antonromanov.arnote.sex.model.investing.response.enums.StockExchange;
import com.antonromanov.arnote.sex.model.ArNoteUser;
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
    private com.antonromanov.arnote.sex.model.investing.BondType type; // тип бумаги - облигация, фонд, акция

    @Column
    @Enumerated(EnumType.STRING)
    private StockExchange stockExchange; // Биржа

    @Column
    private Boolean isBought; // факт / План

    @ManyToOne(cascade = CascadeType.DETACH)
    private ArNoteUser user;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "post_id")
    private List<com.antonromanov.arnote.model.investing.Purchase> purchaseList = new ArrayList<>();
}

