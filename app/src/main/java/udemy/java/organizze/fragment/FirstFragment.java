package udemy.java.organizze.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.text.DecimalFormat;

import udemy.java.organizze.config.ConfigurationFirebase;
import udemy.java.organizze.databinding.FragmentFirstBinding;
import udemy.java.organizze.helper.Base64Custom;
import udemy.java.organizze.model.User;


public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;

    private final DatabaseReference firebaseRef = ConfigurationFirebase.getDatabaseReference();
    private final FirebaseAuth userAuthentication = ConfigurationFirebase.getUserAuthentication();

    private ValueEventListener valueEventListenerUser;

    private MaterialCalendarView calendarRevenue;

    private TextView valueUserName, valueBalance;
    private Double totalExpense = 0.0;
    private Double totalRevenue = 0.0;
    private Double userBalance  = 0.0;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        valueUserName = binding.textViewRevenueUserName;
        valueBalance = binding.textViewRevenueBalance;
        calendarRevenue = binding.calendarViewRevenueCalender;

    }

    @Override
    public void onResume() {
        configCalendarRevenue();
        retrieveBalance();
        super.onResume();
    }

    private void retrieveBalance() {
        String userEmail = userAuthentication.getCurrentUser().getEmail();
        String idUser = Base64Custom.encryptionBase64(userEmail);
        DatabaseReference userRef = firebaseRef.child("users").child(idUser);

        Log.i("onStop", "event was added");
         valueEventListenerUser = userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                User user = snapshot.getValue(User.class);
                if (user != null) {
                    totalExpense = user.getExpenseTotal();
                    totalRevenue = user.getRevenueTotal();
                }
                userBalance = totalRevenue - totalExpense;

                DecimalFormat decimalFormat = new DecimalFormat("0.##");
                String resultFormat = decimalFormat.format(userBalance);

                valueUserName.setText("Olá, " + user.getName());
                valueBalance.setText( resultFormat + " €");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void configCalendarRevenue() {
        CharSequence meses[] = { "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};
        calendarRevenue.setTitleMonths(meses);

        calendarRevenue.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
                Log.i("date","valor: " + (date.getMonth() + 1 )  + "/" + date.getYear() );
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        firebaseRef.removeEventListener(valueEventListenerUser);
        Log.i("onStop", "event was removed");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}