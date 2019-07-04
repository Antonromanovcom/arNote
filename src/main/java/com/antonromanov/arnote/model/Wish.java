package com.antonromanov.arnote.model;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;

import lombok.*;
import org.hibernate.annotations.Type;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = "id")
@EqualsAndHashCode
@Table(name = "wishes", schema = "arnote", catalog = "postgres")
public class Wish {

// Основные поля

    @Id
    @Column(name="id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wishes_seq_gen")
    @SequenceGenerator(name = "wishes_seq_gen", sequenceName ="arnote.wishes_id_seq", allocationSize = 1)
    private long id;

    @Column(name = "wish", nullable = true, length = 255)
    private String wish;

    @Column(name = "archive", nullable = true)
    private Boolean ac;

    @Column(name = "lan", nullable = true)
    private Boolean lan;

    @Column(name = "logged", nullable = true)
    private Boolean logged;

    @Column(name = "price", nullable = true)
    private Integer price;

    @Column(name = "priority", nullable = true)
    private Integer priority;


    @Column(name = "lasthumidity", nullable = true)
    private Integer lasthumidity;

    @Column(name = "servertime", nullable = true)
    @Temporal(TemporalType.TIME)
    @Type(type="time")
    private Time servertime;

    @Column(name = "lastсontacttime", nullable = true)
    @Temporal(TemporalType.TIME)
    @Type(type="time")
    private Time lastсontacttime;

    @Column(name = "lastсontactdate", nullable = true)
    @Temporal(TemporalType.DATE)
    private Date lastсontactdate;

    @Column(name = "current", nullable = true)
    private Integer current;

    @Column(name = "amperage", nullable = true)
    private Integer amperage;

    @Column(name = "power", nullable = true)
    private Integer power;

    @Column(name = "consuming", nullable = true)
    private Long consuming;

}

