package udemy.java.organizze.adapter;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import udemy.java.organizze.R;
import udemy.java.organizze.model.Movements;


public class AdapterMovements extends RecyclerView.Adapter<AdapterMovements.MyViewHolder> {

    ArrayList<Movements> movementsList;
    Context context;

    public AdapterMovements(ArrayList<Movements> movementsList, Context context) {
        this.movementsList = movementsList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemList = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_list_movements_adapter, parent, false);
        return new MyViewHolder(itemList);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Movements movements = movementsList.get(position);

        holder.movementsDescription.setText(movements.getDescription());
        holder.movementsCategory.setText(movements.getCategory());
        holder.movementsTransference.setText(String.valueOf(movements.getTransference()) );
        holder.movementsTransference.setTextColor(context.getResources().getColor(R.color.green));

         if (("expense").equals(movements.getType())) {
            holder.movementsTransference.setTextColor(context.getResources().getColor(R.color.red));
            holder.movementsTransference.setText(String.valueOf("-" + movements.getTransference()));
        }
    }

    @Override
    public int getItemCount() {
        return movementsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView movementsCategory, movementsDescription, movementsTransference;

        public MyViewHolder (View itemView) {
            super(itemView);

            this.movementsCategory = itemView.findViewById(R.id.textView_movementsCategory);
            this.movementsDescription = itemView.findViewById(R.id.textView_movementsDescription);
            this.movementsTransference = itemView.findViewById(R.id.textView_movementsTransference);


        }
    }
}
