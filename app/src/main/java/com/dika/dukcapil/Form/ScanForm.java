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
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
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

    Button btnScan, btnPhoto, btnGallery, btnRotate;
    EditText editConvert;
    String basePhoto64, photoBase64, face, decode;
    Bitmap ktpBitmap;
    ImageButton imgKtp;
    String getBase, token;
    TextInputEditText editText;
    String imageResult = "";
    TextView txtNik, txtIsCard, txtIsSignature, txtBase64Photo, txtBase64Signature, txtResult;
    ImageView imgFace, imgSignature, imgBorder;
    SharedPreferences sharedPreferences;
    Intent intent;
    TextView txtHello;
    ImageView imgView;
    Boolean yeah = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_form);

        imgView = findViewById(R.id.imgView);
        imgBorder = findViewById(R.id.border);

        btnRotate = findViewById(R.id.btnRotate);
        btnRotate.setVisibility(View.GONE);
//        btnRotate.setOnClickListener((v)->{
//            rotateImage(imgView);
//        });

        btnGallery = findViewById(R.id.btnGallery);
        btnGallery.setOnClickListener((b) -> {
            galleryIntent();
        });
        txtHello = findViewById(R.id.editHello);
        intent = getIntent();
        btnPhoto = findViewById(R.id.btnPhoto);
        btnPhoto.setOnClickListener((c) -> {
//            CropImage.activity().start(ScanForm.this);
            goToCustomCamera();

        });

        sharedPreferences = getSharedPreferences("API_KEY", Context.MODE_PRIVATE);
        token = sharedPreferences.getString("TOKEN", "");

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

    private void goToCustomCamera() {
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
            if (requestCode == ID_R && resultCode == CustomCamera.RESULT_CODE) {
//                sharedPreferences = getSharedPreferences("Key", Context.MODE_PRIVATE);
//                String image = sharedPreferences.getString("image", "");
                String image = data.getStringExtra("image");

                ExifInterface exif = null;
                try {
                    exif = new ExifInterface(image);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                        ExifInterface.ORIENTATION_UNDEFINED);

                imgView.setImageURI(Uri.parse(image));

                btnRotate.setVisibility(View.VISIBLE);
                btnRotate.setOnClickListener((f) -> {
                    rotateImage(imgView);
                });


                Matrix matrix = new Matrix();
                matrix.postRotate(270);
                Bitmap border = ((BitmapDrawable)imgView.getDrawable()).getBitmap();
                Bitmap scaledBitmap = Bitmap.createBitmap(border, 800, imgView.getHeight() - 100, 1500, 2000, matrix, true);
//
                imgView.setImageBitmap(scaledBitmap);

//                imgView.setRotation((float) 270.0);
                txtHello.setText(image);
                imageResult = encodeToBase64(scaledBitmap);


//                Log.d("DEBUG", "Bitmap width = " + border.getWidth());
//                Log.d("DEBUG", "Bitmap height = " + border.getHeight());

                int width = imgView.getWidth();
                int height = imgView.getHeight();
                int maxW = imgView.getMaxWidth();
                int maxH = imgView.getMaxHeight();
                Log.d("DEBUG", "width = " + width + " height = " + height);
                Log.d("DEBUG", "maxw = " + maxW + " maxH = " + maxH);


//                Bitmap images = Bitmap.createScaledBitmap(scaledBitmap,  scaledBitmap.getWidth(), scaledBitmap.getHeight(), true);

//                imgView.setImageURI(Uri.parse(image));
////                Toast.makeText(ScanForm.this, image, Toast.LENGTH_LONG).show();
            }
        }
    }
//


    private void galleyPicker(Intent data) {
        try {
            Uri filePath = data.getData();
            InputStream imageStream = null;
            imageStream = this.getContentResolver().openInputStream(filePath);
            Bitmap selected = BitmapFactory.decodeStream(imageStream);
            int bitWidth = selected.getWidth();
            int bitHeight = selected.getHeight();
//
//            // 3. Size of camera preview on screen
//            int preWidth = preview.getWidth();
//            int preHeight = preview.getHeight();
            Bitmap bitmap = Bitmap.createBitmap(selected);
            Bitmap resized = Bitmap.createScaledBitmap(selected, 600, 700, false);
            imgView.setImageBitmap(bitmap);
            String x = encodeToBase64(selected);
            imageResult = encodeToBase64(selected);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void rotateImage(ImageView view) {

        Bitmap border = ((BitmapDrawable) imgView.getDrawable()).getBitmap();

        int width = border.getWidth();
        int height = border.getHeight();
        int newWidth = 200;
        int newHeight = 200;

        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        Matrix matrix = new Matrix();
//        matrix.postScale(scaleWidth, scaleHeight);
        matrix.postRotate(45);

        Bitmap scaledBitmap = Bitmap.createBitmap(border, 800, 600, border.getWidth(), border.getHeight(), matrix, true);
//            view.setRotation((float) 270);
        view.setImageBitmap(scaledBitmap);
        imageResult = encodeToBase64(scaledBitmap);
    }

    public String encodeToBase64(Bitmap ktpBitmap) {
        Bitmap image = ktpBitmap;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(JPEG, 80, baos);
        byte b[] = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

        return "data:image/jpeg;base64," + imageEncoded;
    }

    public static Bitmap rotateBitmap(Bitmap bitmap, int orientation) {

        Matrix matrix = new Matrix();
        switch (orientation) {
            case ExifInterface.ORIENTATION_NORMAL:
                return bitmap;
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                matrix.setScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);
                break;
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                matrix.setRotate(180);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_TRANSPOSE:
                matrix.setRotate(90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_TRANSVERSE:
                matrix.setRotate(-90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.setRotate(-90);
                break;
            default:
                return bitmap;
        }
        try {
            Bitmap bmRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            bitmap.recycle();
            return bmRotated;
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        }
    }

    public void decodeBase64(String resultToShow, ImageView image) {
        String viu = resultToShow.replace("data:image/jpeg;base64,", "");
        viu.split(",");
        byte[] d = Base64.decode(resultToShow, Base64.DEFAULT);
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
        Call<StatusScan> call = apiInterface.getScanData("Bearer " + token, RequestBody.create(MediaType.parse("application/json"), result.toString()));
        call.enqueue(new Callback<StatusScan>() {
            @Override
            public void onResponse(Call<StatusScan> call, Response<StatusScan> response) {
                statusScan = response.body();
                if (statusScan != null) {
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
