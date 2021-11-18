package info.steven.frontend;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface JsonPlaceHolderAPI {

    @GET("users/{id}")
    Call<User> getUserbyId(
            @Path("id") int id
    );

    @GET("users")
    List<User> getAllUsers();
}
