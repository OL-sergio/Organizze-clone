package udemy.java.organizze.config;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ConfigurationFirebase {

    private static FirebaseAuth userAuthentication;
    private static DatabaseReference reference;

    //retorna a instancia do FirebaseDatabase
    public static DatabaseReference getDatabaseReference(){
        if (reference == null) {
            reference = FirebaseDatabase.getInstance().getReference();
        }

        return reference;
    }

    //retorno a instancia do FirebaseAuth
    public  static FirebaseAuth getUserAuthentication() {
        if(userAuthentication == null ){
            userAuthentication = FirebaseAuth.getInstance();
        }
        return userAuthentication;

    }
}
