package com.dika.dukcapil.ApiService;

import com.dika.dukcapil.Models.StatusScan;
import com.dika.dukcapil.Models.Upload;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIInterfaceRest {

    @POST("get_mp4_thumbnailasync")
    Call<StatusScan> getAuth(@Body RequestBody body);

    @POST("scan_idasync")
    Call<StatusScan> getScanData(@Body RequestBody body);
}
