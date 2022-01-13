package com.antonromanov.arnote.finplanning;

import com.antonromanov.arnote.entity.common.Salary;
import com.antonromanov.arnote.entity.finplan.Credit;
import com.antonromanov.arnote.entity.finplan.Goal;
import com.antonromanov.arnote.model.ArNoteUser;
import com.antonromanov.arnote.repositoty.*;
import com.antonromanov.arnote.services.FinPlanServiceImp;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FinPlanTests {

    @Mock
    private SalaryRepository salaryRepository;

    @Mock
    private CreditRepository creditRepo;

    @Mock
    private UsersRepo users;

    @Mock
    private GoalsRepo purchaseRepo;

    @Mock
    private IncomeRepo incomeRepo;

    @InjectMocks
    private FinPlanServiceImp service;


    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }


    /**
     * Тестируем добавление нового кредита: проверка отработки при отсутствии свободных слотов и наличии закрытых кредитов.
     */
    @Test
    public void testCheckingForAddNewLoan_OnlyClosedLoans() {

        ArNoteUser user = new ArNoteUser();
        user.setId(1);
        List<Credit> credits = new ArrayList<>();
        ZoneId defaultZoneId = ZoneId.systemDefault();
        Date queryDate = Date.from(LocalDate.now().atStartOfDay(defaultZoneId).toInstant());
        Date startDate = Date.from(LocalDate.now().minusYears(1).atStartOfDay(defaultZoneId).toInstant());
        for (long i = 1L; i < 6L; i++) {
            credits.add(Credit.builder()
                    .id(i)
                    .startDate(startDate)
                    .startAmount(100)
                    .fullPayPerMonth(i!=1L ? 5 : 20)
                    .realPayPerMonth(i!=1L ? 5 : 20)
                    .creditNumber((int) i)
                    .user(user)
                    .build());
        }



        service.getCalculatedLoansTable(credits);
        when(creditRepo.getCreditsByUser(user)).thenReturn(credits);
        assertEquals(1, service.checkForNewLoanAddingAndGetNewNumber(user, queryDate));
    }


    /**
     * Тестируем добавление нового кредита: проверка отработки при наличии свободных слотов
     */
    @Test
    public void testCheckingForAddNewLoan_OnlyFreeSlots() {

        ArNoteUser user = new ArNoteUser();
        user.setId(1);
        Credit c1 = Credit.builder()
                .id(1L)
                .creditNumber(1)
                .user(user)
                .build();
        Credit c2 = Credit.builder()
                .id(2L)
                .creditNumber(2)
                .user(user)
                .build();

        ZoneId defaultZoneId = ZoneId.systemDefault();
        Date queryDate = Date.from(LocalDate.now().atStartOfDay(defaultZoneId).toInstant());


        when(creditRepo.getCreditsByUser(user)).thenReturn(Arrays.asList(c1, c2));
        assertEquals(3, service.checkForNewLoanAddingAndGetNewNumber(user, queryDate));
    }


    /**
     * Тестируем метод получения дефолтного дохода из ЗП.
     */
    @Test
    public void testGetDefaultIncomeFromSalary() {

        ArNoteUser user = new ArNoteUser();
        user.setId(1);
        Salary s1 = new Salary();
        s1.setFullSalary(100);
        s1.setUser(user);
        s1.setSalaryTimeStamp(LocalDateTime.of(LocalDate.of(2021, 1, 1), LocalTime.now()));
        Salary s2 = new Salary();
        s2.setFullSalary(200);
        s2.setSalaryTimeStamp(LocalDateTime.of(LocalDate.of(2021, 2, 1), LocalTime.now()));
        s2.setUser(user);

        when(salaryRepository.findAllByUserAndMonthAndYear(user, 2021, 1)).thenReturn(Arrays.asList(s1, s2));
        assertEquals(100, service.getDefaultIncomeFromSalary(2021, 1, user).intValue());
    }

    /**
     * Тестируем метод фильтрации кредитной мапы.
     */
   /* @Test
    public void testCreditMapFiltration() {

        Map<LocalDate, Map<Long, Integer>> payMap = new HashMap<>();
        Map<Long, Integer> creditMap = new HashMap<>();
        Map<Long, Integer> creditMap2 = new HashMap<>();
        Map<CreditDict, Integer> expectedResult = new HashMap<>();
        Map<CreditDict, Integer> unExpectedResult = new HashMap<>();
        creditMap.put(1L,1);
        creditMap.put(2L,2);
        payMap.put(LocalDate.of(2021, 1,1),  creditMap);
        payMap.put(LocalDate.of(2022, 5,1),  creditMap2);
        expectedResult.put(CreditDict.FIRST_CREDIT, 1);
        unExpectedResult.put(CreditDict.SECOND_CREDIT, 2);

        assertEquals(expectedResult, service.filterMap(payMap, 2021, 1));
        assertNotEquals(unExpectedResult, service.filterMap(payMap, 2021, 1));
    }*/

    /**
     * LocalDate в Date
     *
     * @param dateToConvert
     * @return
     */
    public Date convertToDateViaInstant(LocalDate dateToConvert) {
        return java.util.Date.from(dateToConvert.atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }

    /**
     * Тестируем метод получения даты последней цели.
     */
    @Test
    public void testGetLastGoalsDate() {

        Goal g1 = new Goal();
        g1.setStartDate(convertToDateViaInstant(LocalDate.of(2021, 1, 1)));
        g1.setId(1L);

        Goal g2 = new Goal();
        g2.setStartDate(convertToDateViaInstant(LocalDate.of(2020, 1, 1)));
        g1.setId(2L);

        assertEquals(LocalDate.of(2021, 1, 1), service.getLastGoalsDate(Arrays.asList(g1, g2)));
    }

    /**
     * Тестируем метод получения даты последнего кредита.
     */
   /* @Test
    public void testGetLastCreditDate() {

        int startAmount = 100;
        int realPayPerMonth = 50;
        int timeToFinish = startAmount/realPayPerMonth;
        Credit c1 = new Credit();
        c1.setStartDate(convertToDateViaInstant(LocalDate.of(2021, 1,1)));
        c1.setId(1L);
        c1.setStartAmount(startAmount);
        c1.setRealPayPerMonth(realPayPerMonth);

        Credit c2 = new Credit();
        c2.setStartDate(convertToDateViaInstant(LocalDate.of(2020, 1,1)));
        c2.setId(2L);
        c2.setStartAmount(200);
        c2.setRealPayPerMonth(23);

        ArNoteUser user = new ArNoteUser();
        user.setId(1);

        assertEquals(LocalDate.of(2021, timeToFinish,1), service.getLastCreditDate(Arrays.asList(c1,c2), user));
    }*/


    /**
     * Тестируем метод получения даты последнего кредита.
     */
    @Test
    public void findClosestDate() {

        NavigableSet<Date> dates = new TreeSet<>();
        dates.add(convertToDateViaInstant(LocalDate.of(2021, 12, 1)));
        dates.add(convertToDateViaInstant(LocalDate.of(2021, 8, 1)));
        dates.add(convertToDateViaInstant(LocalDate.of(2020, 5, 1)));
        // add some dates to dates
        Date now = convertToDateViaInstant(LocalDate.of(2015, 1, 1));
        Date highestDateUpUntilNow = dates.ceiling(now);

        assertEquals((convertToDateViaInstant(LocalDate.of(2020, 5, 1))), highestDateUpUntilNow);
    }
}
