package kr.sofac.jangsisters.network.api;

import java.util.ArrayList;
import java.util.List;

import kr.sofac.jangsisters.network.api.type.ServerRequest;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

import static kr.sofac.jangsisters.config.ServerConfig.PART_CONTROLLER;

public interface ServiceRetrofit {

    @POST(PART_CONTROLLER)
    Call<ResponseBody> getData(@Body ServerRequest serverRequest);

    @Multipart
    @POST(PART_CONTROLLER)
    Call<ResponseBody> sendMultiPartRequest(@Part("json") RequestBody obj, @Part List<MultipartBody.Part> file);

    @Multipart
    @POST(PART_CONTROLLER)
    Call<ResponseBody> sendMultiPartWithTwoObj(@Part("json") RequestBody obj, @Part("deleteFiles") RequestBody listDeleteFiles, @Part ArrayList<MultipartBody.Part> file);

}