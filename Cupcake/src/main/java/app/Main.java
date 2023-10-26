package app;

import app.config.ThymeleafConfig;
import app.controllers.CakeController;
import app.controllers.AccountController;
import app.persistence.ConnectionPool;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinThymeleaf;

public class Main {
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";
    private static final String URL = "jdbc:postgresql://localhost:5432/%s?currentSchema=public";
    private static final String DB = "cupcake";

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance(USER, PASSWORD, URL, DB);

    public static void main(String[] args) {
        // Initializing Javalin and Jetty webserver
        Javalin app = Javalin.create(config -> {
            config.staticFiles.add("/public");
            JavalinThymeleaf.init(ThymeleafConfig.templateEngine());
        }).start(7070);


        // Routing
        app.get("/", ctx ->  CakeController.startBasketSession(ctx, connectionPool));
        //app.post("/addcupcake", ctx -> CakeController.addToCart(ctx, connectionPool));
        //app.get("/", ctx -> ctx.render("index.html"));


        app.get("/login", ctx -> ctx.render("loginpage.html"));
        app.post("/loggedin", ctx -> AccountController.login(ctx, connectionPool));
        //app.post("/login", ctx -> AccountController.login(ctx, connectionPool));

        app.get("/createaccount", ctx -> ctx.render("create-account.html"));
        app.post("/account-created", ctx -> AccountController.createAccount(ctx, connectionPool));
    }
}