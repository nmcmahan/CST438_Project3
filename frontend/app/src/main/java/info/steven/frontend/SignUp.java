package info.steven.frontend;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.GsonBuilder;

import java.util.List;

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

            boolean emptyField = false;

            if (username.isEmpty())
            {
                mUsernameField.setError("This field cannot be blank");
                emptyField = true;
            }

            if (password.isEmpty()) {
                mPasswordField.setError("This field cannot be blank");
                emptyField = true;
            }
            if (!emptyField)
            {
                postUser(username, password);
            }
        });

        homeBtn.setOnClickListener(view -> goToHomePage());
    }

    public void goToHomePage(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void postUser(String username, String password)
    {
        User newUser = new User();

        newUser.setUsername(username);
        newUser.setPassword(password);

        final boolean[] usedUsername = {false};

        // get all the users so we don't make duplicate
        final Call[] call = new Call[]{jsonPlaceHolderApi.getAllUsers()};
        call[0].enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(@NonNull Call<List<User>> call, @NonNull Response<List<User>> response) {


                List<User> userList = response.body();
                assert userList != null;
                for (User user : userList) {
                    if (user.getUsername().equals(newUser.getUsername())) {
                        usedUsername[0] = true;
                        mUsernameField.setError("Username already in use");
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<List<User>> call, @NonNull Throwable t) {

            }
        });

        //if no duplicate name, post it
        if (!usedUsername[0])
        {
            Call<User> postCall = jsonPlaceHolderApi.createUser(newUser);
            postCall.enqueue(new Callback<User>() {
                @Override
                public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {

                }

                @Override
                public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {

                }
            });
        }
        //display error otherwise
        else
        {
            alertDialog();
        }
    }

    private void alertDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage("Your username and/or password is incorrect.");
        dialog.setTitle("Invalid Credentials");
        dialog.setPositiveButton("OK", (dialog1, which) -> {});
        AlertDialog alertDialog=dialog.create();
        alertDialog.show();
    }

}
