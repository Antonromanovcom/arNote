package com.antonromanov.arnote.domain.wish.dto.rs;

import com.antonromanov.arnote.domain.wish.enums.FilterMode;
import com.antonromanov.arnote.domain.wish.enums.SortMode;
import com.antonromanov.arnote.sex.model.investing.InvestingSortMode;
import com.antonromanov.arnote.sex.model.wish.enums.DeltaMode;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.Map;

@Data
@NoArgsConstructor
public class LocalUserRs {


	private long id;

	private String login;

	private String userRole;

	private Date creationDate;

	private String email;

	private String fullname; //todo: нормально переименовать

	private String viewMode;

	/**
	 * Какую дельту отдаем пользователю. Варианта два:
	 *
	 * 1) tinkoffDelta = (сумма покупок * текущую цену рынка) - (Сумма(лот * цену по каждой покупке))
	 * 2) candleDayDelta = (цена текущая - цена закрытия вчера) * кол-во акций в портфеле
	 *
	 * Какой отображаем?
	 */
	private DeltaMode deltaMode;

	/**
	 * Режим сортировки
	 */
	private SortMode sortMode;

	/**
	 * Режим фильтрации.
	 */
	private FilterMode filterMode;


	/**
	 * Режим сортировки для ценных бумаг
	 */
	private InvestingSortMode investingSortMode;

	/**
	 * Режим фильтрации для ценных бумаг
	 */
	private Map<String, String> investingFilterMode;
}
