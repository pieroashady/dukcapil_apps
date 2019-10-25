package com.dika.dukcapil.Form;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.format.Formatter;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

import com.dika.dukcapil.ApiService.APIClient;
import com.dika.dukcapil.ApiService.APIInterfaceRest;
import com.dika.dukcapil.Models.VideoModels.StatusThumbnails;
import com.dika.dukcapil.Models.VideoScan.StatusVideo;
import com.dika.dukcapil.R;
import com.github.tcking.giraffecompressor.GiraffeCompressor;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

import static android.graphics.Bitmap.CompressFormat.JPEG;

public class VideoThumbnail extends AppCompatActivity {

    static final int REQUEST_VIDEO_CAPTURE = 1;
    VideoView videoView;
    Button btnGetVideo, btnGetThumbnail;
    String videoEncoded, sinsinSalto2;
    ProgressDialog loading;
    StatusThumbnails statusThumbnails;
    APIInterfaceRest apiInterfaceRest;
    Uri file;
    File videoFile, outputFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_thumbnail);
        videoView = findViewById(R.id.videoView);
        videoView.setVisibility(View.GONE);
        btnGetVideo = findViewById(R.id.btnGetVideo);
        btnGetVideo.setOnClickListener((c) -> {
            dispatchTakeVideoIntent();
        });
        btnGetThumbnail = findViewById(R.id.btnGetThumbnail);
        btnGetThumbnail.setOnClickListener((c)->{
            getThumbnail();
        });
    }

    private void dispatchTakeVideoIntent() {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            try {
                Uri videoUri = intent.getData();
                String[] projection = {MediaStore.Video.Media.DATA, MediaStore.Video.Media.SIZE, MediaStore.Video.Media.DURATION};
                Cursor cursor = managedQuery(videoUri, projection, null, null, null);
                videoView.setVisibility(View.VISIBLE);
                videoView.setVideoURI(videoUri);
                cursor.moveToFirst();
                String filePath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
                videoFile = new File(filePath);
                String output = "/hallo.mp4";
                outputFile = new File(output);
                Log.d("File Name:", filePath);
                compressVideo(videoFile);
                FileInputStream inputStream = null;
                try {
                    inputStream = new FileInputStream(outputFile);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                int bufferSize = 1024;
                byte[] buffer = new byte[bufferSize];
                ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
                int len = 0;
                try {
                    while ((len = inputStream.read(buffer)) != -1) {
                        byteBuffer.write(buffer, 0, len);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("converted!");

                String videoData = "";
                //Converting bytes into base64
                videoData = Base64.encodeToString(byteBuffer.toByteArray(), Base64.DEFAULT);
                Log.d("VideoData**>  ", videoData);

                String sinSaltoFinal2 = videoData.trim();
                sinsinSalto2 = "data:video/mp4;base64," +  sinSaltoFinal2.replaceAll("\n", "");
                Log.d("VideoData**>  ", sinsinSalto2);


            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }

    }

    private void showLoading() {
        loading = new ProgressDialog(VideoThumbnail.this);
        loading.setMessage("Start Comparing...");
        loading.show();
    }

    private void getThumbnail(){
        apiInterfaceRest = APIClient.getClientWithApi().create(APIInterfaceRest.class);

        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("default_lang", "true");
            requestBody.put("base64_mp4", sinsinSalto2);
        } catch (JSONException e){
            e.printStackTrace();
        }

        showLoading();
        Call<StatusThumbnails> call = apiInterfaceRest.getThumbnails(RequestBody.create(MediaType.parse("application/json"), requestBody.toString()));
        call.enqueue(new Callback<StatusThumbnails>() {
            @Override
            public void onResponse(Call<StatusThumbnails> call, Response<StatusThumbnails> response) {
                statusThumbnails = response.body();
                if(statusThumbnails != null) {
                    loading.dismiss();
                    Toast.makeText(VideoThumbnail.this, "SUCCESS", Toast.LENGTH_LONG).show();
                    statusThumbnails.getPayload().getThumbnails();
                } else {
                    loading.dismiss();
                    Toast.makeText(VideoThumbnail.this, "FAILED", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<StatusThumbnails> call, Throwable t) {
                loading.dismiss();
                t.printStackTrace();
                Toast.makeText(VideoThumbnail.this, "CONNECTION ERROR", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void compressVideo(File video){
        GiraffeCompressor.init(VideoThumbnail.this);
        GiraffeCompressor.create("mediacodec")
                .input(video)
                .output(outputFile)
                .ready()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GiraffeCompressor.Result>() {
                    @Override
                    public void onCompleted() {
                        Toast.makeText(VideoThumbnail.this, "Compress Success", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(VideoThumbnail.this, "Failed to Compress", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNext(GiraffeCompressor.Result s) {
                        String msg = String.format("compress completed \ntake time:%s \nout put file:%s", s.getCostTime(), s.getOutput());
                        msg = msg + "\ninput file size:"+ Formatter.formatFileSize(getApplication(),video.length());
                        msg = msg + "\nout file size:"+ Formatter.formatFileSize(getApplication(),new File(s.getOutput()).length());
                        System.out.println(msg);
                    }
                });
    }

}

