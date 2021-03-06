package com.example.coffee_n_go;

import java.util.ArrayList;

public class Order {

    private String OrderID;
    private ArrayList<Product>Products;
    private String CustomerName;
    private String CustomerPhone;
    private Double Price;
    private boolean TakeAway;
    private String Status;
    private String Date;

    public Order(){}
    public Order(String orderName, String orderPhone,boolean takeAway, ArrayList<Product>myProd, String orderid,Double price,String status,String date) {
        CustomerName = orderName;
        CustomerPhone = orderPhone;
        Products = myProd;
        OrderID = orderid;
        Price=price;
        TakeAway=takeAway;
        Status=status;
        Date=date;
    }

    public String getOrderID() {
        return OrderID;
    }

    public void setOrderID(String orderID) {
        OrderID = orderID;
    }

    public ArrayList<Product> getProducts() {
        return Products;
    }

    public void setProducts(ArrayList<Product> products) {
        Products = products;
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

    public Double getPrice() {
        return Price;
    }

    public void setPrice(Double price) {
        Price = price;
    }

    public String toString(){
        String s=Date+"\n"+getCustomerName()+"\n"+getCustomerPhone()+"\nTake Away : "+TakeAway+"\n"+"\nStatus: "+getStatus()+"\n"+getProducts().toString()+"\n"+getPrice()+"\n";
        return s;
    }

    public boolean isTakeAway() {
        return TakeAway;
    }

    public void setTakeAway(boolean takeAway) {
        TakeAway = takeAway;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
