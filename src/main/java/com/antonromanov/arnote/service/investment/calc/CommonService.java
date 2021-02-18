package com.antonromanov.arnote.service.investment.calc;

import com.antonromanov.arnote.model.investing.Bond;
import com.antonromanov.arnote.model.investing.BondType;
import com.antonromanov.arnote.service.investment.calc.bonds.BondCalcService;
import com.antonromanov.arnote.service.investment.calc.shares.SharesCalcService;
import com.antonromanov.arnote.service.investment.calc.shares.common.CalculateFactory;
import org.springframework.stereotype.Service;

/**
 * Сервис обрабатывающий операции, например, выдачи текущей цены бумаги, общие для разных типов (акция / облигация)
 * и работающий как фабрика.
 */
@Service
public class CommonService {

    private final BondCalcService bondCalcService;
    private final CalculateFactory calcFactory;

    public CommonService(CalculateFactory calcFactory, BondCalcService bondCalcService) {
        this.calcFactory = calcFactory;
        this.bondCalcService = bondCalcService;
    }

    /**
     * Посчитать текущую стоимость бумаги.
     * @param bond
     * @return
     */
   public Double prepareCurrentPrice(Bond bond){

           return bond.getType() == BondType.SHARE ?
                   ((calcFactory.getCalculator(bond.getStockExchange())).getCurrentQuoteWith15MinuteUpdate(bond.getTicker()).getCurrentPrice()) :
                   bondCalcService.getCurrentBondPrice(bond.getTicker());

    }
}
