package udemy.java.organizze.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import udemy.java.organizze.databinding.ActivityReceivedBinding;

public class ReceivedActivity extends AppCompatActivity {

    private ActivityReceivedBinding binding;


    private TextInputEditText valueDate, valueCategory, valueDescription;
    private EditText imputeValue;

    private FloatingActionButton fabReceived;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityReceivedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        imputeValue = binding.editTextReceived;
        valueDate = binding.editTextReceivedDate;
        valueCategory = binding.editTextReceivedCategory;
        valueDescription = binding.editTextReceivedDescription;
        fabReceived = binding.fabReceivedDone;



        fabReceived.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmationValuesExpenses();
            }
        });

    }
    public boolean confirmationValuesExpenses(){

        String textImputeValue = imputeValue.getText().toString();
        String textDate= valueDate.getText().toString();
        String textCategory = valueCategory.getText().toString();
        String textDescription = valueDescription.getText().toString();

        if (!textImputeValue.isEmpty()) {
            if (!textDate.isEmpty()) {
                if (!textCategory.isEmpty()) {
                    if (!textDescription.isEmpty()) {
                        return true;
                    }else {
                        Toast.makeText(this, "Descrição não foi intreduzido!" , Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }else {
                    Toast.makeText(this, "Categoria não foi intreduzido!" , Toast.LENGTH_SHORT).show();
                    return false;
                }
            }else {
                Toast.makeText(this, "Data não foi intreduzido!" , Toast.LENGTH_SHORT).show();
                return false;
            }
        }else {
            Toast.makeText(this, "Valor não foi intreduzido!" , Toast.LENGTH_SHORT).show();
            return false;
        }

    }
}