package udemy.java.organizze.fragment;


import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.text.DecimalFormat;
import java.util.ArrayList;

import udemy.java.organizze.adapter.AdapterMovements;
import udemy.java.organizze.config.ConfigurationFirebase;
import udemy.java.organizze.databinding.FragmentMovementsBinding;
import udemy.java.organizze.helper.Base64Custom;
import udemy.java.organizze.model.Movements;
import udemy.java.organizze.model.User;

public class MovementsFragment extends Fragment {

    private FragmentMovementsBinding binding = null;

    private DatabaseReference firebaseRef = ConfigurationFirebase.getDatabaseReference();
    private FirebaseAuth userAuthentication = ConfigurationFirebase.getUserAuthentication();
    private DatabaseReference userRef;
    private DatabaseReference movementsRef;
    private ValueEventListener valueEventListenerUser, valueEventListenerMovements;

    private ArrayList<Movements> listMovements = new ArrayList<>();

    private MaterialCalendarView calendarView;

    private String monthYearSelected;
    private AdapterMovements adapterListMovements;
    private RecyclerView recyclerViewMovements;
    private Movements movements;

    private TextView valueUserName, valueBalance;

    private Double totalExpense = 0.0;
    private Double totalRevenue = 0.0;
    private Double userBalance  = 0.0;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentMovementsBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        valueUserName = binding.textViewRevenueUserName;
        valueBalance = binding.textViewRevenueBalance;

        calendarView = binding.calendarViewRevenueCalender;

        recyclerViewMovements = binding.recyclerViewMovementsBalance;
        configCalendarView();


    }

    public void swipe() {

        ItemTouchHelper.Callback itemTouch = new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {

                int dragFlags = ItemTouchHelper.ACTION_STATE_IDLE;
                int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;

                return makeMovementFlags(dragFlags, swipeFlags);

            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                deleteMovements( viewHolder );
                //Log.d("swipe","Item foi arrastado");
            }
        };

        new ItemTouchHelper( itemTouch).attachToRecyclerView(recyclerViewMovements);

    }

    private void deleteMovements(RecyclerView.ViewHolder viewHolder) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this.getContext() );

        alertDialog.setTitle("Excluir movimentações da conta");
        alertDialog.setMessage("Tem a certeza que deseja realmente excluir esta movimentação da conta? ");
        alertDialog.setCancelable(false);

        alertDialog.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int position  = viewHolder.getAdapterPosition();
                movements = listMovements.get(position);

                String userEmail = userAuthentication.getCurrentUser().getEmail();
                String idUser = Base64Custom.encryptionBase64(userEmail);

                movementsRef = firebaseRef.child("movements")
                        .child(idUser)
                        .child(monthYearSelected);

                movementsRef.child( movements.getKey() ).removeValue();
                adapterListMovements.notifyItemRemoved(position);
            }
        });
        alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(), "Cancelado", Toast.LENGTH_SHORT).show();
                adapterListMovements.notifyDataSetChanged();
            }
        });

        AlertDialog alert = alertDialog.create();
        alert.show();

    }


    public void getMovements() {

        //setting adapter
        adapterListMovements = new AdapterMovements(listMovements, getContext());

        //setting recyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext());
        recyclerViewMovements.setLayoutManager(layoutManager);
        recyclerViewMovements.setHasFixedSize(true);
        recyclerViewMovements.addItemDecoration( new DividerItemDecoration(requireContext(), LinearLayout.VERTICAL ));
        recyclerViewMovements.setAdapter(adapterListMovements);


        String userEmail = userAuthentication.getCurrentUser().getEmail();
        String idUser = Base64Custom.encryptionBase64(userEmail);

        movementsRef = firebaseRef.child("movements")
                .child(idUser)
                .child(monthYearSelected);

        listMovements.clear();
        adapterListMovements.notifyDataSetChanged();

        valueEventListenerMovements = movementsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot data: dataSnapshot.getChildren()){

                    Movements movements = data.getValue(Movements.class);
                    movements.setKey( data.getKey() );
                    listMovements.add(movements);

                }
                adapterListMovements.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void retrieveBalance() {
        String userEmail = userAuthentication.getCurrentUser().getEmail();
        String idUser = Base64Custom.encryptionBase64(userEmail);
        userRef = firebaseRef.child("users").child(idUser);

       // Log.i("onStop", "event was added");
       valueEventListenerUser = userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                User user = dataSnapshot.getValue(User.class);

                    totalExpense = user.getExpenseTotal();
                    totalRevenue = user.getRevenueTotal();

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

    private void configCalendarView() {
        CharSequence meses[] = { "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};
        calendarView.setTitleMonths(meses);

        CalendarDay actualDate = calendarView.getCurrentDate();
        String mothSelected = String.format("%02d", ( actualDate.getMonth() + 1 ));
        monthYearSelected = String.valueOf(mothSelected + "" + actualDate.getYear());

        calendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
                String mothSelected = String.format("%02d", ( date.getMonth() + 1 ));
                monthYearSelected =  String.valueOf(mothSelected + "" + date.getYear());
                movementsRef.removeEventListener(valueEventListenerMovements);
                getMovements();
               //Log.i("Mes","valor: " + monthYearSelected );
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        retrieveBalance();
    }

    @Override
    public void onResume() {
        super.onResume();
        getMovements();
        swipe();
    }

    @Override
    public void onStop() {
        super.onStop();
        userRef.removeEventListener(valueEventListenerUser);
        movementsRef.removeEventListener(valueEventListenerMovements);
        Log.i("onStop", "event was removed");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}