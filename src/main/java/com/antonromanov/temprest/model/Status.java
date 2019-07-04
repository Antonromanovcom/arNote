package com.antonromanov.temprest.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.sql.Time;
import java.util.Date;

/**
 *  Статус сервера
 */
public class Status {


    private String who; // 220 есть?
    private boolean isAcOn; // 220 есть?
    private boolean isLanOn; // Сеть есть?
    private int lastTemperature; // Последняя температура
    private int lastHumidity; // Последняя влажность
    private Time serverTime; // Серверное время
    private Time lastContactTime; // Время последнего контакта
    private int current; // Напряжение
    private int amperage; // Сила тока (последние измерения)
    private int power; // Мощность потребляемая
    private long consuming; // Энергопотребеление (Ватт / час)
    private Boolean logged; // Энергопотребеление (Ватт / час)


    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date lastContactDate; // Дата последнего контакта

    public Status() {

    }

    public Status(boolean isAcOn, boolean isLanOn, int lastTemperature, int lastHumidity, Time serverTime, Time lastContactTime, int current, int amperage, int power, long consuming, Date lastContactDate) {
        this.isAcOn = isAcOn;
        this.isLanOn = isLanOn;
        this.lastTemperature = lastTemperature;
        this.lastHumidity = lastHumidity;
        this.serverTime = serverTime;
        this.lastContactTime = lastContactTime;
        this.current = current;
        this.amperage = amperage;
        this.power = power;
        this.consuming = consuming;
        this.lastContactDate = lastContactDate;
    }


    public Status(String who, boolean isAcOn, boolean isLanOn, int lastTemperature, int lastHumidity, Time serverTime, Time lastContactTime, int current, int amperage, int power, long consuming, Date lastContactDate, Boolean logged) {
        this.who = who;
        this.isAcOn = isAcOn;
        this.isLanOn = isLanOn;
        this.lastTemperature = lastTemperature;
        this.lastHumidity = lastHumidity;
        this.serverTime = serverTime;
        this.lastContactTime = lastContactTime;
        this.current = current;
        this.amperage = amperage;
        this.power = power;
        this.consuming = consuming;
        this.lastContactDate = lastContactDate;
        this.logged = logged;
    }

    public Status(String who, boolean isAcOn, boolean isLanOn, int lastTemperature, int lastHumidity, Time serverTime, Time lastContactTime, int current, int amperage, int power, int consuming, Date lastContactDate) {
        this.who = who;
        this.isAcOn = isAcOn;
        this.isLanOn = isLanOn;
        this.lastTemperature = lastTemperature;
        this.lastHumidity = lastHumidity;
        this.serverTime = serverTime;
        this.lastContactTime = lastContactTime;
        this.current = current;
        this.amperage = amperage;
        this.power = power;
        this.consuming = consuming;
        this.lastContactDate = lastContactDate;
    }

    public boolean isAcOn() {
        return isAcOn;
    }

    public void setAcOn(boolean acOn) {
        isAcOn = acOn;
    }


public boolean isLogged() {
        return logged;
    }

    public void setLogged(boolean logged) {
        this.logged = logged;
    }

    public boolean isLanOn() {
        return isLanOn;
    }

    public void setLanOn(boolean lanOn) {
        isLanOn = lanOn;
    }

    public int getLastTemperature() {
        return lastTemperature;
    }

    public void setLastTemperature(int lastTemperature) {
        this.lastTemperature = lastTemperature;
    }

    public int getLastHumidity() {
        return lastHumidity;
    }

    public void setLastHumidity(int lastHumidity) {
        this.lastHumidity = lastHumidity;
    }

    public Time getServerTime() {
        return serverTime;
    }

    public void setServerTime(Time serverTime) {
        this.serverTime = serverTime;
    }

    public Time getLastContactTime() {
        return lastContactTime;
    }

    public void setLastContactTime(Time lastContactTime) {
        this.lastContactTime = lastContactTime;
    }

    public Date getLastContactDate() {
        return lastContactDate;
    }

    public void setLastContactDate(Date lastContactDate) {
        this.lastContactDate = lastContactDate;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getAmperage() {
        return amperage;
    }

    public void setAmperage(int amperage) {
        this.amperage = amperage;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public long getConsuming() {
        return consuming;
    }

    public void setConsuming(long consuming) {
        this.consuming = consuming;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    @Override
    public String toString() {
        return "Status{" +
                "isAcOn=" + isAcOn +
                ", isLanOn=" + isLanOn +
                ", lastTemperature=" + lastTemperature +
                ", lastHumidity=" + lastHumidity +
                ", serverTime=" + serverTime +
                ", lastContactTime=" + lastContactTime +
                ", current=" + current +
                ", amperage=" + amperage +
                ", power=" + power +
                ", consuming=" + consuming +
                ", lastContactDate=" + lastContactDate +
                '}';
    }
}

