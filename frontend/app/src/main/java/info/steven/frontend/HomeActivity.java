package info.steven.frontend;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "PostList";
    private List<Post> postList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button logoutButton = findViewById(R.id.logout_button);
        Button addPostButton = findViewById(R.id.btn_add);
        Button viewPostsButton = findViewById(R.id.btn_viewPosts);

        logoutButton.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        addPostButton.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, AddEditPost.class);
            int current_id = getIntent().getIntExtra("CURRENT_ID", 1);
            String currentuser = getIntent().getStringExtra("CURRENT_USER");
            intent.putExtra("CURRENT_ID", current_id);
            intent.putExtra("CURRENT_USER", currentuser);
            startActivity(intent);
        });

        //takes to the list/search posts page
        viewPostsButton.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, ListItemActivity.class);
            startActivity(intent);
        });


        //getting data from Heroku
        new GsonBuilder().serializeNulls().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://cst438-project3.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderAPI jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderAPI.class);

        //get current user
        int current_id = getIntent().getIntExtra("CURRENT_ID", 1);
        Call<User> callUser = jsonPlaceHolderApi.getUserById(current_id);

        callUser.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                //get current user's posts
                Call<List<Post>> callPosts = jsonPlaceHolderApi.getPostsByUserId(Integer.toString(current_id));

                callPosts.enqueue(new Callback<List<Post>>() {
                    @Override
                    public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                        assert response.body() != null;
                        postList = new ArrayList<>(response.body());
                        RecyclerView recyclerView = findViewById(R.id.lv_postList);

        //        Setting up layout
                        recyclerView.hasFixedSize();
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(HomeActivity.this);
                        recyclerView.setLayoutManager(layoutManager);

        //        Setting up Adapter
                        RecyclerView.Adapter mAdapter = new RecycleViewAdapter(postList, HomeActivity.this);
                        recyclerView.setAdapter(mAdapter);
                    }

                    @Override
                    public void onFailure(Call<List<Post>> call, Throwable t) {
                        Toast.makeText(HomeActivity.this,"List not populating", Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(HomeActivity.this,"List not populating", Toast.LENGTH_LONG).show();
            }
        });

//        callUser.enqueue(new Callback<User>() {
//            @Override
//            public void onResponse(@NonNull Call<List<Post>> call, @NonNull Response<List<Post>> response) {
//                assert response.body() != null;
//                postList = new ArrayList<>(response.body());
//                RecyclerView recyclerView = findViewById(R.id.lv_postList);
//
////        Setting up layout
//                recyclerView.hasFixedSize();
//                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(HomeActivity.this);
//                recyclerView.setLayoutManager(layoutManager);
//
////        Setting up Adapter
//                RecyclerView.Adapter mAdapter = new RecycleViewAdapter(postList, HomeActivity.this);
//                recyclerView.setAdapter(mAdapter);
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<List<Post>> call, @NonNull Throwable t) {
//                Toast.makeText(HomeActivity.this,"List not populating", Toast.LENGTH_LONG).show();
//            }
//        });
    }
}