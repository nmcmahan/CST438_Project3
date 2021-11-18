package info.steven.frontend;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface JsonPlaceHolderAPI {

    @GET("users/{id}")
    Call<User> getUserById(
            @Path("id") int id
    );

    @GET("users/")
    Call<List<User>> getAllUsers();

    @POST("users/")
    Call<User> createUser(@Body User user);
}
