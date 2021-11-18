package info.steven.frontend;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface JsonPlaceHolderAPI {

    @GET("users/{id}")
    Call<User> GetUserbyId(
            @Path("id") int id
    );

}
