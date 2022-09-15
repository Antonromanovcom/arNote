package com.antonromanov.arnote.sex.entity.common;

import lombok.*;
import javax.persistence.*;
import java.sql.Date;

/*@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "id")
@EqualsAndHashCode
@Table(name = "calendar")*/
public class CalendarEntity { //todo: нужно что-то решать, у нас есть DTO, есть модели под парсинг JSON-а, есть транспортные ДТОшки, ДТОшки реквеста-респонса, Энтити. Нужно навЕСТЬ ПОРЯДОК В ЭТОМ !!!!!!!


  /*  @Id
    @Column(name="id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cal_entity_seq_gen")
    @SequenceGenerator(name = "cal_entity_seq_gen", sequenceName ="cal_entity_id_seq", allocationSize = 1)
    private Long id;

    @Column*/
  //  @Temporal(TemporalType.DATE)
    Date date;

    /*@Enumerated(EnumType.STRING)
    com.antonromanov.arnote.model.common.enums.CalendarType type;*/
}
