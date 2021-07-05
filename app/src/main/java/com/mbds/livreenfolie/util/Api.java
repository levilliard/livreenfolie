package com.mbds.livreenfolie.util;

import com.mbds.livreenfolie.model.Utilisateur;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {

    String BASE_URL = "http://locahost:8080/bank/api";
    @GET("users")
    Call<List<Utilisateur>> getUsers();
}