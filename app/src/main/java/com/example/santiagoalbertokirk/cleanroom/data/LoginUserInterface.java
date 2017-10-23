package com.example.santiagoalbertokirk.cleanroom.data;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by santiagoalbertokirk on 12/10/17.
 */

public interface LoginUserInterface {

    @GET("api/user/{username}/{password}")
    Call<LoginResponse> loginUser(@Path("username") String user, @Path("password") String password);
}
