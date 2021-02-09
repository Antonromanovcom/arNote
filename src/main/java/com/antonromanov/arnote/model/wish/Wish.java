package com.antonromanov.arnote.model.wish;

import javax.persistence.*;

import com.antonromanov.arnote.model.ArNoteUser;
import com.antonromanov.arnote.model.wish.WishGroup;
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

    @Column(name = "wish")
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

    @ManyToOne(cascade = CascadeType.ALL)
    private ArNoteUser user;

    @Column
    private Integer priorityGroup;

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


    public Wish(String wish, Integer price, Integer priority, Integer priorityGroup, Boolean ac, String description,
                String url, ArNoteUser user, Date creationDate) {
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

    public Wish(long id, String wish, int price, int priority, boolean ac, String description, String url, ArNoteUser user) {

        this.wish = wish;
        this.price = price;
        this.priority = priority;
        this.ac = ac;
        this.description = description;
        this.url = url;
        this.user = user;
        this.id = id;

    }

    //Todo: с таким большим количеством конструкторов явно надо что-то делать. Может быть добавить билдер в класс или добавить метод конвертации
    public Wish(String wish, int price, int priority, boolean archive, String description, String url, ArNoteUser user) {
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

