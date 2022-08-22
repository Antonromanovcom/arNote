package com.antonromanov.arnote.sbdfvjbsdf.utils;

import com.antonromanov.arnote.entity.common.Salary;
import com.antonromanov.arnote.exceptions.*;
import com.antonromanov.arnote.model.ArNoteUser;
import com.antonromanov.arnote.model.investing.BondType;
import com.antonromanov.arnote.model.investing.InvestingFilterMode;
import com.antonromanov.arnote.model.investing.Purchase;
import com.antonromanov.arnote.model.investing.external.requests.ForeignRequests;
import com.antonromanov.arnote.model.investing.external.requests.MoexRestTemplateOperation;
import com.antonromanov.arnote.model.investing.response.BondRs;
import com.antonromanov.arnote.model.investing.response.FoundInstrumentRs;
import com.antonromanov.arnote.model.investing.response.enums.Currencies;
import com.antonromanov.arnote.model.investing.response.enums.StockExchange;
import com.antonromanov.arnote.model.investing.response.enums.TinkoffDeltaFinalValuesType;
import com.antonromanov.arnote.model.investing.response.xmlpart.common.CommonMoexDoc;
import com.antonromanov.arnote.model.investing.response.xmlpart.currentquote.MoexDocumentRs;
import com.antonromanov.arnote.model.investing.response.xmlpart.currentquote.MoexRowsRs;
import com.antonromanov.arnote.model.wish.Wish;
import com.antonromanov.arnote.model.wish.WishDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.Signature;
import org.joda.time.DateTime;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import java.io.*;
import java.net.URL;
import java.time.*;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import static org.apache.commons.lang3.StringUtils.isBlank;


/**
 * Тут собраны основные утилиты.
 */
@Slf4j
public class ArNoteUtils { //todo: надо будет разнести отдельно wish-утилиты и инвест-утилиты


    private final static String DIGITAL_PATTERN = "([0-9]+)";
    private final static String XML_SPECIAL_DATE_PATTERN = "^(0[1-9]|1[0-2]).(0[1-9]|[12][0-9]|3[01])$";
    private final static String WORK_CALENDAR_URL = "http://xmlcalendar.ru/data/ru/%s/calendar.xml";

    public enum ParseType {ADD, EDIT}

    public enum OperationType {
        ADD_WISH, EDIT_WISH, DELETE_WISH, ADD_SALARY, GET_SUMS, GET_ALL_WISHES, GET_GROUP_WISHES,
        UP_PRIORITY, DOWN_PRIORITY, UP_MONTH, DOWN_MONTH, LOGIN, TOGGLE_MODE, GET_USER_INFO,
        UPDATE_USER, GET_CURRENT_USER, TOGGLE_USER_MODE
    }

    private static HashMap<Integer, String> colorClasses;


    /**
     * Определяет лежит ли указанное время между двумя заданными.
     *
     * @param candidate
     * @param start
     * @param end
     * @return
     */
    public static boolean isBetween(LocalTime candidate, LocalTime start, LocalTime end) {
        return !candidate.isBefore(start) && !candidate.isAfter(end);
    }

    /**
     * Преобразование ключа для последующего поиска минимальной или максимальной даты.
     *
     * @param v
     * @return
     */
    private static AbstractMap.SimpleEntry<DateTime, Double> mapKeyToFindMinOrMax(MoexRowsRs v) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        DateTime convertedDateTime = DateTime.parse(v.getEnd(), dateTimeFormatter);

