package udemy.java.organizze.activities;

import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import udemy.java.organizze.databinding.ActivityExpenseBinding;
import udemy.java.organizze.helper.DateCustom;

public class ExpenseActivity extends AppCompatActivity {

    private ActivityExpenseBinding binding;

    private TextInputEditText valueDate, valueCategory, valueDescription;
    private EditText imputValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityExpenseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        valueDate = binding.editTextExpenseDate;
        valueCategory = binding.editTextExpenseCategory;
        valueDescription = binding.editTextExpenseDescription;
        imputValue = binding.editTexExpense;

        valueDate.setText(DateCustom.actualDate());


    }

}