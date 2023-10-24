package app.entities;

public class Order {
    private int id;
    private date orderDate;
    private int orderSum;
    private List<Oderline> orderlines;

    public Order(int id, date orderDate, int orderSum, List<Oderline> orderlines) {
        this.id = id;
        this.orderDate = orderDate;
        this.orderSum = orderSum;
        this.orderlines = orderlines;
    }

    public int getId() {
        return id;
    }

    public date getOrderDate() {
        return orderDate;
    }

    public int getOrderSum() {
        return orderSum;
    }

    public List<Oderline> getOrderlines() {
        return orderlines;
    }
}
