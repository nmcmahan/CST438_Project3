package info.steven.frontend;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class SignUp extends AppCompatActivity {

    private EditText mUsernameField;
    private EditText mPasswordField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mUsernameField = findViewById(R.id.usernameEditText);
        mPasswordField = findViewById(R.id.passwordEditText);

        Button submitBtn = findViewById(R.id.submitBtn);
        Button homeBtn = findViewById(R.id.homeBtn);

        submitBtn.setOnClickListener(v -> {
            mUsernameField.getText().toString();
            mPasswordField.getText().toString();
        });

        homeBtn.setOnClickListener(view -> goToHomePage());
    }

    public void goToHomePage(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
