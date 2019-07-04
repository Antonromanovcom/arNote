package com.antonromanov.temprest.model;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;
import org.hibernate.annotations.Type;


/**
 * Основной Энтити завязанный на логи жизнедеятельности.
 */
@Entity
@Table(name = "logs", schema = "arduino", catalog = "postgres")
public class Logs {

// Основные поля

    @Id
    @Column(name="id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "logs_seq_gen")
    @SequenceGenerator(name = "logs_seq_gen", sequenceName ="arduino.logs_id_seq", allocationSize = 1)
    private long id;

    @Column(name = "who", nullable = true, length = 255)
    private String who;

    @Column(name = "ac", nullable = true)
    private Boolean ac;

    @Column(name = "lan", nullable = true)
    private Boolean lan;

    @Column(name = "logged", nullable = true)
    private Boolean logged;

    @Column(name = "lasttemperature", nullable = true)
    private Integer lasttemperature;

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

    public Logs() {
    }

    public Logs(Integer lasttemperature) {

        this.who = "dao-test";
        this.ac = true;
        this.lan = true;
        this.logged = true;
        this.lasthumidity = 45;
        this.lasttemperature = lasttemperature;
        this.servertime = null;
        this.lastсontacttime = null;
        this.lastсontactdate = null;
        this.current = null;
        this.amperage = null;
        this.power = null;
        this.consuming = null;
        this.logged = null;
    }


    public Logs(Status log) {

        this.who = log.getWho();
        this.ac = log.isAcOn();
        this.lan = log.isLanOn();
        this.logged = log.isLogged();
        this.lasthumidity = log.getLastHumidity();
        this.lasttemperature = log.getLastTemperature();
        this.servertime = log.getServerTime();
        this.lastсontacttime = log.getLastContactTime();
        this.lastсontactdate = log.getLastContactDate();
        this.current = log.getCurrent();
        this.amperage = log.getAmperage();
        this.power = log.getPower();
        this.consuming = log.getConsuming();
        this.logged = log.isLogged();
    }

    // Геттеры - Сеттеры

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public Boolean getAc() {
        return ac;
    }

    public Boolean isLogged() {
        return logged;
    }

    public void setLogged(Boolean logged) {
        this.logged = logged;
    }

    public void setAc(Boolean ac) {
        this.ac = ac;
    }

    public Boolean getLan() {
        return lan;
    }

    public void setLan(Boolean lan) {
        this.lan = lan;
    }

    public Integer getLasttemperature() {
        return lasttemperature;
    }

    public void setLasttemperature(Integer lasttemperature) {
        this.lasttemperature = lasttemperature;
    }

    public Integer getLasthumidity() {
        return lasthumidity;
    }

    public void setLasthumidity(Integer lasthumidity) {
        this.lasthumidity = lasthumidity;
    }

    public Time getServertime() {
        return servertime;
    }

    public void setServertime(Time servertime) {
        this.servertime = servertime;
    }

    public Time getLastсontacttime() {
        return lastсontacttime;
    }

    public void setLastсontacttime(Time lastсontacttime) {
        this.lastсontacttime = lastсontacttime;
    }

    public Date getLastсontactdate() {
        return lastсontactdate;
    }

    public void setLastсontactdate(Date lastсontactdate) {
        this.lastсontactdate = lastсontactdate;
    }

    public Integer getCurrent() {
        return current;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public Integer getAmperage() {
        return amperage;
    }

    public void setAmperage(Integer amperage) {
        this.amperage = amperage;
    }

    public Integer getPower() {
        return power;
    }

    public void setPower(Integer power) {
        this.power = power;
    }

    public Long getConsuming() {
        return consuming;
    }

    public void setConsuming(Long consuming) {
        this.consuming = consuming;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Logs that = (Logs) o;

        if (id != that.id) return false;
        if (who != null ? !who.equals(that.who) : that.who != null) return false;
        if (ac != null ? !ac.equals(that.ac) : that.ac != null) return false;
        if (lan != null ? !lan.equals(that.lan) : that.lan != null) return false;
        if (lasttemperature != null ? !lasttemperature.equals(that.lasttemperature) : that.lasttemperature != null)
            return false;
        if (lasthumidity != null ? !lasthumidity.equals(that.lasthumidity) : that.lasthumidity != null) return false;
        if (servertime != null ? !servertime.equals(that.servertime) : that.servertime != null) return false;
        if (lastсontacttime != null ? !lastсontacttime.equals(that.lastсontacttime) : that.lastсontacttime != null)
            return false;
        if (lastсontactdate != null ? !lastсontactdate.equals(that.lastсontactdate) : that.lastсontactdate != null)
            return false;
        if (current != null ? !current.equals(that.current) : that.current != null) return false;
        if (amperage != null ? !amperage.equals(that.amperage) : that.amperage != null) return false;
        if (power != null ? !power.equals(that.power) : that.power != null) return false;
        if (consuming != null ? !consuming.equals(that.consuming) : that.consuming != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (who != null ? who.hashCode() : 0);
        result = 31 * result + (ac != null ? ac.hashCode() : 0);
        result = 31 * result + (lan != null ? lan.hashCode() : 0);
        result = 31 * result + (lasttemperature != null ? lasttemperature.hashCode() : 0);
        result = 31 * result + (lasthumidity != null ? lasthumidity.hashCode() : 0);
        result = 31 * result + (servertime != null ? servertime.hashCode() : 0);
        result = 31 * result + (lastсontacttime != null ? lastсontacttime.hashCode() : 0);
        result = 31 * result + (lastсontactdate != null ? lastсontactdate.hashCode() : 0);
        result = 31 * result + (current != null ? current.hashCode() : 0);
        result = 31 * result + (amperage != null ? amperage.hashCode() : 0);
        result = 31 * result + (power != null ? power.hashCode() : 0);
        result = 31 * result + (consuming != null ? consuming.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Logs{" +
                "id=" + id +
                ", who='" + who + '\'' +
                ", ac=" + ac +
                ", lan=" + lan +
                ", logged=" + logged +
                ", lasttemperature=" + lasttemperature +
                ", lasthumidity=" + lasthumidity +
                ", servertime=" + servertime +
                ", lastсontacttime=" + lastсontacttime +
                ", lastсontactdate=" + lastсontactdate +
                ", current=" + current +
                ", amperage=" + amperage +
                ", power=" + power +
                ", consuming=" + consuming +
                '}';
    }
}

