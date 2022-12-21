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
import udemy.java.organizze.databinding.ActivityRevenueBinding;
import udemy.java.organizze.helper.Base64Custom;
import udemy.java.organizze.helper.DateCustom;
import udemy.java.organizze.model.Movements;
import udemy.java.organizze.model.User;

public class RevenueActivity extends AppCompatActivity {

    private ActivityRevenueBinding binding;

    private DatabaseReference firebaseRef = ConfigurationFirebase.getDatabaseReference();
    private FirebaseAuth userAuthentication = ConfigurationFirebase.getUserAuthentication();

    private TextInputEditText valueDate, valueCategory, valueDescription;
    private EditText imputeValue;
    private Movements movements;
    private FloatingActionButton fabReceived;

    private Double retrievedValue;
    private Double totalRevenue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRevenueBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        imputeValue = binding.editTextReceived;
        valueDate = binding.editTextReceivedDate;
        valueCategory = binding.editTextReceivedCategory;
        valueDescription = binding.editTextReceivedDescription;
        fabReceived = binding.fabReceivedDone;

        valueDate.setText(DateCustom.actualDate());
        retrievedTotalRevenue();

        fabReceived.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveRevenue();

            }
        });

    }

    public void saveRevenue(){

        if ( confirmationValuesRevenue() ) {

            movements = new Movements();
            String date = valueDate.getText().toString();
            retrievedValue = Double.parseDouble(imputeValue.getText().toString());

            movements.setTransference(retrievedValue);
            movements.setCategory(valueCategory.getText().toString());
            movements.setDescription(valueDescription.getText().toString());
            movements.setData(valueDate.getText().toString());
            movements.setType("revenue");

            Double updateRevenue = totalRevenue + retrievedValue;
            updateRevenue(updateRevenue);
            movements.save(date);

            finish();
        }
    }

    public boolean confirmationValuesRevenue(){

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
                        Toast.makeText(this, "Descrição não foi intreduzida!" , Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }else {
                    Toast.makeText(this, "Categoria não foi intreduzida!" , Toast.LENGTH_SHORT).show();
                    return false;
                }
            }else {
                Toast.makeText(this, "Data não foi intreduzida!" , Toast.LENGTH_SHORT).show();
                return false;
            }
        }else {
            Toast.makeText(this, "Valor não foi intreduzido!" , Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public void retrievedTotalRevenue(){
        String userEmail = userAuthentication.getCurrentUser().getEmail();
        String idUSer = Base64Custom.encryptionBase64(userEmail);
        DatabaseReference userRef = firebaseRef.child("users").child(idUSer);

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                    totalRevenue = user.getRevenueTotal();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void updateRevenue(Double updateRevenue) {
        String userEmail = userAuthentication.getCurrentUser().getEmail();
        String idUSer = Base64Custom.encryptionBase64(userEmail);
        DatabaseReference userRef = firebaseRef.child("users").child(idUSer);

        userRef.child("revenueTotal").setValue(updateRevenue);
    }
}