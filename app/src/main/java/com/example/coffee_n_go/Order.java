package com.example.coffee_n_go;

public class Order {

    String OrderID;
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