        return new AbstractMap.SimpleEntry<>(convertedDateTime, Double.parseDouble(v.getClose()));
    }

    /**
     * Конвертим SQL-TIME в LOCALTIME
     *
     * @param time
     * @return
     */
    public static LocalTime toLocalTime(java.sql.Time time) {
        return time.toLocalTime();
    }

    /**
     * Создаем gson builder
     */
    public static Gson createGsonBuilder() {

        Gson gson = new GsonBuilder()
                .serializeNulls()
                .setDateFormat("dd/MM/yyyy")
                .registerTypeAdapter(java.sql.Time.class, new TimeSerializer())
                .create();

        return gson;
    }

    public static Gson createNullableGsonBuilder() {

        Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
        TypeAdapter<Date> dateTypeAdapter = gson.getAdapter(Date.class);
        TypeAdapter<Date> safeDateTypeAdapter = dateTypeAdapter.nullSafe();

        return new GsonBuilder()
                .registerTypeAdapter(Date.class, safeDateTypeAdapter)
                .create();
    }

    public static int getCurrentYear(Integer priority) {
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int month = localDate.getMonthValue();
        return (month + (priority - 1)) > 12 ? localDate.getYear() + 1 : localDate.getYear();
    }


    /**
     * Конвертим пришедший json в нового пользака и валидируем
     */
    public static ArNoteUser parseJsonToUserAndValidate(String json) throws Exception {

        if (JSONTemplate.fromString(json).getAsJsonObject().size() == 0) {
            throw new JsonNullException("JSON - пустой");
        }

        ArNoteUser localUser;
        Date currentDate = new Date();


        try {
            // ------------------ Валидация -------------------------

            if (isBlank(JSONTemplate.fromString(json).get("login").getAsString())) throw new JsonParseException(json);

            ArNoteUser.Role userRole;

            if (("USER".equals(JSONTemplate.fromString(json).get("userRole").getAsString())) ||
                    ("ADMIN".equals(JSONTemplate.fromString(json).get("userRole").getAsString()))) {
                userRole = ArNoteUser.Role.valueOf(JSONTemplate.fromString(json).get("userRole").getAsString());
            } else {
                userRole = ArNoteUser.Role.USER;
            }

            if (JSONTemplate.fromString(json).get("userCryptoMode") == null) throw new JsonParseException(json);
            if (JSONTemplate.fromString(json).get("pwd") == null) throw new JsonParseException(json);
            if (JSONTemplate.fromString(json).get("email") == null) throw new JsonParseException(json);
            if (JSONTemplate.fromString(json).get("fullname") == null) throw new JsonParseException(json);


            localUser = new ArNoteUser(
                    JSONTemplate.fromString(json).get("login").getAsString(),
                    userRole,
                    JSONTemplate.fromString(json).get("pwd").getAsString(),
                    JSONTemplate.fromString(json).get("userCryptoMode").getAsBoolean(),
                    JSONTemplate.fromString(json).get("email").getAsString(),
                    JSONTemplate.fromString(json).get("fullname").getAsString()
            );

            localUser.setCreationDate(currentDate);

        } catch (Exception e) {
            throw new JsonParseException(json);
        }
        return localUser;
    }


    /**
     * Конвертим пришедший json в новую Salary
     */
    public static Salary parseJsonToSalary(String json, ArNoteUser user) throws Exception {

        if (JSONTemplate.fromString(json).getAsJsonObject().size() == 0) {
            throw new JsonNullException("JSON - пустой");
        }

        Salary salary;
        Date currentDate = new Date();

        try {
            salary = new Salary(
                    JSONTemplate.fromString(json).get("fullsalary").getAsInt(),
                    JSONTemplate.fromString(json).get("residualSalary").getAsInt()
            );
            salary.setSalarydate(currentDate);
            LocalDateTime currentTimestamp = LocalDateTime.now();
            salary.setSalaryTimeStamp(currentTimestamp);

            salary.setUser(user);
        } catch (Exception e) {
            throw new JsonParseException(json);
        }
        return salary;
    }

    /**
     * Конвертим пришедший json в новую Salary
     */
    public static Date localDateToDate(LocalDate date) {
        ZoneId defaultZoneId = ZoneId.systemDefault();
        return Date.from(date.atStartOfDay(defaultZoneId).toInstant());
    }

    public static java.sql.Date localDateToSqlDate(LocalDate date) {
        return java.sql.Date.valueOf(date);
    }


    /**
     * Конвертим пришедший json в новый WISH
     */
    public static Wish parseJsonToWish(ParseType parseType, String json, ArNoteUser user) throws Exception {

        if (JSONTemplate.fromString(json).getAsJsonObject().size() == 0) {
            throw new JsonNullException("JSON - пустой");
        }

        Wish wishAfterParse;
        Date currentDate = new Date();

        try {

            if (parseType == ParseType.EDIT) {
                wishAfterParse = new Wish(
                        JSONTemplate.fromString(json).get("id").getAsLong(),
                        JSONTemplate.fromString(json).get("wish").getAsString(),
                        JSONTemplate.fromString(json).get("price").getAsInt(),
                        JSONTemplate.fromString(json).get("priority").getAsInt(),
                        JSONTemplate.fromString(json).get("archive").getAsBoolean(),
                        JSONTemplate.fromString(json).get("description").getAsString(),
                        JSONTemplate.fromString(json).get("url").getAsString(),
                        user);

                boolean realizedWish = JSONTemplate.fromString(json).get("realized").getAsBoolean();
                if (realizedWish) {
                    wishAfterParse.setRealized(true);
                    wishAfterParse.setRealizationDate(new Date());
                }

            } else {
                wishAfterParse = new Wish(
                        JSONTemplate.fromString(json).get("wish").getAsString(),
                        JSONTemplate.fromString(json).get("price").getAsInt(),
                        JSONTemplate.fromString(json).get("priority").getAsInt(),
                        JSONTemplate.fromString(json).get("archive").getAsBoolean(),
                        JSONTemplate.fromString(json).get("description").getAsString(),
                        JSONTemplate.fromString(json).get("url").getAsString(),
                        user
                );

                wishAfterParse.setCreationDate(currentDate);

            }
        } catch (Exception e) {
            throw new JsonParseException(json);
        }

        return wishAfterParse;
    }

    public static String generateRandomPassword() {

        List rules = Arrays.asList(new CharacterRule(EnglishCharacterData.UpperCase, 1),
                new CharacterRule(EnglishCharacterData.LowerCase, 1), new CharacterRule(EnglishCharacterData.Digit, 1), new CharacterRule(EnglishCharacterData.Special, 1));

        PasswordGenerator generator = new PasswordGenerator();
        String password = generator.generatePassword(8, rules);
        return password;
    }

    public static String computerMonth(Integer proirity) {
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int month = localDate.getMonthValue();

        Locale currentLocale = Locale.getDefault();
        return Month.of((month + (proirity - 1)) > 12 ?
                (month + (proirity - 1)) - 12 :
                (month + (proirity - 1))).getDisplayName(TextStyle.FULL_STANDALONE, currentLocale);
    }

    public static String getMonthByNumber(Integer montNumber) {
        Locale currentLocale = Locale.getDefault();
        return Month.of((montNumber)).getDisplayName(TextStyle.FULL_STANDALONE, currentLocale);
    }

    public static int computerMonthNumber(Integer priority) {
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int month = localDate.getMonthValue();
        return Month.of((month + (priority - 1)) > 12 ? (month + (priority - 1)) - 12 : (month + (priority - 1))).getValue();
    }

    public static WishDTO prepareWishDTO(Wish w, int maxPrior) {
        return WishDTO.builder()
                .id(w.getId())
                .wish(w.getWish())
                .price(w.getPrice())
                .priority(w.getPriority())
                .ac(w.getAc())
                .description(w.getDescription())
                .url(w.getUrl())
                .priorityGroup(w.getPriorityGroup())
                .priorityGroupOrder(w.getPriorityGroupOrder())
                .month(computerMonth(w.getPriorityGroup() == null ? maxPrior : w.getPriorityGroup()))
                .build();
    }

    public static String getClassColorByMonth(int month, boolean overdraft) {

        colorClasses = new HashMap<>();
        colorClasses.put(1, "label label-purple");
        colorClasses.put(2, "label label-blue");
        colorClasses.put(3, "label label-light-blue");
        colorClasses.put(4, "label label-orange");
        colorClasses.put(5, "label label-success");
        colorClasses.put(6, "label label-purple");
        colorClasses.put(7, "label label-blue");
        colorClasses.put(8, "label label-light-blue");
        colorClasses.put(9, "label label-orange");
        colorClasses.put(10, "label label-success");
        colorClasses.put(11, "label label-purple");
        colorClasses.put(12, "label label-blue");
        colorClasses.put(13, "label label-danger");

        if (!overdraft) {
            if (month == 0) {
                return colorClasses.get(1);
            } else {
                return colorClasses.get(month);
            }
        } else {
            return colorClasses.get(13);
        }
    }

    public static String defineUserActionByMethodSignature(Signature signature) {

        String action = "UNKNOWN";

        switch (signature.getName()) {
            case "getSumm":
                action = "GET_SUM";
                break;
            case "findAll":
                action = "GET_ALL";
                break;
            case "gelAllWishes":
                action = "GET_ALL_WISHES";
                break;
            case "updateWish":
                action = "UPDATE_WISH";
                break;
            case "addWish":
                action = "ADD_NEW_WISH";
                break;
            case "deleteWish":
                action = "DELETE_WISH";
                break;
            case "getLastSalary":
                action = "GET_LAST_SALARY";
                break;
            case "addSalary":
                action = "ADD_SALARY";
                break;
            case "parseCsv":
                action = "PARSE_CSV_FILE";
                break;
            case "changePriority":
                action = "CHANGE_WISH_PRIORITY";
                break;
            case "changeMonth":
                action = "CHANGE_MONTH";
                break;
            case "addUser":
                action = "ADD_NEW_USER";
                break;
            case "deleteUser":
                action = "DELETE_USER";
                break;
            case "editUser":
                action = "EDIT_USER";
                break;
            case "toggleUserMode":
                action = "TOGGLE_USER_VIEW_MODE";
                break;
            case "getAllUsers":
                action = "GET_ALL_USERS";
                break;
            case "getCurrentUser":
                action = "GET_CURRENT_USER";
                break;
            case "returnUserPassword":
                action = "RETURN_USER_PASSWORD";
                break;
            case "resetUserPasswordByAdmin":
                action = "RETURN_USER_PASSWORD_BY_ADMIN";
                break;
        }


        return action;
    }

    public static boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static int monthNameToNumber(String value) {
        int result = 0;
        switch (value) {
            case "Январь":
                result = 1;
                break;
            case "Февраль":
                result = 2;
                break;
            case "Март":
                result = 3;
                break;
            case "Апрель":
                result = 4;
                break;
            case "Май":
                result = 5;
                break;
            case "Июнь":
                result = 6;
                break;
            case "Июль":
                result = 7;
                break;
            case "Август":
                result = 8;
                break;
            case "Сентябрь":
                result = 9;
                break;
            case "Октябрь":
                result = 10;
                break;
            case "Ноябрь":
                result = 11;
                break;
            case "Декабрь":
                result = 12;
                break;
        }
        return result;
    }

    private static Pattern getPattern(String regex) {
        return Pattern.compile(regex, Pattern.MULTILINE);
    }

    private static Matcher getMatcher(Pattern pattern, String stringToMatch) {
        return pattern.matcher(stringToMatch);
    }


    private static MyPair getYearAndMonth(String monthAndYear) {
        final Matcher matcher = getMatcher(getPattern(DIGITAL_PATTERN), monthAndYear);
        String year = "";
        String month = "";

        while (matcher.find()) {
            year = matcher.group(1);
            month = monthAndYear.substring(0, matcher.start(1)).trim();
        }

        return new MyPair(year, month);
    }

    public static int parseMonthAndCalculatePriority(String monthAndYear) throws BadIncomeParameter {

        if (Pattern.compile("[А-Яа-я]+ [0-9]+").matcher(monthAndYear).find()) {

            String year = getYearAndMonth(monthAndYear).getYear();
            String month = getYearAndMonth(monthAndYear).getMonth();

            log.info("Обнаружена дата в русской раскладке");
            log.info("Год: {}", year);
            log.info("Месяц: {}", month);

            if (isBlank(year) && isInteger(year)) {
                log.error("Ошибка парсинга даты: {}", monthAndYear);
                throw new BadIncomeParameter(BadIncomeParameter.ParameterKind.WRONG_MONTH);
            } else {


                return 1 + Math.toIntExact((ChronoUnit.MONTHS.between(LocalDate.now(), LocalDate.of(Integer.parseInt(year),
                        monthNameToNumber(month), 1))));
            }
        } else if (Pattern.compile("[A-Za-z]+ [0-9]+").matcher(monthAndYear).find()) {

            String year = getYearAndMonth(monthAndYear).getYear();
            String month = getYearAndMonth(monthAndYear).getMonth();

            log.info("Обнаружена дата в английской раскладке");
            log.info("Год: {}", year);
            log.info("Месяц: {}", month);

            if (isBlank(year) && isInteger(year)) {
                log.error("Ошибка парсинга даты: {}", monthAndYear);
                throw new BadIncomeParameter(BadIncomeParameter.ParameterKind.WRONG_MONTH);
            } else {
                // log.info("Ставим. Текущая дата + 1 мес: {}", month);
                return 1 + Math.toIntExact((ChronoUnit.MONTHS.between(LocalDate.now(), LocalDate.of(Integer.parseInt(year),
                        monthNameToNumber(convertEnglishNames(month)), 1))));
            }

        } else {
            throw new BadIncomeParameter(BadIncomeParameter.ParameterKind.WRONG_MONTH);
        }
    }

    private static String convertEnglishNames(String monthAndYear) throws BadIncomeParameter {
        String returnMonth = monthAndYear;
        switch (monthAndYear) {
            case "January":
                returnMonth = "Январь";
                break;
            case "February":
                returnMonth = "Февраль";
                break;
            case "March":
                returnMonth = "Март";
                break;
            case "April":
                returnMonth = "Апрель";
                break;
            case "May":
                returnMonth = "Май";
                break;
            case "June":
                returnMonth = "Июнь";
                break;
            case "July":
                returnMonth = "Июль";
                break;
            case "August":
                returnMonth = "Август";
                break;
            case "September":
                returnMonth = "Сентябрь";
                break;
            case "October":
                returnMonth = "Октябрь";
                break;
            case "November":
                returnMonth = "Ноябрь";
                break;
            case "December":
                returnMonth = "Декабрь";
                break;
            default:
                throw new BadIncomeParameter(BadIncomeParameter.ParameterKind.WRONG_MONTH);
        }
        log.info("Перевели месяц на русский язык: {}", returnMonth);
        return returnMonth;
    }

    /**
     * Сформировать специальный URL для запроса истории.
     *
     * @return
     */
    public static String prepareUrlForHistory(String urlBase, MoexRestTemplateOperation operation, MultiValueMap<String, String> queryParameters,
                                              Map<String, String> pathParams, String dateFrom, String dateTill, int start) { //todo: почему тут пустой dateFrom ???? Зачем он тогда?


        queryParameters.put("start", Collections.singletonList(String.valueOf(start)));
        queryParameters.put("till", Collections.singletonList(dateTill));
        queryParameters.put("from", Collections.singletonList(dateFrom));

        UriComponents uriComponents = UriComponentsBuilder
                .newInstance()
                .scheme("http")
                .host(urlBase)
                .path(operation.getUrl())
                .queryParams(queryParameters)
                .buildAndExpand(pathParams);


        return uriComponents.toString();
    }

    /**
     * Сформировать специальный URL для запроса истории.
     *
     * @return
     */
    public static String prepareUrlForCandles(String urlBase, MoexRestTemplateOperation operation, MultiValueMap<String, String> queryParameters,
                                              Map<String, String> pathParams, String dateFrom, String dateTill, int start) { //todo: объединить с prepareUrlForHistory


        queryParameters.put("start", Collections.singletonList(String.valueOf(start)));
        queryParameters.put("till", Collections.singletonList(dateTill));
        queryParameters.put("from", Collections.singletonList(dateFrom));

        UriComponents uriComponents = UriComponentsBuilder
                .newInstance()
                .scheme("http")
                .host(urlBase)
                .path(operation.getUrl())
                .queryParams(queryParameters)
                .buildAndExpand(pathParams);


        return uriComponents.toString();
    }


    /**
     * Предикат distinctBy для выкидывания одинаковых тикеров (дублей) при поиске.
     *
     * @param keyExtractor
     * @param <T>
     * @return
     */
    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {

        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    /**
     * Предикат для фильтра по ключевому слову при поиске инструмента.
     *
     * @return
     */
    public static Predicate<MoexRowsRs> filterByKeyword(String keyword) {
        return s -> (s.getSecName().toLowerCase().contains(keyword.toLowerCase()) ||
                s.getSecid().toLowerCase().contains(keyword.toLowerCase()));
    }

    /**
     * Предикат для универсального фильтра.
     *
     * @return
     */
    public static Predicate<BondRs> complexPredicate(Map<String, String> investingFilterMode) {

        List<Predicate<BondRs>> arr = investingFilterMode.values().stream()
                .map(s -> InvestingFilterMode.valueOf(s).getFilter())
                .collect(Collectors.toList());

        if (arr.size() == 1) {
            return arr.get(0);
        } else {
            return arr.stream().reduce(t -> true, Predicate::and);
        }
    }


    /**
     * Подготовить список инструментов
     *
     * @return
     */
    public static List<FoundInstrumentRs> prepareInstruments(List<MoexRowsRs> list, BondType type, StockExchange stockExchange) {
        return list.stream()
                .map(r -> FoundInstrumentRs.builder()
                        .ticker(r.getSecid())
                        .currencies(Currencies.search(r.getCurrencyId()))
                        .description(r.getSecName())
                        .stockExchange(stockExchange)
                        .type(type)
                        .build())
                .filter(distinctByKey(FoundInstrumentRs::getTicker))
                .limit(5)
                .collect(Collectors.toList());
    }

    /**
     * Подготовить URL для буржуйских API.
     *
     * @return
     */
    public static String prepareForeignUrl(ForeignRequests req, MultiValueMap<String, String> queryParameters,
                                           Map<String, String> pathParams) {

        UriComponents uriComponents = UriComponentsBuilder
                .newInstance()
                .scheme(req.getSchema().getSchema())
                .host(req.getHost().getUrl())
                .path(req.getConstantPart())
                .queryParams(queryParameters)
                .buildAndExpand(pathParams);

        return uriComponents.toString();
    }


    /**
     * Подставить ключевик в URL (например, тикер) и отдать готовый URL.
     *
     * @return
     */
    public static String prepareUrl(String urlBase, MoexRestTemplateOperation operation, MultiValueMap<String, String> queryParameters,
                                    Map<String, String> pathParams) {

        UriComponents uriComponents = UriComponentsBuilder
                .newInstance()
                .scheme("http")
                .host(urlBase)
                .path(operation.getUrl())
                .queryParams(queryParameters)
                .buildAndExpand(pathParams);

        return uriComponents.toString();
    }

    /**
     * Преобразовать строковой epoch-mil в LocalDate.
     *
     * @return
     */
    public static LocalDate parseStringEpochMilDate(String epochMil) {
        long milInLong = Long.parseLong(epochMil);
        return LocalDate.from(LocalDateTime.ofInstant(Instant.ofEpochSecond(milInLong), ZoneId.systemDefault()));
    }

    /**
     * Преобразовать epoch-mil и получить время.
     *
     * @return
     */
    public static LocalTime parseEpochMilToTime(Long epochMil) {
        return LocalTime.from(LocalDateTime.ofInstant(Instant.ofEpochSecond(epochMil), ZoneId.systemDefault()));
    }

    /**
     * Рассчитываем Тиньковские дельты.
     *
     * @return
     */
    public static Map<TinkoffDeltaFinalValuesType, Double> getTcsDeltaValues(List<Purchase> purchaseList,
                                                                             Double currentStockPrice) {
        Map<TinkoffDeltaFinalValuesType, Double> resultMap = new HashMap<>();
        if (purchaseList != null && purchaseList.size() > 0) {
            /*
             * Считаем среднюю цену покупки (сумма цена * лот)
             */
            Double tkcAveragePurchasePrice = purchaseList.stream()
                    .map(p -> p.getPrice() * p.getLot())
                    .reduce((double) 0, Double::sum);

            double tinkoffSameLotButNewPrice = (purchaseList.stream()
                    .map(Purchase::getLot)
                    .reduce(0, Integer::sum)) * currentStockPrice;

            double tcsDeltaFinal = tinkoffSameLotButNewPrice - tkcAveragePurchasePrice;
            resultMap.put(TinkoffDeltaFinalValuesType.DELTA_FINAL, tcsDeltaFinal);
            resultMap.put(TinkoffDeltaFinalValuesType.DELTA_PERCENT, ((tcsDeltaFinal * 100) / tinkoffSameLotButNewPrice));
        } else {
            resultMap.put(TinkoffDeltaFinalValuesType.DELTA_FINAL, 0.0D);
            resultMap.put(TinkoffDeltaFinalValuesType.DELTA_PERCENT, 0.0D);
        }
        return resultMap;
    }

    /**
     * Вытаскиваем позицию (цену) закрытия бумаги за вчерашний день.
     *
     * @param doc
     * @return
     */
    public static Double getClosePositionForTomorrow(MoexDocumentRs doc) {
        if (doc.getData() != null && doc.getData().getRow().size() > 0) {

            return doc.getData().getRow().stream()
                    .map(v -> {

                        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
                        DateTime convertedDateTime = DateTime.parse(v.getEnd(), dateTimeFormatter);

                        return new AbstractMap.SimpleEntry<>(convertedDateTime, Double.parseDouble(v.getClose()));
                    })
                    .filter(d -> jodaToJavaLocalDateTime(d.getKey()).toLocalDate().isBefore(LocalDate.now()))
                    .max(Map.Entry.comparingByKey())
                    .map(AbstractMap.SimpleEntry::getValue)
                    .orElse(0D);

        } else {
            return (0d);
        }
    }

    /**
     * Рассчитываем дневную дельту из Свечей.
     *
     * @return
     */
    public static Double getDayDeltaFromCandle(MoexDocumentRs doc) {
        if (doc.getData() != null && doc.getData().getRow().size() > 0) {

            Double currentDayValue = doc.getData().getRow().stream()
                    .map(ArNoteUtils::mapKeyToFindMinOrMax)
                    .max(Map.Entry.comparingByKey())
                    .map(AbstractMap.SimpleEntry::getValue)
                    .orElse(0D);

            return currentDayValue - getClosePositionForTomorrow(doc);
        } else {
            return (0d);
        }
    }

    /**
     * JodaTime to Java LocalDateTime.
     *
     * @param dateTime
     * @return
     */
    public static LocalDateTime jodaToJavaLocalDateTime(DateTime dateTime) {
        return Instant.ofEpochMilli(dateTime.getMillis())
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();


    }

    /**
     * Взять покупки, кол-во купленных акций перемножить на стоимость и все это просуммировать.
     *
     * @param
     * @return
     */
    public static Double getCostOfAllPurchasesOfSecurityInPortfolio(List<Purchase> purchaseList) {
        return purchaseList.stream()
                .map(v -> v.getLot() * v.getPrice())
                .reduce(Double::sum)
                .orElse(0.0D);


    }


    /**
     * Рассчитываем  доход из Свечей по всем купленным бумагам.
     * <p>
     * Формула расчета ((Сt*CP)-(Sp[...]))
     * где:
     * <p>
     * Ct - кол-во купленных бумаг (акций)
     * CP - текущая цена
     * Sp[...] - сумма покупок. То есть 10-апреля купили например 10 по цене 1.5, 11 мая купили 10 по цене 2.0,
     * значит получаем: (10 * 1.5) + (10 * 2.0) = ....
     *
     * @return
     */
    public static Double getIncomeForAllPurchasesFromCandle(MoexDocumentRs candles, MoexDocumentRs history,
                                                            Double currentStockPrice,
                                                            List<Purchase> purchaseList) {
        if (history.getData() != null && history.getData().getRow().size() > 0) {

            Double currentDayValue = (candles.getData() == null || candles.getData().getRow() == null ||
                    candles.getData().getRow().size() < 1) ? 0.0D : (candles.getData().getRow().stream()
                    .map(ArNoteUtils::mapKeyToFindMinOrMax)
                    .max(Map.Entry.comparingByKey())
                    .map(AbstractMap.SimpleEntry::getValue)
                    .orElse(currentStockPrice));

            Integer instrumentsCount = purchaseList.stream()
                    .map(Purchase::getLot)
                    .reduce(Integer::sum)
                    .orElse(0); // считаем кол-во бумаг в портфеле //todo: в отдельный метод в Утилс


            return (currentDayValue * instrumentsCount) - getCostOfAllPurchasesOfSecurityInPortfolio(purchaseList);
        } else {
            return (0d);
        }
    }

    /**
     * Вариант метода getIncomeForAllPurchasesFromCandle, но считающий результат данного метода в процентах.
     *
     * Формула расчета:
     *
     * Сколько_процентов_составляет(Х от Y).
     * где:
     *
     * X - результат метода getIncomeForAllPurchasesFromCandle
     * Y - Sp[...]
     * Sp[...] - сумма покупок. То есть 10-апреля купили например 10 по цене 1.5, 11 мая купили 10 по цене 2.0,
     * значит получаем: (10 * 1.5) + (10 * 2.0) = ....
     *
     * @return
     */
    public static Double getIncomeForAllPurchasesInPercents(Double income, List<Purchase> purchaseList) {
        if (income != 0) {

            double coef = getCostOfAllPurchasesOfSecurityInPortfolio(purchaseList) / income;

            return coef == 0 ? 0.0D : 100 / coef;
        } else {
            return 0.0D;
        }
    }


    /**
     * Достать ближайшую дату к заданной.
     *
     * @param dates
     * @param currentDate
     * @return
     */
    public static Long getNearestDate(Map<Long, LocalDate> dates, LocalDate currentDate) {

        NavigableSet<LocalDate> datesInSet = new TreeSet<>(dates.values());
        LocalDate minDate = datesInSet.lower(currentDate);

        if (minDate != null) {
            return dates.entrySet()
                    .stream()
                    .filter(w -> w.getValue().isEqual(minDate))
                    .findFirst()
                    .map(Map.Entry::getKey).orElse(0L);
        } else {
            return null;
        }
    }

    /**
     * Достаем курсы перевода валют.
     *
     * @return
     */
    public static double calculateCurrencyMultiplier(CommonMoexDoc doc, String currency) {

        if (Currencies.getTransferByCodes(currency) == null) {
            return (1d);
        } else {
            return Optional.ofNullable(doc)
                    .map(MoexDocumentRs.class::cast)
                    .orElseThrow(() -> new MoexXmlResponseMappingException("курсы валют"))
                    .getData()
                    .getRow()
                    .stream()
                    .filter(curr -> curr.getCurrencyExchangeType().equals(Currencies.getTransferByCodes(currency)))
                    .findFirst()
                    .map(MoexRowsRs::getRate)
                    .map(Double::valueOf)
                    .orElse(Double.valueOf("1"));
        }
    }

    /**
     * Грузим производственный календарь по ссылке.
     *
     * @return
     */
    public static Optional<com.antonromanov.arnote.model.common.Calendar> getWorkCalendar(Integer year) {

        String path = String.format(WORK_CALENDAR_URL, year);

        try (BufferedInputStream in = new BufferedInputStream(new URL(path).openStream())) {
            return Optional.of(unmarshall(in));
        } catch (IOException e) {
            log.error("Не удалось, найти файл xml производственного календаря или спарсить его!");
            return Optional.empty();
        }
    }


    /**
     * Грузим производственный календарь по ссылке.
     *
     * @return
     */
    public static java.sql.Date getSqlDateFromXmlCalendar(String year, String xmlDate) {
        if (isBlank(year) || isBlank(xmlDate)) {
            return null;
        } else {

            Matcher matcher = getMatcher(getPattern(XML_SPECIAL_DATE_PATTERN), xmlDate);
            LocalDate tempDate = null;
            while (matcher.find()) {
                 tempDate = LocalDate.of(Integer.parseInt(year),
                        Integer.parseInt(matcher.group(1)),
                        Integer.parseInt(matcher.group(2)));

            }

            return localDateToSqlDate(tempDate);
        }
    }

    public static com.antonromanov.arnote.model.common.Calendar unmarshall(InputStream response) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(com.antonromanov.arnote.model.common.Calendar.class);
            Unmarshaller un = jaxbContext.createUnmarshaller();
            return (com.antonromanov.arnote.model.common.Calendar) un.unmarshal(response);
        } catch (JAXBException e) {
            log.error("Ошибка анмаршелинга: {}", e.getMessage());
            throw new MoexXmlResponseMarshalingException();
        }
    }
}
