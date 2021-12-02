package info.steven.frontend;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AddEditPost extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_post);

        Button okButton = findViewById(R.id.btn_ok);
        Button cancelButton = findViewById(R.id.btn_cancel);

        okButton.setOnClickListener(view -> {
            Intent intent = new Intent(AddEditPost.this, HomeActivity.class);
            startActivity(intent);
        });

        cancelButton.setOnClickListener(view -> {
            Intent intent = new Intent(AddEditPost.this, HomeActivity.class);
            startActivity(intent);
        });
    }
}