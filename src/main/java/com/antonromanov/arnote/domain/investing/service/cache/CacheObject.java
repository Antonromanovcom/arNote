package com.antonromanov.arnote.domain.investing.service.cache;


import lombok.Getter;

/**
 * Обобщенный объект для хранения в кэше разных сущностей
 */
public class CacheObject<T> {

    /**
     * Хранимый объект.
     */
    @Getter
    private T t;

    /**
     * Класс для последующего кастинга.
     */
    @Getter
    private Class<T> clazz;

    public CacheObject(T t, Class<T> clazz) {
        this.t = t;
        this.clazz = clazz;
    }
}
