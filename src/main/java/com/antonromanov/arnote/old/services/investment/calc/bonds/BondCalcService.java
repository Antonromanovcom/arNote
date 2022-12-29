package com.antonromanov.arnote.old.services.investment.calc.bonds;

/**
 * Расчетный сервис с операциями по облигациям.
 */
public interface BondCalcService {
    /**
     * Подготовить список купонов в формате списка дивидендов.
     *
     * @return
     */
    //List<DividendRs> prepareCouponList(MoexRowsRs bondData);

    /**
     * Получить имя облигации.
     *
     * @param ticker
     * @return
     */
   // Optional<String> getBondName(String ticker);

    /**
     * Получить минимальный лот облигации или сколько куплено уже.
     *
     * @return
     */
  //  Integer getBondLot(Bond bond);

    /**
     * Получить купоны по облигации.
     *
     * @return
     */
   // ConsolidatedDividendsRs getCoupons(Bond bond);

    /**
     * Получить текущую цену облигации
     *
     * @param ticker - тикер бумаги.
     * @return
     */
   // Double getCurrentBondPrice(String ticker);

    /**
     * Получить текущую валюту облигации
     *
     * @param ticker - тикер бумаги.
     * @return
     */
  //  Currencies getBondCurrency(String ticker);

    /**
     * Запросить Облигации.
     *
     * @return
     */
  //  MoexDocumentRs getBondsByBoardGroup(String boardGroup);

    /**
     * Запросить Облигации по всем доскам сразу.
     *
     * @return
     */
  //  MoexDocumentRs getBonds();

    /**
     * Запросить Облигацию по тикеру.
     *
     * @param ticker - тикер
     * @return
     */
  //  Optional<MoexRowsRs> getBondDataByTicker(String ticker);

    /**
     * Подготовить финальную цену (цена * лот).
     *
     * @param bond
     * @param user
     * @return
     */
 //   Integer calculateFinalPrice(Bond bond);
}
