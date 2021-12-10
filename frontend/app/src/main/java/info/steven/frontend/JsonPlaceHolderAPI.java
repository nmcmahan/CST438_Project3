package info.steven.frontend;

import java.util.List;

//import io.reactivex.Flowable;
//import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JsonPlaceHolderAPI {

    @GET("users/{id}")
    Call<User> getUserById(
            @Path("id") int id
    );

    @GET("users/")
    Call<List<User>> getAllUsers();

    @GET("users/")
    Call<List<User>> getUserByName(
            @Query("username") String username
    );

    @POST("users/")
    Call<User> createUser(@Body User user);


    //items
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

    @PUT("items/{id}/")
    Call<Post> updateLike(
            @Path("id") int id,
            @Body Post updateLikes
    );

    @DELETE("users/{id}/")
    Call<User> deleteUserById(
            @Path("id") int id
    );
}
