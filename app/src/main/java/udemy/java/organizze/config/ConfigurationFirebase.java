package udemy.java.organizze.config;

import com.google.firebase.auth.FirebaseAuth;

public class ConfigurationFirebase {

    private static FirebaseAuth userAuthentication;

    //retorno a instancia do FirebaseAuth
    public  static FirebaseAuth getUserAuthentication() {
        if(userAuthentication == null ){
            userAuthentication = FirebaseAuth.getInstance();
        }
        return userAuthentication;

    }
}
