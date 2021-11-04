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

public class MainActivity extends AppCompatActivity {
    private EditText username;
    private EditText password;
    private Button login_button;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
