package com.adgvit.papervit2.Services;

import com.adgvit.papervit2.Object.HomeData;
import com.adgvit.papervit2.Object.ServerResponse;
import com.adgvit.papervit2.Object.root;
import com.adgvit.papervit2.Object.root1;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface API {

    @GET("/api/v1/examTypes/")
    Call<HomeData> getHome();

    @GET("/api/v1/subjects/{type}")
    Call<root> getSub(@Path("type") String type);

    @GET("/api/v1/papers/{id}")
    Call<root1> getPaperCat1(@Path("id") String _id);

    @GET("/api/v1/papers/{id}")
    Call<root1> getPaperCat2(@Path("id") String _id);

    @GET("/api/v1/papers/{id}")
    Call<root1> getPaperFat(@Path("id") String _id);

    @Multipart
    @POST("/api/v1/public/paper")
    Call<ServerResponse> upload(@Part MultipartBody.Part file);
    

}