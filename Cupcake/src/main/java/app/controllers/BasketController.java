package app.controllers;

import app.entities.Basket;
import app.entities.Orderline;
import io.javalin.http.Context;

import java.util.List;

public class BasketController {
    Basket basket = new Basket();
    public static void ShowAllOrderlines(Basket basket, Context ctx)
    {
        List <Orderline> orderlines = basket.getOrderlines();

        ctx.sessionAttribute("orderlines", orderlines);
        ctx.render("/Cart");
    }
}
