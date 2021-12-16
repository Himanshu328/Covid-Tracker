package com.example.covid19tracker.CovidVaccineLocation.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covid19tracker.CovidVaccineLocation.Model.VaccineModel;
import com.example.covid19tracker.CovidVaccineLocation.Vaccine.VaccineResultActivity;
import com.example.covid19tracker.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class VaccinationInfoAdapter extends RecyclerView.Adapter<VaccinationInfoAdapter.ViewHolder> {

    Context context;
    List<VaccineModel> lis_vaccine_centre;

    public VaccinationInfoAdapter(Context context, List<VaccineModel> lis_vaccine_centre) {
        this.context = context;
        this.lis_vaccine_centre = lis_vaccine_centre;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
       View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.vaccination_info_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull VaccinationInfoAdapter.ViewHolder holder, int position) {
        holder.vaccinationCentre.setText(lis_vaccine_centre.get(position).getVaccineCenter());
        holder.vaccinationCentreAddress.setText(lis_vaccine_centre.get(position).getVaccinationAddress());
        holder.vaccinationTiming.setText(lis_vaccine_centre.get(position).getVaccinationTiming());
        holder.vaccineName.setText(lis_vaccine_centre.get(position).getVaccineName());
        holder.vaccinationAvailable.setText(lis_vaccine_centre.get(position).getVaccineAvailable());
        holder.vaccinationAge.setText(lis_vaccine_centre.get(position).getVaccinationAge());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),VaccineResultActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lis_vaccine_centre.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView vaccinationCentre;
        TextView vaccinationCentreAddress;
        TextView vaccinationTiming;
        TextView vaccinationAge;
        TextView vaccinationAvailable;
        TextView vaccineName;
        LinearLayout linearLayout ;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            vaccinationAge = itemView.findViewById(R.id.vaccination_Age);
            vaccinationCentreAddress = itemView.findViewById(R.id.vaccineLocation);
            vaccinationTiming = itemView.findViewById(R.id.vaccination_time);
            vaccinationAvailable = itemView.findViewById(R.id.isAvailable);
            vaccinationCentre = itemView.findViewById(R.id.vaccination_address);
            vaccineName = itemView.findViewById(R.id.vaccineName);
            linearLayout = itemView.findViewById(R.id.vaccineLayout);

        }
    }
}
