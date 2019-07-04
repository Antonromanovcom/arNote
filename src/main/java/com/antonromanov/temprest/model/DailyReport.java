package com.antonromanov.temprest.model;

import java.util.Date;

/**
 * Класс для отчетности по температуре
 *
 * Created by Anton on 08.04.2019 at 14:49.
 */
public class DailyReport {

    private Date measureDate; // Дата измерения
    private Double dayTemp; // Дневная температура
    private Double nightTemp; // Ночная температура

    public DailyReport() {
    }

    public DailyReport(Date measureDate, Double dayTemp, Double nightTemp) {
        this.measureDate = measureDate;
        this.dayTemp = dayTemp;
        this.nightTemp = nightTemp;
    }

    public Date getMeasureDate() {
        return measureDate;
    }

    public void setMeasureDate(Date measureDate) {
        this.measureDate = measureDate;
    }

    public Double getDayTemp() {
        return dayTemp;
    }

    public void setDayTemp(Double dayTemp) {
        this.dayTemp = dayTemp;
    }

    public Double getNightTemp() {
        return nightTemp;
    }

    public void setNightTemp(Double nightTemp) {
        this.nightTemp = nightTemp;
    }

    @Override
    public String toString() {
        return "DailyReport{" +
                "measureDate=" + measureDate +
                ", dayTemp=" + dayTemp +
                ", nightTemp=" + nightTemp +
                '}';
    }
}