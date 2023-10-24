package app.controllers;

import app.entities.Basket;
import app.entities.Orderline;
import app.entities.Topping;
import app.entities.Bottom;
import app.exceptions.DatabaseException;
import app.persistence.BasketMapper;
import app.persistence.BottomMapper;
import app.persistence.ConnectionPool;
import app.persistence.ToppingMapper;
import io.javalin.http.Context;

public class BasketController {

    //Metoden skal tilføje en ordrelinje til kurven
    public static void addOrderline(Context ctx, ConnectionPool connectionPool) throws DatabaseException {

        int toppingId = Integer.parseInt(ctx.formParam("topping"));
        int bottomId = Integer.parseInt(ctx.formParam("bottom"));
        int amount = Integer.parseInt(ctx.formParam("amount"));

        Topping topping = ToppingMapper.getToppingById(toppingId, connectionPool);
        Bottom bottom = BottomMapper.getBottomById(bottomId, connectionPool);

        Orderline orderline = new Orderline(amount, bottom, topping);

        Basket basket = ctx.sessionAttribute("currentBasket");
        basket.addOrderline(orderline);
        ctx.render("index.html");

    }
}
