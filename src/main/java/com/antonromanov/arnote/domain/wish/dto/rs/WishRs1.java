package com.antonromanov.arnote.domain.wish.dto.rs;


public class WishRs1 {

    private  long id;
    private  String wishName;
   /* private  Integer price;
    private  Integer priority;
    private  Boolean ac;
    private  String description;
    private  String url;
    private  Integer priorityGroup;
    private  Integer priorityGroupOrder;
    private  String month;*/


    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setWishName(String wishName) {
        this.wishName = wishName;
    }

    public WishRs1(long id, String wishName) {
        this.id = id;
        this.wishName = wishName;
    }

    public WishRs1() {
    }
}

