package info.steven.frontend;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

public class MainActivity extends AppCompatActivity {
    private EditText username;
    private EditText password;

    private TextView getUser;

    private static JsonPlaceHolderAPI jsonPlaceHolderApi;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getUser = findViewById(R.id.display_User);

        //getting data from Heroku
        new GsonBuilder().serializeNulls().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://cst438-project3.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderAPI.class);

        int id = 2;
        Call<User> call = jsonPlaceHolderApi.getUserById(id);


        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {

                User user = response.body();

                String content;

                assert user != null;
                content = String.valueOf(user.getUsername());

                getUser.setText(content);
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                getUser.setText("Call Failure");
            }
        });

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        Button login_button = findViewById(R.id.login);
        TextView signUpLink = findViewById(R.id.signUpLink);

        login_button.setOnClickListener(view -> {
            Call<List<User>> allUsersCall = jsonPlaceHolderApi.getAllUsers();

            allUsersCall.enqueue(new Callback<List<User>>() {
                @Override
                public void onResponse(@NonNull Call<List<User>> call, @NonNull Response<List<User>> response) {
                    List<User> all_users = response.body();
                    String name = username.getText().toString();
                    String pass = password.getText().toString();
                    boolean isValid = false, isUserFound = false;
                    int userId = -1;

                    if (name.isEmpty()) {
                        username.setError("This field cannot be blank");
                    }

                    if (pass.isEmpty()) {
                        password.setError("This field cannot be blank");
                    }

                    assert all_users != null;
                    for(User u:all_users){
                        if ((u.getUsername()).equals(name)) {
                            if (u.getPassword().equals(pass)){
                                isValid = true;
                                userId = u.getId();
                            }

                            isUserFound = true;
                        }
                    }

                    if (isValid) {
                        loadHomeActivity(userId);
                    } else {
                        alertDialog();

                        if (!isUserFound) {
                            username.setError("Your username is incorrect");
                        } else {
                            password.setError("Your password is incorrect");
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<User>> call, @NonNull Throwable t) {
                    Toast.makeText(MainActivity.this,"Call Failure", Toast.LENGTH_SHORT).show();
                }
            });
        });

        signUpLink.setOnClickListener(view -> goToSignUpPage());
    }

    private void alertDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage("Your username and/or password is incorrect.");
        dialog.setTitle("Invalid Credentials");
        dialog.setPositiveButton("OK", (dialog1, which) -> {});
        AlertDialog alertDialog=dialog.create();
        alertDialog.show();
    }

    public void goToSignUpPage() {
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }

    private void loadHomeActivity(int id) {
        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        intent.putExtra("CURRENT_ID", id);
        startActivity(intent);
    }
}
