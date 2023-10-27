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
            ctx.sessionAttribute("currentAccount", account);
            ctx.sessionAttribute("is_admin", account.isAdmin());

            if (account.isAdmin() == true) {
                ctx.render("admin-side.html");

            }
            else {
                CakeController.loadFrontPageData( ctx, connectionPool);
            }
        } catch (DatabaseException e) {
            ctx.attribute("message", e.getMessage());
            ctx.render("loginpage.html");
        }
    }



    public static void loginFromBasket(Context ctx, ConnectionPool connectionPool) {
        String name = ctx.formParam("email");
        String password = ctx.formParam("password");
        try {

            Account account = AccountMapper.login(name, password, connectionPool);
            ctx.sessionAttribute("currentAccount", account);
            ctx.sessionAttribute("is_admin", account.isAdmin());

            if (account.isAdmin() == true) {
                ctx.render("admin-side.html");

            }
            else {
                CakeController.loadPaymentPageData( ctx, connectionPool);
            }
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
        } else {
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

    public static void getAllCustomers(Context ctx, ConnectionPool connectionPool) throws DatabaseException {

        List<Account> accountList;

        accountList = AccountMapper.getAllCustomers(connectionPool);

        ctx.attribute("accountList", accountList);

        ctx.render("admin-kunde-side.html");

    }

    public static void getAllOrders(Context ctx, ConnectionPool connectionPool) throws DatabaseException {

        List<Order> orderList;

        orderList = AccountMapper.getAllOrders(connectionPool);

        ctx.attribute("order_list", orderList);

        ctx.render("alle-ordrer-side-admin.html");

    }
}
