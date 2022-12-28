package udemy.java.organizze.model;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import udemy.java.organizze.config.ConfigurationFirebase;
import udemy.java.organizze.helper.Base64Custom;
import udemy.java.organizze.helper.DateCustom;

public class Movements {



    private String data;
    private String category;
    private String description;
    private String type;
    private double transference;
    private String key;

    public Movements() {
    }

    public void save(String date) {

        FirebaseAuth userAuthentication = ConfigurationFirebase.getUserAuthentication();
        DatabaseReference firebase = ConfigurationFirebase.getDatabaseReference();
        String idUser = Base64Custom.encryptionBase64(userAuthentication.getCurrentUser().getEmail());
        String monthYear = DateCustom.selectedDate(date);

      firebase.child("movements")
                .child(idUser)
                .child(monthYear)
                .push()
                .setValue(this);
    }

    public String getKey() { return key; }

    public void setKey(String key) { this.key = key; }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {return type;}

    public void setType(String type) {
        this.type = type;
    }

    public double getTransference() {return transference; }

    public void setTransference(double transference) {
        this.transference = transference;
    }
}
