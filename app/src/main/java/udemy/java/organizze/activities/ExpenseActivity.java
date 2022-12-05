package udemy.java.organizze.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import udemy.java.organizze.config.ConfigurationFirebase;
import udemy.java.organizze.databinding.ActivityExpenseBinding;
import udemy.java.organizze.helper.Base64Custom;
import udemy.java.organizze.helper.DateCustom;
import udemy.java.organizze.model.Movements;
import udemy.java.organizze.model.User;

public class ExpenseActivity extends AppCompatActivity {

    private ActivityExpenseBinding binding;

    private final DatabaseReference firebaseRef = ConfigurationFirebase.getDatabaseReference();
    private final FirebaseAuth userAuthentication = ConfigurationFirebase.getUserAuthentication();
    private Movements movements;


    private TextInputEditText valueDate, valueCategory, valueDescription;
    private EditText imputeValue;
    private FloatingActionButton fabExpense;

    private Double totalExpense;
    private Double retrievedValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityExpenseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        imputeValue = binding.editTexExpense;
        valueDate = binding.editTextExpenseDate;
        valueCategory = binding.editTextExpenseCategory;
        valueDescription = binding.editTextExpenseDescription;
        fabExpense = binding.fabExpenseDone;

        valueDate.setText(DateCustom.actualDate());
        retrievedTotalExpense();

        fabExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  saveExpense() ;

            }
        });
    }

    private void saveExpense() {

        if ( confirmationValuesExpenses() ) {

            String date = valueDate.getText().toString();
            retrievedValue = Double.parseDouble(imputeValue.getText().toString());

            movements = new Movements();
            movements.setValue( retrievedValue );
            movements.setCategory(valueCategory.getText().toString() );
            movements.setDescription(valueDescription.getText().toString() );
            movements.setData(valueDate.getText().toString());
            movements.setType("expense");

            Double updatedExpense = totalExpense + retrievedValue;
            updatedExpense(updatedExpense);
            movements.save(date);

            finish();
        }
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

    public void retrievedTotalExpense() {
        String userEmail = userAuthentication.getCurrentUser().getEmail();
        String idUser = Base64Custom.encryptionBase64(userEmail);
        DatabaseReference userRef = firebaseRef.child("users").child(idUser);

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if (user != null) {
                    totalExpense = user.getExpenseTotal();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void updatedExpense(Double updatedExpense){
        String userEmail = userAuthentication.getCurrentUser().getEmail();
        String idUser = Base64Custom.encryptionBase64(userEmail);
        DatabaseReference userRef = firebaseRef.child("users").child(idUser);

        userRef.child("expenseTotal").setValue(updatedExpense);

    }
}