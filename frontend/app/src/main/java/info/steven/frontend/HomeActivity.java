package info.steven.frontend;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
    List<Post> postList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button logoutButton = findViewById(R.id.logout_button);
        Button addPostButton = findViewById(R.id.btn_add);

//        createToast();

        populatePostList();

        Log.d(TAG, "onCreate: " + postList.toString());

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
    }

    private JsonPlaceHolderAPI getAPI() {
        //getting data from Heroku
        new GsonBuilder().serializeNulls().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://cst438-project3.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(JsonPlaceHolderAPI.class);
    }

    private void createToast() {
        JsonPlaceHolderAPI jsonPlaceHolderApi = getAPI();

        int current_id = getIntent().getIntExtra("CURRENT_ID", 1);
        Call<User> callUser = jsonPlaceHolderApi.getUserById(current_id);
        callUser.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {

                User user = response.body();

                String content;

                assert user != null;
                content = String.valueOf(user.getUsername());

                Toast.makeText(getApplicationContext(), "Hello "+ content + "!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(), "Call Failure", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void populatePostList() {
        JsonPlaceHolderAPI jsonPlaceHolderApi = getAPI();

        Call<List<Post>> callPosts = jsonPlaceHolderApi.getALlPosts();
        callPosts.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(@NonNull Call<List<Post>> call, @NonNull Response<List<Post>> response) {
                postList = response.body();
            }

            @Override
            public void onFailure(@NonNull Call<List<Post>> call, @NonNull Throwable t) {
                Toast.makeText(HomeActivity.this,"Call Failure", Toast.LENGTH_LONG).show();
            }
        });
    }
}