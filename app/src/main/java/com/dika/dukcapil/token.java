//package com.juaracoding.dukcapil_test_api;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//
//import android.Manifest;
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.content.pm.PackageManager;
//import android.os.Bundle;
//import android.preference.PreferenceManager;
//import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.juaracoding.dukcapil_test_api.ApiService.ApiClient;
//import com.juaracoding.dukcapil_test_api.ApiService.ApiInterfaceREST;
//import com.juaracoding.dukcapil_test_api.ApiService.AppUtil;
//import com.juaracoding.dukcapil_test_api.Model.Status;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.util.ArrayList;
//import java.util.List;
//
//import okhttp3.MediaType;
//import okhttp3.RequestBody;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class token extends AppCompatActivity {
//
//    TextView textview, getToken;
//    Button loginBtn;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        textview = findViewById(R.id.textView);
//        getToken = findViewById(R.id.Token);
//        loginBtn = findViewById(R.id.loginBtn);
//
//
//        checkAndRequestPermissions(Is_AuthAsync.this);
//
//        loginBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getToken();
//            }
//        });
//    }
//
//
//    public boolean checkAndRequestPermissions(Context context) {
//
//        List<String> listPermissionsNeeded = new ArrayList();
//        int writefilePermission = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE);
//        int cameraPermission = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA);
//        if (writefilePermission != PackageManager.PERMISSION_GRANTED) {
//            listPermissionsNeeded.add(Manifest.permission.READ_PHONE_STATE);
//        }
//        if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
//            listPermissionsNeeded.add(Manifest.permission.CAMERA);
//        }
//
//        if (!listPermissionsNeeded.isEmpty()) {
//            ActivityCompat.requestPermissions((Activity) context, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 6969);
//            return false;
//        }
//        return true;
//    }
//
//    ApiInterfaceREST apiInterface;
//    Status status;
//    private void getToken(){
//        apiInterface = ApiClient.getClientWithApi().create(ApiInterfaceREST.class);
//
//        JSONObject rBody = new JSONObject();
//        try {
//            rBody.put("default_lang", AppUtil.DEFAULT_LANG);
//            rBody.put("api_key", AppUtil.API_KEY);
//            rBody.put("api_secret", AppUtil.API_SECRET);
//        }catch (JSONException e){
//
//        }
//        Call<Status> statusCall = apiInterface.getToken(RequestBody.create(MediaType.parse("application/json"), rBody.toString()));
//        statusCall.enqueue(new Callback<Status>() {
//
//            @Override
//            public void onResponse(Call<Status> call, Response<Status> response) {
//                status = response.body();
//                if(status != null){
//
//                    getToken.setText(status.getPayload().getToken());
//                    Toast.makeText(Is_AuthAsync.this, "Token Get!!!", Toast.LENGTH_LONG).show();
//
//                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(Is_AuthAsync.this);
//                    prefs.edit().putString("Token", status.getPayload().getToken()).commit();
//
//                    Intent intent = new Intent(Is_AuthAsync.this, Scan_IDAsync.class);
//                    startActivity(intent);
//
//                } else {
//                    InputStream i = response.errorBody().byteStream();
//                    BufferedReader r = new BufferedReader(new InputStreamReader(i));
//                    StringBuilder errorResult = new StringBuilder();
//                    String line;
//                    try {
//                        while ((line = r.readLine()) != null) {
//                            errorResult.append(line).append('\n');
//                        }
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//
//                    Toast.makeText(Is_AuthAsync.this, "Login Failed " + errorResult, Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Status> call, Throwable t) {
//                Toast.makeText(getApplicationContext(), "There is connection problem!!!", Toast.LENGTH_LONG).show();
//                call.cancel();
//            }
//        });
//    }
//
//}