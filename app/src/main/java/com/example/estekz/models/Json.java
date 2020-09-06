package com.example.estekz.models;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface Json {
    @GET("products?consumer_key=ck_e9dea022d5c5b5a2196aa916ea074993edcdc68e&consumer_secret=cs_a07d9a7a84ee4aea51611277669e817a2d9da9c7&per_page=20&page=1")
    Call<List<AllProducts>> getPosts();
    @GET("products/{id}?consumer_key=ck_e9dea022d5c5b5a2196aa916ea074993edcdc68e&consumer_secret=cs_a07d9a7a84ee4aea51611277669e817a2d9da9c7")
    Call<AllProducts> getPost(@Path("id") long i);
//    int page=2;
    @GET("products?consumer_key=ck_e9dea022d5c5b5a2196aa916ea074993edcdc68e&consumer_secret=cs_a07d9a7a84ee4aea51611277669e817a2d9da9c7&per_page=20")
    Call<List<AllProducts>> getMore(@Query("page") int p);
}
