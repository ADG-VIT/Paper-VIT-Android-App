package com.adg.papervit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface API {

    @GET("/api/v1/subjects/0")
    Call<root> getSubCat1();

    @GET("/api/v1/subjects/1")
    Call<root> getSubCat2();

    @GET("/api/v1/subjects/2")
    Call<root> getSubFat();

    @GET("/api/v1/papers/{id}")
    Call<root1> getPaperCat1(@Path("id") String _id);

    @GET("/api/v1/papers/{id}")
    Call<root1> getPaperCat2(@Path("id") String _id);

    @GET("/api/v1/papers/{id}")
    Call<root1> getPaperFat(@Path("id") String _id);

}