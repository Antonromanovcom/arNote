package com.antonromanov.temprest.model;

import javax.persistence.*;
import java.sql.Time;
import java.time.LocalTime;
import java.util.Date;

/**
 * Основной температурный энтити. Тут все понятно.
 *
 */
@Entity
@Table(name="temperature_copy", schema = "arduino", catalog = "postgres")
public class Temperature {

    @Id
    @Column(name="id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "temp_seq_gen")
    @SequenceGenerator(name = "temp_seq_gen", sequenceName ="arduino.temp_id_seq", allocationSize = 1)
    private Integer id;


    @Column(name="name")
    private String name;

    @Column(name="temperature")
    private Double temperature;

    @Column(name = "datecreated", nullable = true)
    @Temporal(TemporalType.DATE)
    private Date dateCreated;

    @Column(name="timecreated")
    private Time timeCreated;


    @Column(name = "status")
    private String status;


    public Temperature() {
    }

    public Temperature(Double newTemp) {
        this.temperature = newTemp;
        this.name = "from Arduino Home";
        this.dateCreated = new Date();
        this.timeCreated = java.sql.Time.valueOf(LocalTime.now());
    }

    public Temperature(Double newTemp, String status) {
        this.temperature = newTemp;
        this.status = status;
        this.name = "from Arduino Home";
        this.dateCreated = new Date();
        this.timeCreated = java.sql.Time.valueOf(LocalTime.now());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Time getTimeCreated() {

        return timeCreated;
    }

    public void setTimeCreated(Time timeCreated) {
        this.timeCreated = timeCreated;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", temperature=" + temperature +
                ", dateCreated=" + dateCreated +
                ", timeCreated=" + timeCreated +
                '}';
    }
}
