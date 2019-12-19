package com.example.coffee_n_go;

import java.util.ArrayList;

public class Order {

    private String OrderID;
    private ArrayList<String>products;
    private String CustomerName;
    private String CustomerPhone;


    public Order(){}
    public Order(String orderName, String orderPhone, ArrayList<String>myProd, String orderid) {
        CustomerName = orderName;
        CustomerPhone = orderPhone;
        products = myProd;
        OrderID = orderid;
    }

    public String getOrderID() {
        return OrderID;
    }

    public void setOrderID(String orderID) {
        OrderID = orderID;
    }

    public ArrayList<String> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<String> products) {
        this.products = products;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public String getCustomerPhone() {
        return CustomerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        CustomerPhone = customerPhone;
    }
}
