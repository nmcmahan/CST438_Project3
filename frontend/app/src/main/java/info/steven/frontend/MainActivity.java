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

    private static JsonPlaceHolderAPI jsonPlaceHolderApi;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //getting data from Heroku
        new GsonBuilder().serializeNulls().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://cst438-project3.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderAPI.class);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        Button login_button = findViewById(R.id.login);
        TextView signUpLink = findViewById(R.id.signUpLink);
        TextView deleteLink = findViewById(R.id.deleteTextView);

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
                        loadHomeActivity(userId, name);
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
        deleteLink.setOnClickListener(view -> goToDeletePage());
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

    public void goToDeletePage() {
        Intent intent = new Intent(this, DeleteAccount.class);
        startActivity(intent);
    }

    private void loadHomeActivity(int id, String username) {
        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        intent.putExtra("CURRENT_ID", id);
        intent.putExtra("CURRENT_USER", username);
        startActivity(intent);
    }
}
