package com.antonromanov.arnote.sex.model.wish;

import javax.persistence.*;

import com.antonromanov.arnote.sex.model.ArNoteUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "wishes")
public class Wish {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wishes_seq_gen")
    @SequenceGenerator(name = "wishes_seq_gen", sequenceName = "wishes_id_seq", allocationSize = 1)
    @Getter
    @Setter
    private long id;

    @Column(name = "wish", length = 255)
    @Getter
    @Setter
    private String wishName;

    @Column(name = "price")
    @Getter
    @Setter
    private Integer price;

    @Column(name = "priority")
    @Getter
    @Setter
    private Integer priority;

    @Column(name = "archive")
    @Getter
    @Setter
    private Boolean archive = false;

    @Column(name = "description", length = 1024)
    @Getter
    @Setter
    private String description;

    @Column(name = "url", length = 1024)
    @Getter
    @Setter
    private String url;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.MERGE)
    @Setter
    private ArNoteUser user;

    @Column
    @Getter
    @Setter
    private Integer priorityGroup;

    @JsonIgnore
    @Column
    @Getter
    @Setter
    private Integer priorityGroupOrder;

    @Column
    @Temporal(TemporalType.DATE)
    @Setter
    private Date creationDate;

    @Column
    @Temporal(TemporalType.DATE)
    private Date realizationDate;

    @Column
    @Getter
    @Setter
    private Boolean realized = false;

    @ManyToOne(cascade = CascadeType.ALL)
    private WishGroup wishGroup;

    public Wish setPriorityAndReturnWish(Integer priority) {
        this.priority = priority;
        return this;
    }
}

