package com.antonromanov.arnote.user;

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
