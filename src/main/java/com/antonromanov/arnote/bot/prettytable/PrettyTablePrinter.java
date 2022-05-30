package com.antonromanov.arnote.bot.prettytable;

import com.antonromanov.arnote.bot.userdata.UserData;
import com.antonromanov.arnote.model.wish.Wish;
import org.apache.commons.lang3.StringUtils;
import org.sk.PrettyTable;
import java.util.List;

public class PrettyTablePrinter {

    public String prepareWishTable(List<Wish> wishList) {
        PrettyTable table = new PrettyTable("ID", "Имя", "$");
        UserData userData = UserData.getInstance();
        Integer maxWidth = userData.getDisplayType() == null ? 15 : userData.getDisplayType().getMaxWidth();

        wishList.forEach(e -> table.addRow(String.valueOf(e.getId()),
                StringUtils.abbreviate(String.valueOf(e.getWish()), maxWidth),
                        String.valueOf(e.getPrice())));
        return table.toString();
    }
}
