package info.steven.frontend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.gson.Gson;
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

        //getting data from Heroku
        Gson gson = new GsonBuilder().serializeNulls().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://cst438-project3.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderAPI jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderAPI.class);

        int current_id = getIntent().getIntExtra("CURRENT_ID", -1);
        Call<User> call = jsonPlaceHolderApi.getUserbyId(current_id);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                User user = response.body();

                String content = "";

                content = String.valueOf(user.getUsername());

                Toast.makeText(getApplicationContext(), "Hello "+ content + "!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Call Failure", Toast.LENGTH_SHORT).show();
            }
        });


    }

    public static Intent getIntent(Context context){
        Intent intent = new Intent(context, HomeActivity.class);
        return intent;
    }
}