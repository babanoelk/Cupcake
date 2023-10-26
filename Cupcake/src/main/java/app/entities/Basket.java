package app.entities;

import java.util.List;

import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import io.javalin.http.Context;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class Basket {

    private List<Orderline> orderlines = new ArrayList<>();

    public void addOrderline(Orderline orderline) {

        orderlines.add(orderline);
    }

    public List<Orderline> getOrderlines() {
        return orderlines;
    }

    //This method is only used int the cart.html. Thats why the methos is showen as not used
    public int getOrderTotalPrice() {
        int totalPrice = 0;

        for (Orderline orderline : orderlines) {
            totalPrice += orderline.getPricePrOrderLine();
        }


        return totalPrice;
    }

    public static void deleteOrderline(Context ctx) {

        int orderID = Integer.parseInt(ctx.formParam("orderline.id"));

    }
}


