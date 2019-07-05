package com.antonromanov.arnote.model;

import javax.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "id")
@EqualsAndHashCode
@Table(name = "wishes", schema = "arnote", catalog = "postgres")
public class Wish {

    @Id
    @Column(name="id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wishes_seq_gen")
    @SequenceGenerator(name = "wishes_seq_gen", sequenceName ="arnote.wishes_id_seq", allocationSize = 1)
    private long id;

    @Column(name = "wish", nullable = true, length = 255)
    private String wish;

    @Column(name = "price", nullable = true)
    private Integer price;

    @Column(name = "priority", nullable = true)
    private Integer priority;

    @Column(name = "archive", nullable = true)
    private Boolean ac;

    @Column(name = "description", nullable = true, length = 1024)
    private String description;

    @Column(name = "url", nullable = true, length = 1024)
    private String url;

    public Wish(String wish, Integer price, Integer priority, Boolean ac, String description, String url) {
        this.wish = wish;
        this.price = price;
        this.priority = priority;
        this.ac = ac;
        this.description = description;
        this.url = url;
    }
}

