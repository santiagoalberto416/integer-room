package com.example.santiagoalbertokirk.cleanroom.data;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by santiagoalbertokirk on 28/10/17.
 */

public interface GetRoomsInterface {
    @GET("api/rooms")
    Call<Room[]> getRooms();
}
