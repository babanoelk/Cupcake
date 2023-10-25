package app.entities;

import java.util.List;
import io.javalin.http.Context;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class Basket {

    private List<Orderline> orderlines = new ArrayList<>();

    public void addOrderline(Orderline orderline){

        orderlines.add(orderline);
    }

    public List<Orderline> getOrderlines() {
        return orderlines;
    }
}
