package info.steven.frontend;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUp extends AppCompatActivity {

    private EditText mUsernameField;
    private EditText mPasswordField;

    private static JsonPlaceHolderAPI jsonPlaceHolderApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mUsernameField = findViewById(R.id.usernameEditText);
        mPasswordField = findViewById(R.id.passwordEditText);

        Button submitBtn = findViewById(R.id.submitBtn);
        Button homeBtn = findViewById(R.id.homeBtn);

        new GsonBuilder().serializeNulls().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://cst438-project3.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderAPI.class);

        submitBtn.setOnClickListener(v -> {
            String username = mUsernameField.getText().toString();
            String password = mPasswordField.getText().toString();

            postUser(username, password);
            goToHomePage();
        });

        homeBtn.setOnClickListener(view -> goToHomePage());
    }

    public void goToHomePage(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void postUser(String username, String password)
    {
        User newuser = new User();

        newuser.setUsername(username);
        newuser.setPassword(password);
        Call<User> call = jsonPlaceHolderApi.createUser(newuser);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                return;
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }
}
