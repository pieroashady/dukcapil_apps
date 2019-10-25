package com.dika.dukcapil.Form;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dika.dukcapil.ApiService.APIClient;
import com.dika.dukcapil.ApiService.APIInterfaceRest;
import com.dika.dukcapil.Camera.CustomCamera;
import com.dika.dukcapil.Models.StatusScan;
import com.dika.dukcapil.R;
import com.google.android.material.textfield.TextInputEditText;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.graphics.Bitmap.CompressFormat.JPEG;

public class ScanForm extends AppCompatActivity {

    public static int PICK_IMAGE = 101;
    public static int ID_R = 102;
    public static int FOLDER1_REQUEST = 104;
    APIInterfaceRest apiInterface;

    Button btnScan, btnPhoto, btnGallery;
    EditText editConvert;
    String basePhoto64, photoBase64, face, decode;
    Bitmap ktpBitmap;
    ImageButton imgKtp;
    String getBase;
    TextInputEditText editText;
    String imageResult = "";
    TextView txtNik, txtIsCard, txtIsSignature, txtBase64Photo, txtBase64Signature, txtResult;
    ImageView imgFace, imgSignature;
    SharedPreferences sharedPreferences;
    Intent intent;
    TextView txtHello;
    ImageView imgView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_form);

        imgView = findViewById(R.id.imgView);

        btnGallery = findViewById(R.id.btnGallery);
        btnGallery.setOnClickListener((b)->{
            galleryIntent();
        });
        txtHello = findViewById(R.id.editHello);
        intent = getIntent();
        btnPhoto = findViewById(R.id.btnPhoto);
        btnPhoto.setOnClickListener((c)->{
//            CropImage.activity().start(ScanForm.this);
            goToCustomCamera();

        });

        txtResult = findViewById(R.id.txtResult);
        txtResult.setVisibility(View.GONE);

        btnScan = findViewById(R.id.btnValidate);
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanKtp();
                txtResult.setVisibility(View.VISIBLE);
            }
        });

//        imgKtp = findViewById(R.id.img1);
//        imgKtp.setOnClickListener((x)->{
//            galleryIntent();
//        });

        txtNik = findViewById(R.id.nik);
        txtIsSignature = findViewById(R.id.isIdCard);

        imgFace = findViewById(R.id.imgFace);
        imgFace.setVisibility(View.GONE);

        imgSignature = findViewById(R.id.imgSignature);
        imgSignature.setVisibility(View.GONE);

        imgFace = findViewById(R.id.imgFace);
        imgSignature = findViewById(R.id.imgSignature);



//        initToolbar();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Scan KTP");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void goToCustomCamera(){
        startActivityForResult(new Intent(ScanForm.this, CustomCamera.class), ID_R);
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            galleyPicker(data);
        } else {
            if (requestCode == ID_R && resultCode == CustomCamera.RESULT_CODE){
//                sharedPreferences = getSharedPreferences("Key", Context.MODE_PRIVATE);
//                String image = sharedPreferences.getString("image", "");
                data = getIntent();
                String image = data.getExtras().getString("image");
                txtHello.setText(image);
                imgView.setImageURI(Uri.parse(image));
//                Toast.makeText(ScanForm.this, image, Toast.LENGTH_LONG).show();
            }
        }
    }
//        } else if (requestCode = ID_R && resultCode = CustomCamera.RESULT_CODE){
//
//        }
//        else {
//           if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
//                CropImage.ActivityResult result = CropImage.getActivityResult(data);
//                if (resultCode == RESULT_OK) {
//                    Uri resultUri = result.getUri();
//                    imgKtp.setImageURI(resultUri);
//                    InputStream stream = null;
//                    try {
//                        stream = this.getContentResolver().openInputStream(resultUri);
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    }
//                    Bitmap selected = BitmapFactory.decodeStream(stream);
//                    imageResult = "data:image/jpeg;base64," + encodeToBase64(selected);
//                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
//                    Exception error = result.getError();
//                    error.printStackTrace();
//                }
//            }
//        }


    private void galleyPicker(Intent data) {
        try {
            Uri filePath = data.getData();
            InputStream imageStream = null;
            imageStream = this.getContentResolver().openInputStream(filePath);
            Bitmap selected = BitmapFactory.decodeStream(imageStream);
            Bitmap bitmap = Bitmap.createBitmap(selected, 50, 100, 100, 100);
            Bitmap resized = Bitmap.createScaledBitmap(selected, 600, 700, false);
            imgView.setImageBitmap(bitmap);
            String x = "data:image/jpeg;base64," + encodeToBase64(selected);
            imageResult = "data:image/jpeg;base64," + encodeToBase64(selected);
        } catch (IOException e) {
            e.printStackTrace();
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

    public void decodeBase64(String resultToShow, ImageView image){
        String viu = resultToShow.replace("data:image/jpeg;base64,", "");
        viu.split(",");
        byte[] d =  Base64.decode(resultToShow, Base64.DEFAULT);
        Bitmap decoded = BitmapFactory.decodeByteArray(d, 0, d.length);
        image.setImageBitmap(decoded);
    }

    StatusScan status;
    File fileImgKtp;
    ProgressDialog loading;

    private void showLoading() {
        loading = new ProgressDialog(ScanForm.this);
        loading.setMessage("Working on it...");
        loading.show();
    }

    StatusScan statusScan;

    private void scanKtp() {
        apiInterface = APIClient.getClientWithApi().create(APIInterfaceRest.class);

        JSONObject result = new JSONObject();
        try {
            result.put("default_lang", "true");
            result.put("base64_id", imageResult);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        showLoading();
        sharedPreferences = getSharedPreferences("Key", Context.MODE_PRIVATE);
        Call<StatusScan> call = apiInterface.getScanData(RequestBody.create(MediaType.parse("application/json"), result.toString()));
        call.enqueue(new Callback<StatusScan>() {
            @Override
            public void onResponse(Call<StatusScan> call, Response<StatusScan> response) {
                statusScan = response.body();
                if (statusScan != null) {
                    loading.dismiss();
                    if(statusScan.getPayload().getData().getIsIdCard().equals(false)){
                        txtNik.setText("Can't Detect an ID Card");
                    }
                    if(statusScan.getPayload().getData().getIsPhotoInside().equals(false)){
                        txtIsSignature.setText("Cannot Detect Your ID Photo");
                    }
                    else {
                        loading.dismiss();
                        Toast.makeText(ScanForm.this, "SUCCESS", Toast.LENGTH_LONG).show();
                        // get NIK
                        String nik = statusScan.getPayload().getData().getNik();
                        txtNik.setVisibility(View.VISIBLE);
                        txtNik.setText(nik);
                        // save NIK to memory
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("NIK", nik);
                        //  get Face Photo
                        imgFace.setVisibility(View.VISIBLE);
                        photoBase64 = statusScan.getPayload().getData().getBase64Photo();
                        face = photoBase64.split(",")[1];
                        decodeBase64(face, imgFace);
                        // save base64 image result
                        editor.putString("Face", face);
                        editor.apply();
                        // get Signature Photo
                        imgSignature.setVisibility(View.VISIBLE);
                        String signatureBase64 = statusScan.getPayload().getData().getBase64Signature();
                        String signature = signatureBase64.split(",")[1];
                        decodeBase64(signature, imgSignature);
                    }
                } else {
                    loading.dismiss();
                    Toast.makeText(ScanForm.this, "PLEASE SELECT YOUR KTP SCAN IMAGE", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<StatusScan> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(ScanForm.this, "CONNECTION ERROR", Toast.LENGTH_LONG).show();
                t.printStackTrace();
            }
        });
    }


    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }
}
