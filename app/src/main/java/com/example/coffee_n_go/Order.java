package com.example.coffee_n_go;

import java.util.ArrayList;

public class Order {

    String OrderID;
    //ArrayList<String>products;
    String OrderName;
    String OrderPhone;
    String myDrink;

    public String getOrderName() {
        return OrderName;
    }

    public String getOrderPhone() {
        return OrderPhone;
    }

    public String getMyDrink() {
        return myDrink;
    }

    public Order(String orderName, String orderPhone, String myDrink, String orderid) {
        OrderName = orderName;
        OrderPhone = orderPhone;
        this.myDrink = myDrink;
        this.OrderID = orderid;
    }
}
