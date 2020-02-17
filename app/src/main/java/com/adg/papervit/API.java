package com.adg.papervit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface API {

    @GET("papers/cat1/subjects")
    Call<Sub> getSubCat1();

    @GET("papers/cat2/subjects")
    Call<Sub> getSubCat2();

    @GET("papers/fat/subjects")
    Call<Sub> getSubFat();

    @GET("papers/cat1/{id}")
    Call<Papers> getPaperCat1(@Path("id") String _id);

    @GET("papers/cat2/{id}")
    Call<Papers> getPaperCat2(@Path("id") String _id);

    @GET("papers/cat1/{id}")
    Call<Papers> getPaperFat(@Path("id") String _id);

    @Streaming
    @GET()
    Call<String> getPaper(@Url String id);

}