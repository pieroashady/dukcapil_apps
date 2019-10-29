package com.dika.dukcapil.Form;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dika.dukcapil.ApiService.APIClient;
import com.dika.dukcapil.ApiService.APIInterfaceRest;
import com.dika.dukcapil.Models.Similar.StatusSimilar;
import com.dika.dukcapil.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.graphics.Bitmap.CompressFormat.JPEG;

public class SimilarForm extends AppCompatActivity {

    private static final int CAMERA_REQUEST_CODE = 1;
    ImageButton imgFace;
    ImageView imgKtp, imgRect, imgCropped;
    SharedPreferences sharedPreferences;
    String faceKtp, nik, selfieEncoded, rectangled64, cropped64, convertRect, convertCropped, ktp64;
    Button btnCompare;
    Bitmap bitmap;
    ProgressDialog loading;
    APIInterfaceRest apiInterfaceRest;
    StatusSimilar statusSimilar;
    TextView txtResult, txtSimilarity, txtNik;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_similar_form);

        sharedPreferences = getSharedPreferences("API_KEY", Context.MODE_PRIVATE);
        token = sharedPreferences.getString("TOKEN", "");
        // get shared preferencces context
        sharedPreferences = getSharedPreferences("Key", Context.MODE_PRIVATE);
        faceKtp = sharedPreferences.getString("Face", "");
        ktp64 = "data:image/jpeg;base64," + faceKtp;
        nik = sharedPreferences.getString("NIK", "");
        // get img from shared preferences
        imgKtp = findViewById(R.id.imgKtp);
        decodeBase64(faceKtp, imgKtp);

        imgRect = findViewById(R.id.imgRect);
        imgRect.setVisibility(View.GONE);

        imgCropped = findViewById(R.id.imgCropped);
        imgCropped.setVisibility(View.GONE);

        txtResult = findViewById(R.id.txtResult);
        txtResult.setVisibility(View.GONE);

        txtNik = findViewById(R.id.nik);
        txtNik.setVisibility(View.GONE);

        txtSimilarity = findViewById(R.id.txtSimilarity);
        txtSimilarity.setVisibility(View.GONE);

        imgFace = findViewById(R.id.imgSelfie);
        imgFace.setOnClickListener((x) -> {
            takePicture();
        });

        btnCompare = findViewById(R.id.btnValidate);
        btnCompare.setOnClickListener((c) -> {
            if (imgKtp == null) {
                Toast.makeText(SimilarForm.this, "Please Finish Your Scan KTP Section", Toast.LENGTH_LONG).show();
            } else {
                getComparedData();
            }
        });

    }

    private void showLoading() {
        loading = new ProgressDialog(SimilarForm.this);
        loading.setMessage("Start Comparing...");
        loading.show();
    }


    public void getComparedData() {
        apiInterfaceRest = APIClient.getClientWithApi().create(APIInterfaceRest.class);

        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("default_lang", true);
            requestBody.put("nik", nik);
            requestBody.put("base64_source", ktp64);
            requestBody.put("base64_target", selfieEncoded);
            requestBody.put("threshold", "45.00");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        showLoading();
        Call<StatusSimilar> statusSimilarCall = apiInterfaceRest.getCompare("Bearer " + token, RequestBody.create(MediaType.parse("application/json"), requestBody.toString()));
        statusSimilarCall.enqueue(new Callback<StatusSimilar>() {
            @Override
            public void onResponse(Call<StatusSimilar> call, Response<StatusSimilar> response) {
                statusSimilar = response.body();

                if (statusSimilar != null) {
                    loading.dismiss();
                    Toast.makeText(SimilarForm.this, "SUCCESS", Toast.LENGTH_LONG).show();
                    txtResult.setVisibility(View.VISIBLE);
                    // get NIK
                    txtNik.setVisibility(View.VISIBLE);
                    txtNik.setText(nik);

                    // get and show Similarity Percentage
                    txtSimilarity.setVisibility(View.VISIBLE);
                    String similarity = statusSimilar.getPayload().getSimilarity();
                    String replace = similarity.replace("%", "");
                    Double sim = Double.valueOf(replace);

                    if (sim > 45.00) {
                        txtSimilarity.setText(similarity);
                        // get rectangled image
                        imgRect.setVisibility(View.VISIBLE);
                        rectangled64 = statusSimilar.getPayload().getRectangled();
                        convertRect = rectangled64.split(",")[1];
                        decodeBase64(convertRect, imgRect);
                        // get cropped image
                        imgCropped.setVisibility(View.VISIBLE);
                        cropped64 = statusSimilar.getPayload().getCropped();
                        convertCropped = cropped64.split(",")[1];
                        decodeBase64(convertCropped, imgCropped);
                    } else {
                        txtSimilarity.setText("Tidak Mirip");
                        imgRect.setImageBitmap(null);
                        imgCropped.setImageBitmap(null);
                    }
                } else {
                    loading.dismiss();
                    Toast.makeText(SimilarForm.this, "Cannot Compare Your Face", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<StatusSimilar> call, Throwable t) {
                loading.dismiss();
                t.printStackTrace();
            }
        });
    }

    private void takePicture() {
        //Create an Intent with action as ACTION_PICK
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Sets the type as image/*. This ensures only components of type image are selected
        startActivityForResult(intent, CAMERA_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Result code is RESULT_OK only if the user selects an Image
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == CAMERA_REQUEST_CODE) {
            //data.getData returns the content URI for the selected Image
            Bundle extras = data.getExtras();
            bitmap = (Bitmap) extras.get("data");
            imgFace.setImageBitmap(bitmap);
            selfieEncoded = "data:image/jpeg;base64," + encodeToBase64(bitmap);
        }
    }

    public String encodeToBase64(Bitmap ktpBitmap) {
        Bitmap image = ktpBitmap;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(JPEG, 80, baos);
        byte b[] = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

        return imageEncoded;
    }

    public void decodeBase64(String resultToShow, ImageView image) {
        String viu = resultToShow.replace("data:image/jpeg;base64,", "");
        viu.split(",");
        byte[] d = Base64.decode(resultToShow, Base64.DEFAULT);
        Bitmap decoded = BitmapFactory.decodeByteArray(d, 0, d.length);
        image.setImageBitmap(decoded);
    }


}
