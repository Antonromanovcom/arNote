package com.antonromanov.arnote.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "id")
@EqualsAndHashCode
@Table(name = "wishes")
public class Wish {

    @Id
    @Column(name="id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wishes_seq_gen")
    @SequenceGenerator(name = "wishes_seq_gen", sequenceName ="wishes_id_seq", allocationSize = 1)
    private long id;

    @Column(name = "wish", nullable = true, length = 255)
    private String wish; //todo: переименовать в классе в wishName

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

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    private LocalUser user;

    @Column
    private Integer priorityGroup;

    @JsonIgnore
    @Column
    private Integer priorityGroupOrder;

    @Column
    @Temporal(TemporalType.DATE)
    private Date creationDate;

    @Column
    @Temporal(TemporalType.DATE)
    private Date realizationDate;

    @Column
    private Boolean realized;

    @ManyToOne(cascade = CascadeType.ALL)
    private WishGroup wishGroup;

    public Wish setPriorityAndReturnWish(Integer priority) {
        this.priority = priority;
        return this;
    }

    public Wish(String wish, Integer price, Integer priority, Integer priorityGroup, Boolean ac, String description,
                String url, LocalUser user, Date creationDate) {
        this.wish = wish;
        this.price = price;
        this.priority = priority;
        this.priorityGroup = priorityGroup;
        this.ac = ac;
        this.description = description;
        this.url = url;
        this.user = user;
        this.creationDate = creationDate;
    }

    public Wish(long id, String wish, int price, int priority, boolean ac, String description, String url, LocalUser user) {

        this.wish = wish;
        this.price = price;
        this.priority = priority;
        this.ac = ac;
        this.description = description;
        this.url = url;
        this.user = user;
        this.id = id;

    }

    //todo: с таким большим количеством конструкторов явно надо что-то делать. Может быть добавить билдер в класс или добавить метод конвертации
    public Wish(String wish, int price, int priority, boolean archive, String description, String url, LocalUser user) {
        this.wish = wish;
        this.price = price;
        this.priority = priority;
        this.priorityGroup = null;
        this.ac = archive;
        this.description = description;
        this.url = url;
        this.user = user;
    }
}

