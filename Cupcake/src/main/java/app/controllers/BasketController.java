package app.controllers;

import app.entities.*;
import app.exceptions.DatabaseException;
import app.persistence.BottomMapper;
import app.persistence.ConnectionPool;
import app.persistence.OrderMapper;
import app.persistence.ToppingMapper;
import io.javalin.http.Context;

import java.sql.SQLException;
import java.util.List;

public class BasketController {

    Basket basket = new Basket();

    public static void showAllOrderlines(Context ctx) {
        Basket basket = ctx.sessionAttribute("currentBasket");
        ctx.attribute("orderlines",basket.getOrderlines());
        ctx.render("cart.html");
    }


    //Metoden skal tilføje en ordrelinje til kurven
    public static void addOrderline(Context ctx, ConnectionPool connectionPool) throws DatabaseException {

        int toppingId = Integer.parseInt(ctx.formParam("top"));
        int bottomId = Integer.parseInt(ctx.formParam("bottom"));
        int amount = Integer.parseInt(ctx.formParam("antal"));

        Topping topping = ToppingMapper.getToppingById(toppingId, connectionPool);
        Bottom bottom = BottomMapper.getBottomById(bottomId, connectionPool);

        Orderline orderline = new Orderline(amount, bottom, topping);

        Basket basket = ctx.sessionAttribute("currentBasket");
        basket.addOrderline(orderline);

        List<Topping> toppings = ToppingMapper.getAllToppings(connectionPool);
        List<Bottom> bottoms = BottomMapper.getAllBottoms(connectionPool);

        ctx.attribute("toppingsList", toppings);
        ctx.attribute("bottomsList", bottoms);

        ctx.attribute("orderlines",basket.getOrderlines());
        ctx.render("index.html");
    }

    public void executeOrder(Context ctx, ConnectionPool connectionPool) throws DatabaseException, SQLException {
        if (true) { //todo: Lav en metode her som tjekker om personen er logget ind eller ej
            Account account = ctx.sessionAttribute("currentAccount");
            Basket basket = ctx.sessionAttribute("currentBasket");
            Order order = OrderMapper.addOrder(account, basket.getOrderlines(), connectionPool);
            OrderMapper.addOrderline(order, basket.getOrderlines(), connectionPool);
        } else {
            //todo: lav en metode som enten opretter en bruger eller logger ind
        }
    }

}
