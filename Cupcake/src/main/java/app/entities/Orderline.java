package app.entities;

public class Orderline {
    private int id;
    private int amount;
    private Bottom bottom;
    private Topping topping;

    public Orderline(int amount, Bottom bottom, Topping topping) {
        this.amount = amount;
        this.bottom = bottom;
        this.topping = topping;
    }
    public Orderline(int id, int amount, Bottom bottom, Topping topping) {
        this.id = id;
        this.amount = amount;
        this.bottom = bottom;
        this.topping = topping;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Topping: "+topping.getName() + " Bottom: " + bottom.getName();
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


