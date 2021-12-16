package com.example.covid19tracker.CovidVaccineLocation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.covid19tracker.CovidVaccineLocation.Adapter.VaccinationInfoAdapter;
import com.example.covid19tracker.CovidVaccineLocation.Model.VaccineModel;
import com.example.covid19tracker.CovidVaccineLocation.View.PickDate;
import com.example.covid19tracker.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class CovidVaccineLocation extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    String baseURL = "https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/findByPin?";
    private EditText areaPINCode;
    private Button forwardbtn;
    private ProgressBar holdOnProgress;
    private ArrayList<VaccineModel> vaccination_centres;
    private RecyclerView resultRecyclerView;
    private String areaPIN,avlDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid_vaccine_location);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        mapViews();
        onClickSetup();
    }

    private void onClickSetup() {
        forwardbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                areaPIN = areaPINCode.getText().toString();
                if(areaPIN.isEmpty()){
                    Toast.makeText(CovidVaccineLocation.this,"Please enter PIN CODE of your area",Toast.LENGTH_LONG).show();
                }else if(areaPIN.length()<6){
                    Toast.makeText(CovidVaccineLocation.this,"Please enter valid PIN CODE",Toast.LENGTH_LONG).show();
                }
                else {
                    holdOnProgress.setVisibility(View.VISIBLE);
                    forwardbtn.setVisibility(View.INVISIBLE);
                    DialogFragment dp = new PickDate();
                    dp.show(getSupportFragmentManager(), "pick a date");
                }
            }
        });
    }

    private void mapViews() {
        forwardbtn = findViewById(R.id.getResult);
        holdOnProgress = findViewById(R.id.progressBar1);
        areaPINCode = findViewById(R.id.enterPinCode);
        resultRecyclerView = findViewById(R.id.recyclerView);
        resultRecyclerView.setHasFixedSize(true);
        vaccination_centres = new ArrayList<VaccineModel>();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        Calendar k = Calendar.getInstance();
        k.set(Calendar.YEAR,year);
        k.set(Calendar.MONTH,month);
        k.set(Calendar.DAY_OF_MONTH,dayOfMonth);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        dateFormat.setTimeZone(k.getTimeZone());

        String d = dateFormat.format(k.getTime());
        setup(d);
    }

    private void setup(String d) {
         avlDate = d;
         fetchDataNow();
    }

    private void fetchDataNow() {
        vaccination_centres.clear();
        String url_api = baseURL + "pincode=" + areaPIN + "&date=" +avlDate;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url_api, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray sessionArray = jsonObject.getJSONArray("sessions");
                    for (int i = 0; i < sessionArray.length(); i++) {
                        JSONObject sesObject = sessionArray.getJSONObject(i);
                        VaccineModel vaccineModel = new VaccineModel();
                        vaccineModel.setVaccineCenter(sesObject.getString("name"));
                        vaccineModel.setVaccinationAddress(sesObject.getString("address"));
                        vaccineModel.setVaccinationTiming(sesObject.getString("from"));
                        vaccineModel.setVaccineName(sesObject.getString("vaccine"));
                        vaccineModel.setVaccinationAge(sesObject.getString("min_age_limit"));
                        vaccineModel.setVaccineAvailable(sesObject.getString("available_capacity"));
                        vaccination_centres.add(vaccineModel);
                    }
                    VaccinationInfoAdapter vaccinationInfoAdapter = new VaccinationInfoAdapter(getApplicationContext(), vaccination_centres);
                    resultRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    resultRecyclerView.setAdapter(null);
                    resultRecyclerView.setAdapter(vaccinationInfoAdapter);
                    holdOnProgress.setVisibility(View.INVISIBLE);
                    forwardbtn.setVisibility(View.VISIBLE);

                } catch (Exception e) {
                    holdOnProgress.setVisibility(View.INVISIBLE);
                    forwardbtn.setVisibility(View.VISIBLE);
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                holdOnProgress.setVisibility(View.INVISIBLE);
                forwardbtn.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(),"Error"+error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case  android.R.id.home:
                this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}