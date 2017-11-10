package com.example.santiagoalbertokirk.cleanroom.data;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by santiagoalbertokirk on 28/10/17.
 */

public interface GetRoomInterface {
    @GET("api/room/{id}")
    Call<SingleRoomResponse> getRoom(@Path("id") int id);
}
