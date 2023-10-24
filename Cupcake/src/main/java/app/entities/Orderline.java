package app.entities;

public class Orderline {
    private int id;
    private int amount;
    private Bottom bottom;
    private Topping topping;

    public int getId() {
        return id;
    }

    public int getAmount() {
        return amount;
    }

    public Bottom getBottom() {
        return bottom;
    }

    public Topping getTopping() {
        return topping;
    }
}
