package com.antonromanov.arnote.wish;

import com.antonromanov.arnote.domain.salary.repository.SalaryRepository;
import com.antonromanov.arnote.domain.user.service.UserService;
import com.antonromanov.arnote.domain.wish.dto.rq.WishRq;
import com.antonromanov.arnote.domain.wish.dto.rs.GroupedWishRs;
import com.antonromanov.arnote.domain.wish.dto.rs.WishRs;
import com.antonromanov.arnote.domain.wish.entity.Wish;
import com.antonromanov.arnote.domain.wish.enums.SortMode;
import com.antonromanov.arnote.domain.wish.mapper.WishMapperImpl;
import com.antonromanov.arnote.domain.wish.service.impl.WishServiceImpl;
import com.antonromanov.arnote.old.entity.common.Salary;
import com.antonromanov.arnote.old.model.ArNoteUser;
import com.antonromanov.arnote.old.repositoty.WishRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.antonromanov.arnote.old.utils.ArNoteUtils.getCurrentMonth;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WishTests {


    @Spy
    private WishRepository wishRepository;

    @Spy
    private SalaryRepository salaryRepository;

    @Spy
    private WishMapperImpl mapper;

@Mock
   UserService userService;

    @Mock
    WishServiceImpl service;


  /*  @InjectMocks
    private WishServiceImpl service;*/


    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }


    /**
     * Тестовый юзер.
     *
     * @return
     */
    private ArNoteUser prepareUser() {
        ArNoteUser user = new ArNoteUser();
        user.setId(1);
        user.setSortMode(SortMode.ALL);
        return user;
    }

    /**
     * Тестовая зарплата.
     *
     * @return
     */
    private Salary prepareTestSalary(ArNoteUser user) {
        Salary salary = new Salary();
        salary.setId(1);
        salary.setUser(user);
        salary.setFullSalary(100000);
        salary.setResidualSalary(50000);
        return salary;
    }

   /* @Test
    public void learnVerifyMethod() {
        WishRq newWish = new WishRq();
        newWish.setId(1L);
        newWish.setWishName("777");

        ArgumentCaptor<WishRq> requestCaptor = ArgumentCaptor.forClass(WishRq.class);

        when(userService.getUserFromPrincipal()).thenReturn(prepareUser());
        //when(service.updateWish(newWish)).thenReturn(WishRs.builder().id(1).build());
        given(service.updateWish(newWish))
                .willReturn(WishRs.builder().id(1).build());

        service.updateWish(any());
        verify(service, times(1)).updateWish(requestCaptor.capture());
    }*/


    /**
     * Тестируем список желаний, сгруппированный по месяцам, заполненный только лишь моками с priorityGroup = NULL.
     */
    @Test
    public void testGroupedWishListWishOnlyNullPriorityWishes() {

        ArNoteUser user = prepareUser();
        Salary salary = prepareTestSalary(user);

        List<Wish> wishList = new ArrayList<>();

        for (int i = 0; i < 5; i++) { // создаем желания
            Wish w = new Wish();
            w.setId(i);
            w.setPrice(100);
            w.setWishName("Wish " + i);
            w.setUser(user);
            w.setArchive(false);
            wishList.add(w);
        }


        when(salaryRepository.getLastSalaryListByUserDesc(user)).thenReturn(Arrays.asList(salary));
        when(wishRepository.getAllWithGroupOrder(user)).thenReturn(wishList);
        assertEquals(1, service.getAllWishesWithGroupPriority(user).size());
        assertEquals(5, service.getAllWishesWithGroupPriority(user).get(0).getWishes().size());
    }

    /**
     * Тестируем список желаний, сгруппированный по месяцам, заполненный только лишь моками с priorityGroup = NULL.
     */
    @Test
    public void testGroupedWishListWishOnlyPriorityWishes() {

        ArNoteUser user = prepareUser();

        Salary salary = prepareTestSalary(user);

        List<Wish> wishList = new ArrayList<>();

        for (int i = 0; i < 5; i++) { // создаем желания
            Wish w = new Wish();
            w.setId(i);
            w.setPrice(100);
            w.setWishName("Wish " + i);
            w.setUser(user);
            w.setArchive(false);
            w.setPriorityGroup(i);
            wishList.add(w);
        }


        when(salaryRepository.getLastSalaryListByUserDesc(user)).thenReturn(Arrays.asList(salary));
        when(wishRepository.getAllWithGroupOrder(user)).thenReturn(wishList);
        assertEquals(5, service.getAllWishesWithGroupPriority(user).size());
        assertEquals(1, service.getAllWishesWithGroupPriority(user).get(0).getWishes().size());
        assertEquals(1, service.getAllWishesWithGroupPriority(user).get(1).getWishes().size());
        assertEquals(1, service.getAllWishesWithGroupPriority(user).get(2).getWishes().size());
        assertEquals(getCurrentMonth(), service.getAllWishesWithGroupPriority(user).get(0).getMonthNumber());
    }

    /**
     * Тестируем правильную сортировку для группированного списка желаний.
     */
    @Test
    public void testGroupedWishListSorting() {


      /*  Integer i = Integer.MAX_VALUE;
        Integer j = i+1;
        int u = 1;
        assertEquals(1, u+0);
*/

        ArNoteUser user = prepareUser();
        user.setSortMode(SortMode.PRICE_DESC);
        Salary salary = prepareTestSalary(user);
        List<Wish> wishList = new ArrayList<>();

        for (int i = 0; i < 5; i++) { // создаем желания
            Wish w = new Wish();
            w.setId(i);
            w.setPrice(100);
            w.setWishName("Wish " + i);
            w.setUser(user);
            w.setArchive(false);
            w.setPriorityGroup(i);
            wishList.add(w);
        }

        for (int i = 6; i < 9; i++) { // Добавляем желания для проверки сортировки
            Wish w = new Wish();
            w.setId(i);
            w.setPrice(100 + i);
            w.setWishName("Wish " + i);
            w.setUser(user);
            w.setArchive(false);
            w.setPriorityGroup(1);
            wishList.add(w);
        }


        when(salaryRepository.getLastSalaryListByUserDesc(user)).thenReturn(Arrays.asList(salary));
        when(wishRepository.getAllWithGroupOrder(user)).thenReturn(wishList);
        List<GroupedWishRs> listForTestSorting = service.getAllWishesWithGroupPriority(user).get(0).getWishes();
        assertEquals(Integer.valueOf(108), listForTestSorting.get(0).getPrice());
        assertEquals(Integer.valueOf(107), listForTestSorting.get(1).getPrice());
        assertEquals(Integer.valueOf(106), listForTestSorting.get(2).getPrice());

    }
}
