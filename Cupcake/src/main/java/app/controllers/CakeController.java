package app.controllers;

import app.entities.*;
import app.exceptions.DatabaseException;
import app.persistence.BottomMapper;
import app.persistence.ConnectionPool;
import app.persistence.ToppingMapper;
import io.javalin.http.Context;

import java.util.List;

public class CakeController {


    public static void loadFrontPageData(Context ctx, ConnectionPool connectionPool) {

        try {
            Basket basket = new Basket();
            Account account = null; //test

            ctx.sessionAttribute("currentBasket", basket);
            ctx.sessionAttribute("currentAccount", account); //test

            ctx.attribute("orderlines",basket.getOrderlines());

            List<Topping> toppings = ToppingMapper.getAllToppings(connectionPool);
            List<Bottom> bottoms = BottomMapper.getAllBottoms(connectionPool);

            ctx.attribute("toppingsList", toppings);
            ctx.attribute("bottomsList", bottoms);

            ctx.render("index.html");

        } catch (DatabaseException e) {
            ctx.attribute("message", e.getMessage());
            ctx.render("index.html");
        }
    }
}