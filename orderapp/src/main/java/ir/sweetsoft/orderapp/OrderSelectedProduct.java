package ir.sweetsoft.orderapp;

import com.google.gson.Gson;

public class OrderSelectedProduct{
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Long getItemID() {
        return itemID;
    }

    public void setItemID(Long itemID) {
        this.itemID = itemID;
    }

    private int count;

    public OrderSelectedProduct(Long itemID,int count) {
        this.count = count;
        this.itemID = itemID;
    }
    public String getJSON(){
        return (new Gson()).toJson(this);
    }
    public static OrderSelectedProduct createFromJson(String json){
        return new Gson().fromJson(json,OrderSelectedProduct.class);
    }
    private Long itemID;
}