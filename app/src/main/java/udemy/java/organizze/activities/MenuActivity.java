package udemy.java.organizze.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.slide.FragmentSlide;

import udemy.java.organizze.R;

public class MenuActivity extends IntroActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
    }

    public void btLogin (View view){
            startActivity( new Intent(this, LoginActivity.class));
    }

    public void btRegister (View view) {
        startActivity( new Intent(this, RegisterActivity.class));
    }
}