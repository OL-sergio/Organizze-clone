package udemy.java.organizze.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.slide.FragmentSlide;

import udemy.java.organizze.R;
import udemy.java.organizze.config.ConfigurationFirebase;
import udemy.java.organizze.databinding.IntroMenuBinding;

public class MenuActivity extends IntroActivity {

    private FirebaseAuth userAuthentication;

    private IntroMenuBinding binding;
    private Button buttonGoLogin ;
    private TextView imageViewGoRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       /* *binding = IntroMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

         buttonGoLogin = binding.buttonLogin;
         imageViewGoRegister = binding.textViewGoToRegister;
         */

        setButtonBackVisible(false);
        setButtonNextVisible(false);

        addSlide(new FragmentSlide.Builder()
                .background(R.color.white)
                .fragment(R.layout.intro_1)
                .build()
        );

        addSlide(new FragmentSlide.Builder()
                .background(R.color.white)
                .fragment(R.layout.intro_2)
                .build()
        );

        addSlide(new FragmentSlide.Builder()
                .background(R.color.white)
                .fragment(R.layout.intro_3)
                .build()
        );

        addSlide(new FragmentSlide.Builder()
                .background(R.color.white)
                .fragment(R.layout.intro_4)
                .canGoBackward(true)
                .canGoForward(true)
                .build()
        );

        addSlide(new FragmentSlide.Builder()
                .background(R.color.white)
                .fragment(R.layout.intro_menu)
                .canGoBackward(true)
                .canGoForward(false)
                .build()
        );

        /*
        *
        setButtonBackVisible(false);
        setButtonNextVisible(false);

        addSlide(new SimpleSlide.Builder()
                .title("Titulo")
                .description("Descrição")
                .image(R.drawable.um)
                .background(R.color.purple_200)
                .build()
        );

        addSlide(new SimpleSlide.Builder()
                .title("Titulo2")
                .description("Descrição")
                .image(R.drawable.dois)
                .background(R.color.purple_500)
                .build()
        );

        addSlide(new SimpleSlide.Builder()
                .title("Titulo3")
                .description("Descrição")
                .image(R.drawable.tres)
                .background(R.color.purple_700)
                .build()
        );
         */
        /*
        buttonGoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(getApplicationContext(), LoginActivity.class));
            }
        });

        imageViewGoRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });
       */
    }
    @Override
    protected void onStart() {
        super.onStart();
        userLogged();
    }



    public void btLogin (View view){
        startActivity( new Intent(getApplicationContext(), LoginActivity.class));
    }

    public void btRegister (View view) {
        startActivity( new Intent(getApplicationContext(), RegisterActivity.class));
    }

    public void userLogged() {

        userAuthentication = ConfigurationFirebase.getUserAuthentication();

        if (userAuthentication.getCurrentUser() != null){

            goToMainActivity();
        }
    }

    private void goToMainActivity() {
        startActivity( new Intent(this, MainActivity.class));
        //userAuthentication.signOut();
        finish();
    }
}