package udemy.java.organizze.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

import udemy.java.organizze.config.ConfigurationFirebase;
import udemy.java.organizze.databinding.ActivityLoginBinding;
import udemy.java.organizze.model.User;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    private FirebaseAuth userAuthentication;
    private User user ;

    private EditText enterEmail, enterPassword;
    private Button loginAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        enterEmail = binding.editTextLoginEmail;
        enterPassword = binding.editTextLoginPassword;
        loginAccount = binding.buttonLogin;

        loginAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String textEmail    = enterEmail.getText().toString();
                String textPassword = enterPassword.getText().toString();

                if (!textEmail.isEmpty()){
                    if (!textPassword.isEmpty()){

                        user = new User();
                        user.setEmail(textEmail);
                        user.setPassword(textPassword);

                        loginAccount();

                    }else {
                        Toast.makeText(LoginActivity.this, "Intreduza a senha!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Intreduza o Email!", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void loginAccount() {
        userAuthentication = ConfigurationFirebase.getUserAuthentication();
        userAuthentication.signInWithEmailAndPassword(
                user.getEmail(),
                user.getPassword()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    Toast.makeText(LoginActivity.this, "Sucesso realizar login!", Toast.LENGTH_SHORT).show();
                    goToMainActivity();

                }else {

                    String exception;
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        exception = "Introduza um email valido! ";
                    } catch (FirebaseAuthInvalidUserException e){
                        exception = "Utilizador e e-amil não existe! ";
                    } catch (Exception e) {
                        exception = "Erro ao realizar login: " + e.getMessage();
                        e.printStackTrace();
                    }
                    Toast.makeText(LoginActivity.this, exception, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void goToMainActivity() {
        startActivity( new Intent(this, MainActivity.class));
        finish();
    }
}