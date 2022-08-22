package com.antonromanov.arnote.entity;

import lombok.*;
import javax.persistence.*;


/**
 * Группа желаний (одежда, еда, ...).
 */
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
    private String groupName;
}

