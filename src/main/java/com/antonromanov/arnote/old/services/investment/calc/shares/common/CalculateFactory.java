package com.antonromanov.arnote.old.services.investment.calc.shares.common;

/**
 * Фабрика для Сервис-Локатора, которая обеспечивает выбор, с методами какой имплементации сервиса калькуляции
 * акций работать: с имплементацией для MOEX (Московской Биржи) или с имплементацией для работы с данными,
 * запрашиваемыми из различных буржуйских API.
 */
//@Service
public interface CalculateFactory {
   // SharesCalcService getCalculator(StockExchange se);
}
