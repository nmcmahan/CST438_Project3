package info.steven.frontend;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.GsonBuilder;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DeleteAccount extends AppCompatActivity {
    private Button cancelButton;
    private Button deleteButton;
    private EditText username;
    private EditText password;
    private EditText confirmPassword;

    private static JsonPlaceHolderAPI jsonPlaceHolderApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_user);

        new GsonBuilder().serializeNulls().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://cst438-project3.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderAPI.class);

        username = findViewById(R.id.confirmUserField);
        password = findViewById(R.id.confirmPassword1);
        confirmPassword = findViewById(R.id.confirmPassword2);
        cancelButton = findViewById(R.id.cancelDelBtn);
        deleteButton = findViewById(R.id.DeleteButton);

        cancelButton.setOnClickListener(view -> goToHomePage());
        deleteButton.setOnClickListener(view -> {
            String name = username.getText().toString();
            String pass1 = password.getText().toString();
            String pass2 = confirmPassword.getText().toString();

            if (name.isEmpty()) {
                username.setError("This field cannot be blank");
                return;
            }

            if (pass1.isEmpty() || pass2.isEmpty()) {
                password.setError("Enter your password in both fields");
                return;
            } else if (!pass1.equals(pass2)) {
                password.setError("Entered Passwords must match");
                return;
            }

            findUser(name, pass1);
        });

    }

    public void goToHomePage(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void findUser(String name, String pass) {
        Call<List<User>> foundUser = jsonPlaceHolderApi.getUserByName(name);
        foundUser.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(@NonNull Call<List<User>> call, @NonNull Response<List<User>> response) {
                List<User> userfound = response.body();
                if(userfound.isEmpty()) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(DeleteAccount.this);
                    dialog.setMessage("That username couldn't be found.");
                    dialog.setTitle("Invalid");
                    dialog.setPositiveButton("OK", (dialog1, which) -> {});
                    AlertDialog alertDialog=dialog.create();
                    alertDialog.show();
                } else {
                    User userToDelete = userfound.get(0);
                    if(userToDelete.getPassword().equals(pass)) {
                        deleteUser(userToDelete.getId());
                    } else {
                        AlertDialog.Builder dialog = new AlertDialog.Builder(DeleteAccount.this);
                        dialog.setMessage("Incorrect data entered");
                        dialog.setTitle("Invalid");
                        dialog.setPositiveButton("OK", (dialog1, which) -> {});
                        AlertDialog alertDialog=dialog.create();
                        alertDialog.show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<User>> call, @NonNull Throwable t) {
            }
        });
    }

    public void deleteUser(int id) {
        Call<User> delete = jsonPlaceHolderApi.deleteUserById(id);
        Log.i("Deletion info", "User id: " + id);
        delete.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(DeleteAccount.this);
                dialog.setMessage("User Deleted");
                dialog.setTitle("Deleted");
                dialog.setPositiveButton("OK", (dialog1, which) -> {});
                AlertDialog alertDialog=dialog.create();
                alertDialog.show();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
            }
        });
    }
}
