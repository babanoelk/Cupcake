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

    public Account (int id, String name, String email, String password, boolean isAdmin){

    }
    public Account (int id, String name, String email, String password, boolean isAdmin, int balance){

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
