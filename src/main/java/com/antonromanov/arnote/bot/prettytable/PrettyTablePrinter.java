package com.antonromanov.arnote.bot.prettytable;


import com.antonromanov.arnote.model.wish.Wish;
import org.sk.PrettyTable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PrettyTablePrinter {

    public String prepareWishTable(List<Wish> wishList) {
        PrettyTable table = new PrettyTable("ID", "Желание", "Цена");

        wishList.stream()
                .filter(v->v.getPriority()==1)
                .forEach(e-> table.addRow(String.valueOf(e.getId()),
                        String.valueOf(e.getWish()),
                        String.valueOf(e.getPrice())));
        return table.toString();
    }
}
