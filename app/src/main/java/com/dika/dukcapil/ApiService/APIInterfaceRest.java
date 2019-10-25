package com.dika.dukcapil.ApiService;

import com.dika.dukcapil.Models.Nik.StatusNik;
import com.dika.dukcapil.Models.Similar.StatusSimilar;
import com.dika.dukcapil.Models.StatusScan;
import com.dika.dukcapil.Models.Token.Status;
import com.dika.dukcapil.Models.Upload;
import com.dika.dukcapil.Models.VideoModels.StatusThumbnails;

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

    @POST("is_authasync")
    Call<Status> getToken(@Body RequestBody body);

    @POST("scan_idasync")
    Call<StatusScan> getScanData(@Body RequestBody body);

    @POST("detect_facesasync")
    Call<StatusSimilar> getDetectFace(@Body RequestBody body);

    @POST("compare_facesasync")
    Call<StatusSimilar> getCompare(@Body RequestBody body);

    @POST("get_mp4_thumbnailsasync")
    Call<StatusThumbnails> getThumbnails(@Body RequestBody body);

    @POST("get_dukcapil_infoasync")
    Call<StatusNik> validateNik(@Body RequestBody body);
}
