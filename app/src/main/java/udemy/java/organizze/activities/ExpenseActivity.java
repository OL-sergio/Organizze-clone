package udemy.java.organizze.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import udemy.java.organizze.databinding.ActivityExpenseBinding;
import udemy.java.organizze.helper.DateCustom;
import udemy.java.organizze.model.Movements;

public class ExpenseActivity extends AppCompatActivity {

    private ActivityExpenseBinding binding;

    private TextInputEditText valueDate, valueCategory, valueDescription;
    private EditText imputValue;
    private FloatingActionButton fabExpense;
    private Movements movements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityExpenseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        valueDate = binding.editTextExpenseDate;
        valueCategory = binding.editTextExpenseCategory;
        valueDescription = binding.editTextExpenseDescription;
        imputValue = binding.editTexExpense;
        fabExpense = binding.fabExpenseDone;

        valueDate.setText(DateCustom.actualDate());

        fabExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  saveExpense() ;  
            }
        });
    }

    private void saveExpense() {

        String date = valueDate.getText().toString();
        movements = new Movements();
        movements.setValue( Double.parseDouble(imputValue.getText().toString()) );
        movements.setCategory(valueCategory.getText().toString() );
        movements.setDescription(valueDescription.getText().toString() );
        movements.setData(valueDate.getText().toString());
        movements.setType("expense");

        movements.save(date);
    }
}