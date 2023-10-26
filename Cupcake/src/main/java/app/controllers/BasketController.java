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

    public static void showAllOrderlines(Context ctx) {
        Basket basket = ctx.sessionAttribute("currentBasket");
        ctx.attribute("orderlines",basket.getOrderlines());
        ctx.render("cart.html");
    }


    public static void deleteOrderline(Context ctx) {
        Basket basket = ctx.sessionAttribute("currentBasket");

        int basketIndex = Integer.parseInt(ctx.formParam("order_id"));

        basket.getOrderlines().remove(basketIndex);
        ctx.sessionAttribute("currentBasket", basket);
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

        //Always remember to save session after manipulation
        ctx.sessionAttribute("currentBasket", basket);
        ctx.render("index.html");
    }

    public static void addMoreCupcakes(Context ctx, ConnectionPool connectionPool) {

        try {
            Basket basket = ctx.sessionAttribute("currentBasket");

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

    public static void executeOrder(Context ctx, ConnectionPool connectionPool) throws DatabaseException, SQLException {
        if (isAccountLoggedIn(ctx)) {
            Account account = ctx.sessionAttribute("currentAccount");
            Basket basket = ctx.sessionAttribute("currentBasket");
            Basket basket1 = ctx.sessionAttribute("currentBasket");
            int completeOrder = basket1.getOrderTotalPrice();
            withdrawPayment(ctx, account, completeOrder);
            Order order = OrderMapper.addOrder(account, basket.getOrderlines(), connectionPool);
            OrderMapper.addOrderline(order, basket.getOrderlines(), connectionPool);
        } else {
            ctx.render("loginpage.html");
        }
    }
    public static boolean isAccountLoggedIn(Context ctx){
        Account account = ctx.sessionAttribute("currentAccount");
        return account != null;
    }
    public static void withdrawPayment(Context ctx, Account account, int amountTowithdraw){
        int currentBalance = account.getBalance();
        if (currentBalance >= amountTowithdraw){
            int newBalance = currentBalance - amountTowithdraw;
            account.setBalance(newBalance);
            ctx.sessionAttribute("currentAccount", account);
        } else{
            ctx.attribute("message", "Beløbet kunne ikke trækkes fra din konto");
            ctx.render("index.html");
        }

    }
    
}
