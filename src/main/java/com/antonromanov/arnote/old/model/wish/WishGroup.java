package com.antonromanov.arnote.old.model.wish;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table
public class WishGroup {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "group_seq_gen")
    @SequenceGenerator(name = "group_seq_gen", sequenceName ="group_id_seq", allocationSize = 1)
    private long id;

    private String groupName;
}

