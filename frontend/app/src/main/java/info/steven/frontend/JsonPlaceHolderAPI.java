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

    @GET("users/{username}")
    Call<User> getUserByName(
            @Path("username") String username
    );

    @POST("users/")
    Call<User> createUser(@Body User user);

    @GET("items/")
    Call<List<Post>> getALlPosts();

    @GET("items/{user_id}/{category}/{likes}")
    Call<List<Post>> searchForPosts(
            @Path("user_id") int user_id,
            @Path("category") String category,
            @Path("likes") int likes
    );
}
