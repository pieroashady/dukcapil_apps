package com.dika.dukcapil.Form;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nik_validation);

        linearLayout = findViewById(R.id.linear);
        editText = findViewById(R.id.editNik);
        txtNama = findViewById(R.id.fullName);
        txtKK = findViewById(R.id.nomorKK);
        txtPob = findViewById(R.id.pob);
        txtDob = findViewById(R.id.dob);
        txtGender = findViewById(R.id.gender);
        txtReligion = findViewById(R.id.religion);
        txtMarital = findViewById(R.id.maritalStatus);
        txtJob = findViewById(R.id.job);
        txtMother = findViewById(R.id.namaIbu);
        txtAddress = findViewById(R.id.address);
        txtRT = findViewById(R.id.RT);
        txtRW = findViewById(R.id.RW);
        txtKodeKelurahan = findViewById(R.id.kodeKelurahan);
        txtNamaKelurahan = findViewById(R.id.namaKelurahan);
        txtKodeKecamatan = findViewById(R.id.kodeKecamatan);
        txtNamaKecamatan = findViewById(R.id.namaKecamatan);
        txtKodeKabupaten = findViewById(R.id.kodeKabupaten);
        txtNamaKabupaten = findViewById(R.id.namaKabupaten);
        txtKodePropinsi  = findViewById(R.id.kodePropinsi);
        txtNamaPropinsi = findViewById(R.id.namaPropinsi);

        btnValidate = findViewById(R.id.btnValidate);
        btnValidate.setOnClickListener((v)->{
            if (nik == null){
                snackbar("Please Finish Your KTP Section First");
            }
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
        Call<StatusNik> call = apiInterfaceRest.validateNik(RequestBody.create(MediaType.parse("application/json"), result.toString()));
        call.enqueue(new Callback<StatusNik>() {
            @Override
            public void onResponse(Call<StatusNik> call, Response<StatusNik> response) {
                loading.dismiss();
                statusNik = response.body();
                datum = response.body().getPayload().getDukcapil().getData();
                if (statusNik != null){
                    loading.dismiss();
                    List<String> list = new ArrayList<>();
                    for (int i = 0; i < datum.size(); i++){
                        String getLabel = datum.get(i).getLabel();
                        String getValue = datum.get(i).getValue();
                        txtNama.setText(getLabel + " : " + getValue);
                        txtNama.setVisibility(View.VISIBLE);
                    }
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
}
