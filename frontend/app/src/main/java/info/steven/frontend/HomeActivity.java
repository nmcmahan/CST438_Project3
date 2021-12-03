package info.steven.frontend;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Button logoutButton = findViewById(R.id.logout_button);
        int current_id = getIntent().getIntExtra("CURRENT_ID", -1);

        //getting data from Heroku
        new GsonBuilder().serializeNulls().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://cst438-project3.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderAPI jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderAPI.class);
        Call<User> call = jsonPlaceHolderApi.getUserById(current_id);

        call.enqueue(new Callback<User>() {
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

        logoutButton.setOnClickListener(view -> {
            Intent intent = MainActivity.getIntent(getApplicationContext());
            startActivity(intent);
            finish();
        });
    }

    public static Intent getIntent(Context context){
        return new Intent(context, HomeActivity.class);
    }
}