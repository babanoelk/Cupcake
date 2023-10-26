package app.controllers;

import app.entities.Account;
import app.entities.Order;
import app.exceptions.DatabaseException;
import app.persistence.AccountMapper;
import app.persistence.ConnectionPool;
import io.javalin.http.Context;

import java.util.List;

public class AccountController {

    public static void login(Context ctx, ConnectionPool connectionPool) {


        String name = ctx.formParam("email");
        String password = ctx.formParam("password");


        try {

            Account account = AccountMapper.login(name, password, connectionPool);
            ctx.sessionAttribute("currentUser", account);

            ctx.render("index.html");
        } catch (DatabaseException e) {
            ctx.attribute("message", e.getMessage());
            ctx.render("loginpage.html");
        }
    }

    public static void createAccount(Context ctx, ConnectionPool connectionPool) {
        String name = ctx.formParam("name");
        String email = ctx.formParam("email");
        String password = ctx.formParam("password");
        String password1 = ctx.formParam("password1");

        if (password.equals(password1)) {
            try {
                AccountMapper.createAccount(name, email, password, connectionPool);
                ctx.attribute("message", "Velkommen til OLSKER CUPCAKES familien!");
                ctx.render("test.html"); // todo: skal sende dig videre til "tak for din bestilling" side.
            } catch (DatabaseException e) {
                ctx.attribute("message", e.getMessage());
                ctx.render("create-account.html");
                }
            } else{
            ctx.attribute("message", "Dine kodeord stemmer ikke overens!");
            ctx.render("create-account.html");
            }

    }


    public static void getOrders(Context ctx, ConnectionPool connectionPool) throws DatabaseException {

        List<Order> ordersList;
        Account account = ctx.sessionAttribute("currentAccount");

        ordersList = AccountMapper.getAllOrdersByID(account.getId(), connectionPool);

        ctx.attribute("orderlines", ordersList);

        ctx.render("min-side.html");

    }

}
