package app.entities;

import java.util.List;

public class Account {
    private int id;
    private String name;
    private String email;
    private String password;
    private boolean isAdmin;
    private int balance;
    private List<Order> orders;

    public Account(int id, String name, String email, String password, boolean isAdmin) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public Account(int id, String name, String email, String password, boolean isAdmin, int balance) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.isAdmin = isAdmin;
        this.balance = balance;
    }

    public static void put(String email, Account account) {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public int getBalance() {
        return balance;
    }
    public void setBalance(int newBalance) {
    }

    public List<Order> getAllOrders() {
        return orders;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", isAdmin=" + isAdmin +
                ", balance=" + balance +
                ", orders=" + orders +
                '}';
    }

    public void addOrder(){

    }

}
