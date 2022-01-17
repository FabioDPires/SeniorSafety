package com.example.seniorsafety.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seniorsafety.R;
import com.example.seniorsafety.database.Medication;
import com.example.seniorsafety.utility.Utility;

import java.util.ArrayList;

public class MedicationAdapter extends RecyclerView.Adapter<MedicationAdapter.ViewHolder> {

    private static final String TAG = "MedicationAdapter";
    private ArrayList<Medication> medicationList = new ArrayList<>();
    private OnMedicationListener mOnMedicationListener;

    public MedicationAdapter(ArrayList<Medication> medicationList, OnMedicationListener mOnMedicationListener) {

        this.medicationList = medicationList;
        this.mOnMedicationListener = mOnMedicationListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.medication_row, viewGroup, false);
        return new ViewHolder(view, mOnMedicationListener);//return new viewHolder object from that view
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            viewHolder.name.setText(medicationList.get(i).getName());
            String quantity=Integer.toString(medicationList.get(i).getQuantity());
            viewHolder.quantity.setText("Quantity: "+quantity);
            viewHolder.time.setText(medicationList.get(i).getTime());

    }

    @Override
    public int getItemCount() {
        return medicationList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView name, quantity,time;
        OnMedicationListener onMedicationListener;

        public ViewHolder(@NonNull View itemView, OnMedicationListener onMedicationListener) {
            super(itemView);
            name = itemView.findViewById(R.id.medicationName);
            quantity = itemView.findViewById(R.id.medicationQuantity);
            time=itemView.findViewById(R.id.medicationTime);
            this.onMedicationListener = onMedicationListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onMedicationListener.onMedicationClick(getAdapterPosition());
        }

    }

    public interface OnMedicationListener {
        void onMedicationClick(int position);
    }

}
