package info.steven.frontend;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private EditText username;
    private EditText password;
    private Button login_button;

    private TextView getUser;

    private JsonPlaceHolderAPI jsonPlaceHolderApi;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getUser = findViewById(R.id.display_User);

        //getting data from Heroku
        Gson gson = new GsonBuilder().serializeNulls().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://cst438-project3.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderAPI.class);

        int id = 2;
        Call<User> call = jsonPlaceHolderApi.GetUserbyId(id);


        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                User user = response.body();

                String content = "";

                content = String.valueOf(user.getUsername());

                getUser.setText(content);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                getUser.setText("Call Failure");
            }
        });

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login_button = findViewById(R.id.login);
        TextView signUpLink = findViewById(R.id.signUpLink);

        login_button.setOnClickListener(view -> {
            String name = username.getText().toString();
            String pass = password.getText().toString();

            if (name.isEmpty() && pass.isEmpty()) {
                username.setError("This field cannot be blank");
                password.setError("This field cannot be blank");
            }

            if (name.isEmpty()) {
                username.setError("This field cannot be blank");
            }

            if (pass.isEmpty()) {
                password.setError("This field cannot be blank");
            }

            boolean isValid = validate(name, pass);

            if (isValid) {
//                loadHomeActivity(view, username.getText().toString());
            } else {
                alertDialog();
                boolean foundUser = findUser(name);

                if (!foundUser) {
                    username.setError("Your username is incorrect");
                } else {
                    password.setError("Your password is incorrect");
                }
            }
        });

        signUpLink.setOnClickListener(view -> goToSignUpPage());
    }

    static boolean validate(String name, String pass) {
        return true;
    }

    static boolean findUser(String name) {
        return true;
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

/*
    private void loadHomeActivity(View view, String username) {
        Intent intent = HomeActivity.getIntent(getApplicationContext());
        intent.putExtra("CURRENT_USERNAME", username);
        startActivity(intent);
    }
*/

    public static Intent getIntent(Context context){
        return new Intent(context, MainActivity.class);
    }
}
