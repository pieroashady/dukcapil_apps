package com.dika.dukcapil.Form;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.dika.dukcapil.ApiService.APIClient;
import com.dika.dukcapil.ApiService.APIInterfaceRest;
import com.dika.dukcapil.ApiService.AppUtil;
import com.dika.dukcapil.Models.Token.Status;
import com.dika.dukcapil.R;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TokenForm extends AppCompatActivity {

    View linearLayout;
    Button btnToken;
    public static String API_TOKEN;
    ProgressDialog loading;
    APIInterfaceRest apiInterfaceRest;
    Status status;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_token_form);

        btnToken = findViewById(R.id.btnToken);
        linearLayout = findViewById(R.id.linear);

        btnToken.setOnClickListener((x)->{
            getToken();
        });
    }

    private void showLoading() {
        loading = new ProgressDialog(TokenForm.this);
        loading.setMessage("Get token...");
        loading.show();
    }

    private void getToken(){
        apiInterfaceRest = APIClient.getClientWithApi().create(APIInterfaceRest.class);

        JSONObject token = new JSONObject();
        try {
            token.put("default_lang", "true");
            token.put("api_key", AppUtil.API_KEY);
            token.put("api_secret", AppUtil.API_SECRET);
        } catch (JSONException e){
            e.printStackTrace();
        }

        showLoading();
        sharedPreferences = getSharedPreferences("API_KEY", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor  = sharedPreferences.edit();
        Call<Status> call = apiInterfaceRest.getToken(RequestBody.create(MediaType.parse("application/json"), token.toString()));
        call.enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {
                status = response.body();
                if(status.getStatus().equals(1)){
                    loading.dismiss();
                    Snackbar snackbar = Snackbar.make(linearLayout, "Successfully Get Token", Snackbar.LENGTH_LONG);
                    snackbar.show();
                    API_TOKEN = status.getPayload().getToken();
                    editor.putString("TOKEN", API_TOKEN);
                    editor.apply();
                } else {
                    loading.dismiss();
                    Snackbar snackbar = Snackbar.make(linearLayout, "Failed Get Token", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {
                loading.dismiss();
                t.printStackTrace();
            }
        });
    }
}
