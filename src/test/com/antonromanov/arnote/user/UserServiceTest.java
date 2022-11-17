package com.antonromanov.arnote.user;

import com.antonromanov.arnote.domain.user.service.UserService;
import com.antonromanov.arnote.domain.wish.enums.FilterMode;
import com.antonromanov.arnote.domain.wish.enums.UserSettingType;
import com.antonromanov.arnote.sex.model.ArNoteUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.HashMap;
import java.util.Map;
import static org.assertj.core.api.Java6Assertions.assertThat;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class UserServiceTest {

   /* @Autowired
    private UserService userService;*/



    /**
     * Тестируем метод CheckAndSaveUserSettings на нулевые значения
     */
   /* @Test
    public void testCheckAndSaveUserSettingsMethodForNullValues() {

        ArNoteUser user = new ArNoteUser();
       // user.setId(1);

        Map<UserSettingType, String> h = new HashMap<UserSettingType, String>() {{
            put(UserSettingType.FILTER,null);
        }};

        ArNoteUser processedUser = userService.checkAndSaveUserSettings(user, h);
     //   assertThat(processedUser.getFilterMode()).isEqualTo(FilterMode.NONE);
    }*/
}
