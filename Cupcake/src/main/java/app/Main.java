package app;

import app.config.ThymeleafConfig;
import app.controllers.AccountController;
import app.controllers.BasketController;
import app.controllers.CakeController;
import app.persistence.ConnectionPool;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinThymeleaf;

public class Main {
    private static final String USER = "postgres";
    private static final String PASSWORD = "Ys240514";
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

        app.get("/", ctx -> CakeController.loadFrontPageData(ctx, connectionPool));
        app.post("/addcupcake", ctx -> BasketController.addOrderline(ctx, connectionPool));
        app.get("/cart", ctx -> BasketController.showAllOrderlines(ctx));

        app.get("/login", ctx -> ctx.render("loginpage.html"));
        app.post("/loggedfrontpage", ctx -> AccountController.loginFrontPage(ctx, connectionPool));
        app.post("/loggedbasket", ctx -> AccountController.loginBasket(ctx, connectionPool));
        app.get("/createaccount", ctx -> ctx.render("create-account.html"));
        app.post("/account-created", ctx -> AccountController.createAccount(ctx, connectionPool));
        app.post("/delete", ctx -> BasketController.deleteOrderline(ctx));
        app.post("/addmore", ctx -> BasketController.addMoreCupcakes(ctx, connectionPool));
        app.get("/min-side", ctx -> AccountController.getOrders(ctx, connectionPool));
        app.get("/admin-kunde-side", ctx -> AccountController.getAllCustomers(ctx, connectionPool));
        app.get("/admin-ordre-side", ctx -> AccountController.getAllOrders(ctx, connectionPool));
        app.post("/ordernow", ctx -> BasketController.orderNow(ctx, connectionPool));
        app.post("/withdraw", ctx -> BasketController.executeOrder(ctx, connectionPool));
        app.post("/kundens-ordrer-side", ctx -> AccountController.getOrdersByID(ctx,connectionPool));
        app.get("/logout", ctx -> AccountController.logout(ctx));
        app.post("/admin-kunde-side", ctx -> AccountController.getAllCustomers(ctx, connectionPool));
        app.get("/kundens-ordrer-side", ctx -> AccountController.getOrdersByID(ctx, connectionPool));

    }
}