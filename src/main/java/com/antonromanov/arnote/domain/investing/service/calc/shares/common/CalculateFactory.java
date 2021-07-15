package com.antonromanov.arnote.domain.investing.service.calc.shares.common;

import com.antonromanov.arnote.domain.investing.dto.response.enums.StockExchange;
import com.antonromanov.arnote.domain.investing.service.calc.shares.SharesCalcService;

/**
 * Фабрика для Сервис-Локатора, которая обеспечивает выбор, с методами какой имплементации сервиса калькуляции
 * акций работать: с имплементацией для MOEX (Московской Биржи) или с имплементацией для работы с данными,
 * запрашиваемыми из различных буржуйских API.
 */
public interface CalculateFactory {
    SharesCalcService getCalculator(StockExchange se);
}
