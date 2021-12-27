package com.antonromanov.arnote.services.investment.calc.shares.common;

import com.antonromanov.arnote.model.investing.response.enums.StockExchange;
import com.antonromanov.arnote.services.investment.calc.shares.SharesCalcService;

/**
 * Фабрика для Сервис-Локатора, которая обеспечивает выбор, с методами какой имплементации сервиса калькуляции
 * акций работать: с имплементацией для MOEX (Московской Биржи) или с имплементацией для работы с данными,
 * запрашиваемыми из различных буржуйских API.
 */
public interface CalculateFactory {
    SharesCalcService getCalculator(StockExchange se);
}
