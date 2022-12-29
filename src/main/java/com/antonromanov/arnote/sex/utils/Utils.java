package com.antonromanov.arnote.sex.utils;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.Signature;
import org.springframework.stereotype.Service;
import java.sql.Time;
import java.time.*;
import java.time.format.TextStyle;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Тут собраны основные утилиты.
 */
@Slf4j
@Service
public class Utils {


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
     * Определяет количество дней между двумя датами (создания желания и его реализации).
     *
     * @param creation - дата создания желания.
     * @param realization - дата реализации желания.
     * @return
     */
    public static long wishLifeTime(Date creation, Date realization) {

        return Duration.between(convertDateToLocalDate(realization).atStartOfDay(),
                convertDateToLocalDate(creation).atStartOfDay()).toDays();
    }

    /**
     * Java Util Date в LocalDate.
     *
     * @param userDate
     * @return
     */
    public static LocalDate convertDateToLocalDate(Date userDate) {
        return userDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
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

    // Проверяем таймут до последнего пинга.
    public static Boolean checkTimeout(Time lastPingTime) {

        Date date = new Date();
        Time time = new Time(date.getTime());
        boolean result = true;

        if (lastPingTime != null) { // время должно быть не ноль, иначе все наебнется
            // TODO надо еще проверить, чтобы дата была именно сегодняшняя
            LocalTime offsetTime = toLocalTime(lastPingTime).plusMinutes(15);
            result = isBetween(toLocalTime(time), toLocalTime(lastPingTime), offsetTime);
        }
        return result;

    }



    /*public static String generateRandomPassword() {

        List rules = Arrays.asList(new CharacterRule(EnglishCharacterData.UpperCase, 1),
                new CharacterRule(EnglishCharacterData.LowerCase, 1), new CharacterRule(EnglishCharacterData.Digit, 1), new CharacterRule(EnglishCharacterData.Special, 1));

        PasswordGenerator generator = new PasswordGenerator();
        String password = generator.generatePassword(8, rules);
        return password;
    }*/

    public static String computerMonth(Integer proirity) {
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int month = localDate.getMonthValue();

        Locale currentLocale = Locale.getDefault();
        return Month.of((month + (proirity - 1)) > 12 ? (month + (proirity - 1)) - 12 : (month + (proirity - 1))).getDisplayName(TextStyle.FULL_STANDALONE, currentLocale);
    }

    public static int computerMonthNumber(Integer priority) {
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int month = localDate.getMonthValue();
        return Month.of((month + (priority - 1)) > 12 ? (month + (priority - 1)) - 12 : (month + (priority - 1))).getValue();
    }


    public static int getCurrentYear(Integer proirity) {
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int month = localDate.getMonthValue();
        return (month + (proirity - 1)) > 12 ? localDate.getYear() + 1 : localDate.getYear();
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

    /**
     * Перевести Дату в LocalDate.
     *
     * @param entityDate
     * @return
     */
    public static LocalDate dateToLocalDate(Date entityDate) {
        return new Date(entityDate
                .getTime())
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    private static boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

  /*  private static int monthNameToNumber(String value) {
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
        log.info("Выcчитали месяц: {}", result);
        return result;
    }*/

    private static Pattern getDigitsPattern() {
        return Pattern.compile("([0-9]+)", Pattern.MULTILINE);
    }

    private static Matcher getMatcher(Pattern pattern, String stringToMatch) {
        return pattern.matcher(stringToMatch);
    }

    /**
     * Поиск в enum'е сортировок подходящее по имени, переданному с UI.
     *
     * @param name
     * @return
     */
   /* public static Optional<SortMode_SOLVE> lookUpSortType(String name) { // поиск в Енуме
        for (SortMode_SOLVE mode : SortMode_SOLVE.values()) {
            //   if (mode.getUiValue().equals(name)) return Optional.of(mode);
        }
        return null; // если не нашли
    }*/

   /* private static String convertEnglishNames(String monthAndYear) throws BadIncomeParameter {
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
    }*/
}
