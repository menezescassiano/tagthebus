package services;

import models.StationResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by cassiano.menezes on 13/05/2017.
 */

public interface RetrofitModel {

    @GET("{latitude}/{longitude}/1.json")
    Call<StationResponse> getStationsInfo(@Path(value = "latitude", encoded = true) String latitude,
                                          @Path(value = "longitude", encoded = true) String longitude);

}

