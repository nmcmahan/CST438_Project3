package info.steven.frontend;

import java.util.List;

//import io.reactivex.Flowable;
//import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

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


    @POST("items/")
    Call<Post> createPost(@Body Post post);

    @GET("items/")
    Call<List<Post>> getAllPosts();

    @GET("items/")
    Call<List<Post>> searchForPosts(
            @Query("username") String username,
            @Query("category") String category,
            @Query("likes") String likes
    );

    @GET("items/")
    Call<List<Post>> getPostsByUserId(
            @Query("user_id") String user_id
    );

    @GET("items/")
    Call<List<Post>> getPostsByUsername(
            @Query("username") String username
    );



}
