package app.controllers;

import app.entities.Account;
import app.exceptions.DatabaseException;
import app.persistence.AccountMapper;
import app.persistence.ConnectionPool;
import io.javalin.http.Context;

public class AccountController {

    public static void login(Context ctx, ConnectionPool connectionPool) {


        String name = ctx.formParam("email");
        String password = ctx.formParam("password");


        try {

            Account account = AccountMapper.login(name, password, connectionPool);
            ctx.sessionAttribute("currentUser", account);

            ctx.render("index.html");
        }
        catch (DatabaseException e)
        {
            ctx.attribute("message", e.getMessage());
            ctx.render("loginpage.html");
        }
    }
}
