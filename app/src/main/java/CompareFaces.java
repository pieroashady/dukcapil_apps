//package com.juaracoding.dukcapil_test_api;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.os.Bundle;
//import android.preference.PreferenceManager;
//import android.provider.MediaStore;
//import android.util.Base64;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.Spinner;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.juaracoding.dukcapil_test_api.ApiService.ApiClientBearerToken;
//import com.juaracoding.dukcapil_test_api.ApiService.ApiInterfaceREST;
//import com.juaracoding.dukcapil_test_api.ApiService.AppUtil;
//import com.juaracoding.dukcapil_test_api.Model.Status;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.ByteArrayOutputStream;
//import java.util.ArrayList;
//import java.util.List;
//
//import okhttp3.MediaType;
//import okhttp3.RequestBody;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class CompareFaces extends AppCompatActivity {
//
//    private static final int CAMERA_REQUEST_CODE = 1;
//    Bitmap bitmap;
//    Bitmap decodedByteFace;
//    TextView bNik, txtStatus, txtSimilar;
//    ImageView imgBase, imgTarget, rectImg, cropImg;
//    Button takeTarget, btnCompare;
//    Spinner ambang;
//    Double border;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_compare__faces);
//        imgBase = findViewById(R.id.imgBase);
//        imgTarget = findViewById(R.id.imgTarget);
//        takeTarget = findViewById(R.id.takeTarget);
//        btnCompare = findViewById(R.id.btnCompare);
//        bNik = findViewById(R.id.bNik);
//        txtStatus = findViewById(R.id.txtStatus);
//        txtSimilar = findViewById(R.id.txtSimilar);
//        rectImg = findViewById(R.id.rectImg);
//        cropImg = findViewById(R.id.cropImg);
//        ambang = findViewById(R.id.ambang);
//        btnCompare.setVisibility(View.INVISIBLE);
//
//        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
//        String Face = prefs.getString("base", "base");
//        String nik = prefs.getString("nik", "nik");
//
//        bNik.setText(nik);
//
//        Face.replace("data:image/jpeg;base64,", "");
//        String Face2 = Face.split(",")[1];
//        byte[] decodedStringFace = Base64.decode(Face2, Base64.DEFAULT);
//        decodedByteFace = BitmapFactory.decodeByteArray(decodedStringFace, 0, decodedStringFace.length);
//        imgBase.setImageBitmap(decodedByteFace);
//
//
//        takeTarget.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                takePicture();
//            }
//        });
//
//        List<String> list = new ArrayList<>();
//        list.add("45.00");
//        list.add("50.00");
//        list.add("55.00");
//        list.add("60.00");
//        list.add("65.00");
//        list.add("70.00");
//        list.add("75.00");
//        list.add("80.00");
//        list.add("90.00");
//        list.add("95.00");
//        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, list);
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        ambang.setAdapter(dataAdapter);
//        ambang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                border = Double.valueOf(adapterView.getItemAtPosition(i).toString());
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
//
//        if(imgTarget != null){
//            btnCompare.setVisibility(View.VISIBLE);
//            btnCompare.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    getCompareData();
//                }
//            });
//        }
//    }
//
//    private void takePicture(){
//        //Create an Intent with action as ACTION_PICK
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        // Sets the type as image/*. This ensures only components of type image are selected
//        startActivityForResult(intent,CAMERA_REQUEST_CODE);
//    }
//
//    public void onActivityResult(int requestCode,int resultCode,Intent data){
//        // Result code is RESULT_OK only if the user selects an Image
//        if (resultCode == Activity.RESULT_OK && requestCode == CAMERA_REQUEST_CODE){
//            //data.getData returns the content URI for the selected Image
//            Bundle extras = data.getExtras();
//            bitmap = (Bitmap) extras.get("data");
//            imgTarget.setImageBitmap(bitmap);
//
//        }
//
//    }
//
//    private String encodeImage(Bitmap bm)
//    {
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bm.compress(Bitmap.CompressFormat.JPEG,80,baos);
//        byte[] b = baos.toByteArray();
//        String encImage = Base64.encodeToString(b, Base64.DEFAULT);
//        return encImage;
//    }
//
//    ApiInterfaceREST apiInterface;
//    Status status;
//    public void getCompareData(){
//        apiInterface = ApiClientBearerToken.getClientWithApi().create(ApiInterfaceREST.class);
//        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
//        String nik = prefs.getString("nik", "nik");
//        JSONObject rBody = new JSONObject();
//        try {
//            rBody.put("default_lang", AppUtil.DEFAULT_LANG);
//            rBody.put("nik", nik);
//            rBody.put("base64_source", "data:image/jpeg;base64," + encodeImage(decodedByteFace));
//            rBody.put("base64_target", "data:image/jpeg;base64," + encodeImage(bitmap));
//            rBody.put("threshold", border);
//
//        }catch (JSONException e){
//
//        }
//        String token = prefs.getString("Token", "Token");
//        Call<Status> statusCall = apiInterface.getCompare("Bearer " + token, RequestBody.create(MediaType.parse("application/json"), rBody.toString()));
//        statusCall.enqueue(new Callback<Status>() {
//            @Override
//            public void onResponse(Call<Status> call, Response<Status> response) {
//                if(response.body() != null) {
//                    status = response.body();
//
//                    String sml = status.getPayload().getSimilarity().replace(" %", "");
//                    Double Dsml = Double.valueOf(sml);
//                    if(Dsml > border) {
//
//                        txtStatus.setText("Status : MIRIP");
//                        txtSimilar.setText("Similarity : " + status.getPayload().getSimilarity());
//
//                        String Rect = status.getPayload().getRectangled();
//                        String Crop = status.getPayload().getCropped();
//
//                        Rect.replace("data:image/jpeg;base64,", "");
//                        Crop.replace("data:image/jpeg;base64,", "");
//                        String rect = Rect.split(",")[1];
//                        String crop = Crop.split(",")[1];
//
//                        byte[] decodedStringFace = Base64.decode(rect, Base64.DEFAULT);
//                        Bitmap decodedByteFace = BitmapFactory.decodeByteArray(decodedStringFace, 0, decodedStringFace.length);
//
//                        rectImg.setImageBitmap(decodedByteFace);
//
//                        byte[] decodedStringSign = Base64.decode(crop, Base64.DEFAULT);
//                        Bitmap decodedByteSign = BitmapFactory.decodeByteArray(decodedStringSign, 0, decodedStringSign.length);
//
//                        cropImg.setImageBitmap(decodedByteSign);
//                    }else {
//                        txtStatus.setText("Status : TIDAK TERIDENTIFIKASI!!!");
//                        txtSimilar.setText("Similarity : 0%");
//                        rectImg.setImageBitmap(null);
//                        cropImg.setImageBitmap(null);
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Status> call, Throwable t) {
//
//            }
//        });
//    }
//
//}