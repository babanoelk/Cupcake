package app.entities;

import java.util.Date;
import java.util.List;

public class Order {
    private int id;
    private Date orderDate;
    private int orderSum;
    private List<Orderline> orderlines;
    int accountID;

    public Order(int id, Date orderDate, int orderSum, List<Orderline> orderlines) {
        this.id = id;
        this.orderDate = orderDate;
        this.orderSum = orderSum;
        this.orderlines = orderlines;
    }

    public Order(int id, Date orderDate, int orderSum, int accountID) {
        this.id = id;
        this.orderDate = orderDate;
        this.orderSum = orderSum;
        this.accountID = accountID;
    }

    public int getAccountID() {
        return accountID;
    }

    public int getId() {
        return id;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public int getOrderSum() {
        return orderSum;
    }

    public List<Orderline> getOrderlines() {
        return orderlines;
    }
}
