package com.example.covid19tracker.CovidCases;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.covid19tracker.R;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class CovidCasesFragment extends Fragment {

    TextView totalCases,todayCases,recoveredCases,todayRecovered,activeCases,totalDeaths,todayDeath,lastUpdate;
    PieChart pieChart;
    private List<CountryData> list;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_covid_cases, container, false);

        totalCases = view.findViewById(R.id.totalCases);
        todayCases = view.findViewById(R.id.todayCases);
        recoveredCases = view.findViewById(R.id.recovered);
        todayRecovered = view.findViewById(R.id.todayRecovered);
        activeCases = view.findViewById(R.id.active);
        totalDeaths = view.findViewById(R.id.deaths);
        todayDeath = view.findViewById(R.id.todayDeaths);
        lastUpdate = view.findViewById(R.id.lastUpdate);

        pieChart = view.findViewById(R.id.pieChart);

        list = new ArrayList<>();

        ApiUtilities.getApiInterface().getCountryData().enqueue(new Callback<List<CountryData>>() {
            @Override
            public void onResponse(Call<List<CountryData>> call, retrofit2.Response<List<CountryData>> response) {
                list.addAll(response.body());

                for(int i = 0; i < list.size();i++){
                    if(list.get(i).getCountry().equals("India")){
                        int confirm = Integer.parseInt(list.get(i).getCases());
                        int active = Integer.parseInt(list.get(i).getActive());
                        int recovered = Integer.parseInt(list.get(i).getRecovered());
                        int death = Integer.parseInt(list.get(i).getDeaths());


                        activeCases.setText(NumberFormat.getInstance().format(active));
                        totalCases.setText(NumberFormat.getInstance().format(confirm));
                        recoveredCases.setText(NumberFormat.getInstance().format(recovered));
                        totalDeaths.setText(NumberFormat.getInstance().format(death));

                        todayDeath.setText("+ "+NumberFormat.getInstance().format(Integer.parseInt(list.get(i).getTodayDeaths())));
                        todayRecovered.setText("+ "+NumberFormat.getInstance().format(Integer.parseInt(list.get(i).getTodayRecovered())));
                        todayCases.setText("+ "+NumberFormat.getInstance().format(Integer.parseInt(list.get(i).getTodayCases())));

                        setText(list.get(i).getUpdated());

                        pieChart.addPieSlice(
                                new PieModel(
                                        "Confirm",
                                        confirm,
                                        Color.parseColor("#29B6F6")));
                        pieChart.addPieSlice(
                                new PieModel(
                                        "Recovered",
                                        recovered,
                                        Color.parseColor("#66BB6A")));
                        pieChart.addPieSlice(
                                new PieModel(
                                        "Total Death",
                                        death,
                                        Color.parseColor("#EF5350")));
                        pieChart.addPieSlice(
                                new PieModel(
                                        "Active",
                                        active,
                                        Color.parseColor("#FFA726")));

                        // To animate the pie chart
                        pieChart.startAnimation();

                    }
                }
            }

            @Override
            public void onFailure(Call<List<CountryData>> call, Throwable t) {
                Toast.makeText(view.getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    private void setText(String updated){
        DateFormat format = new SimpleDateFormat("MMM,dd,yyyy");

        long milliseconds = Long.parseLong(updated);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);
        lastUpdate.setText("updated at "+format.format(calendar.getTime()));
    }
}