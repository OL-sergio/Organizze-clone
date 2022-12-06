package udemy.java.organizze.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import udemy.java.organizze.config.ConfigurationFirebase;

public class User {

    private String idUser;
    private String name;
    private String email;
    private String password;
    private Double revenueTotal = 0.00;
    private Double expenseTotal = 0.00;


    public User() {
    }

    public void save(){
        DatabaseReference firebase = ConfigurationFirebase.getDatabaseReference();
        firebase.child("users")
                .child(this.idUser)
                .setValue(this);
    }


    public Double getRevenueTotal() {
        return revenueTotal;
    }

    public void setRevenueTotal(Double revenueTotal) {
        this.revenueTotal = revenueTotal;
    }

    public Double getExpenseTotal() {
        return expenseTotal;
    }

    public void setExpenseTotal(Double expenseTotal) {
        this.expenseTotal = expenseTotal;
    }

    @Exclude
    public String getIdUser() { return idUser; }

    public void setIdUser(String idUser) { this.idUser = idUser; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @Exclude
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
