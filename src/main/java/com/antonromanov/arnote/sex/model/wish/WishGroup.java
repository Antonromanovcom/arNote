package com.antonromanov.arnote.model.wish;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "id")
@EqualsAndHashCode
@Table
public class WishGroup {

    @Id
    @Column(name="id", nullable = true)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "group_seq_gen")
    @SequenceGenerator(name = "group_seq_gen", sequenceName ="group_id_seq", allocationSize = 1)
    private long id;

    //@OneToMany(mappedBy = "group", fetch = FetchType.EAGER)
    //private List<Wish> groupName;
    private String groupName;
}

