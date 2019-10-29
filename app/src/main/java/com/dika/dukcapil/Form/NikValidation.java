package com.dika.dukcapil.Form;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dika.dukcapil.Adapter;
import com.dika.dukcapil.ApiService.APIClient;
import com.dika.dukcapil.ApiService.APIInterfaceRest;
import com.dika.dukcapil.Models.Nik.Datum;
import com.dika.dukcapil.Models.Nik.StatusNik;
import com.dika.dukcapil.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NikValidation extends AppCompatActivity {

    TextInputEditText txtNama, txtKK, txtPob, txtDob, txtGender, txtReligion, txtMarital, editText,
    txtJob, txtMother, txtAddress, txtRT, txtRW, txtKodeKelurahan, txtNamaKelurahan, txtKodeKecamatan,
    txtNamaKecamatan, txtKodeKabupaten, txtNamaKabupaten, txtKodePropinsi, txtNamaPropinsi;
    Button btnValidate;
    SharedPreferences sharedPreferences;
    ProgressDialog loading;
    APIInterfaceRest apiInterfaceRest;
    StatusNik statusNik;
    String nik;
    View linearLayout;
    List<Datum> datum;
    String token;
    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nik_validation);

        rv = findViewById(R.id.rv);

        sharedPreferences = getSharedPreferences("API_KEY", Context.MODE_PRIVATE);
        token = sharedPreferences.getString("TOKEN", "");

        linearLayout = findViewById(R.id.linear);
        editText = findViewById(R.id.editNik);

        btnValidate = findViewById(R.id.btnValidate);
        btnValidate.setOnClickListener((v)->{
            validateNIK();
        });

        sharedPreferences = getSharedPreferences("Key", Context.MODE_PRIVATE);
        nik = sharedPreferences.getString("NIK", "");
        editText.setText(nik);
    }

    private void showLoading() {
        loading = new ProgressDialog(NikValidation.this);
        loading.setMessage("Getting Your NIK Data...");
        loading.show();
    }

    private void validateNIK() {
        apiInterfaceRest = APIClient.getClientWithApi().create(APIInterfaceRest.class);

        JSONObject result = new JSONObject();
        try {
            result.put("nik", nik);
        } catch (JSONException e){
            e.printStackTrace();
        }

        showLoading();
        Call<StatusNik> call = apiInterfaceRest.validateNik("Bearer " + token, RequestBody.create(MediaType.parse("application/json"), result.toString()));
        call.enqueue(new Callback<StatusNik>() {
            @Override
            public void onResponse(Call<StatusNik> call, Response<StatusNik> response) {
                loading.dismiss();
                statusNik = response.body();
                if (statusNik != null){
                    loading.dismiss();
                    datum = statusNik.getPayload().getData().getData();
                    for (int i = 0; i < datum.size(); i++){
                        String data = datum.get(i).getLabel();
                        editText.setText(data);
                    }
//                    snackbar("SUCCESS");
//                    setAdapterList(datum);
                } else {
                    loading.dismiss();
                    snackbar("Cannot Validate Your ID");
                }
            }


            @Override
            public void onFailure(Call<StatusNik> call, Throwable t) {
                loading.dismiss();
                t.printStackTrace();
                snackbar("Connection Error");
            }
        });
    }

    private void snackbar(String message){
        Snackbar snackbar = Snackbar.make(linearLayout, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    private void setAdapterList(List<Datum> model){
        Adapter adapter = new Adapter(NikValidation.this, model);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(NikValidation.this, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(linearLayoutManager);
        rv.setAdapter(adapter);
    }
}
