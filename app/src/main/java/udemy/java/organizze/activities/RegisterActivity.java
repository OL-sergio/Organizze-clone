package udemy.java.organizze.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import udemy.java.organizze.config.ConfigurationFirebase;
import udemy.java.organizze.databinding.ActivityRegisterBinding;
import udemy.java.organizze.helper.Base64Custom;
import udemy.java.organizze.model.User;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    private EditText createName, createEmail, createPassword;
    private Button registerUser;
    private FirebaseAuth userAuthentication;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        createName =   binding.editTextRegisterNome;
        createEmail = binding.editTextRegisterEmail;
        createPassword = binding.editTextRegisterPassword;
        registerUser = binding.buttonRegister;

        registerUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String textName = createName.getText().toString();
                String textEmail = createEmail.getText().toString();
                String textPassword = createPassword.getText().toString();

                //Validar se os valores foram preenchidos

                if(!textName.isEmpty() ){
                    if (!textEmail.isEmpty()){
                        if (!textPassword.isEmpty()){

                            user = new User();
                            user.setName(textName);
                            user.setEmail(textEmail);
                            user.setPassword(textPassword);
                            createUser();

                        }else {
                            Toast.makeText(RegisterActivity.this, "Intreduza o senha!", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(RegisterActivity.this, "Intreduza o Email!", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(RegisterActivity.this, "Intreduza o nome!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void createUser(){
      userAuthentication = ConfigurationFirebase.getUserAuthentication();
      userAuthentication.createUserWithEmailAndPassword( user.getEmail(), user.getPassword()
      ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
          @Override
          public void onComplete(@NonNull Task<AuthResult> task) {
              if (task.isSuccessful()) {
                  String idUser = Base64Custom.encryptionBase64(user.getEmail());
                  user.setIdUser( idUser);
                  user.save();
                 finish();
              } else {

                  String exception;
                  try {
                      throw task.getException();
                  } catch (FirebaseAuthWeakPasswordException e){
                      exception = "Intreduza uma senha mais forte!";
                  } catch (FirebaseAuthInvalidCredentialsException e) {
                      exception = "Intreduza um email valido!";
                  } catch (FirebaseAuthUserCollisionException e){
                      exception = "Esta conta já existe!";
                  }catch (Exception e) {
                        exception = "Erro ao criar utilizador " + e.getMessage();
                        e.printStackTrace();
                  }
                  Toast.makeText(RegisterActivity.this, exception, Toast.LENGTH_SHORT).show();
              }
          }
      });
    }
}