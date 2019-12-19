package com.example.coffee_n_go;

import java.util.ArrayList;

public class Order {

    String OrderID;
    ArrayList<String>products;
    String OrderName;
    String OrderPhone;
//    String myDrink;

    public String getOrderName() {
        return OrderName;
    }

    public String getOrderPhone() {
        return OrderPhone;
    }

    public ArrayList<String> getMyProd() {
        return products;
    }

    public Order(String orderName, String orderPhone, ArrayList<String>myProd, String orderid) {
        OrderName = orderName;
        OrderPhone = orderPhone;
        products = myProd;
        this.OrderID = orderid;
    }
}
