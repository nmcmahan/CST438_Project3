package info.steven.frontend;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddEditPost extends AppCompatActivity {


    private static JsonPlaceHolderAPI jsonPlaceHolderApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_post);

        final String[] category = {""};
        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                category[0] = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                category[0] = (String) parent.getItemAtPosition(0);
            }
        });
        EditText mtitle = findViewById(R.id.image_title);
        EditText murl = findViewById(R.id.image_url);
        Button okButton = findViewById(R.id.btn_ok);
        Button cancelButton = findViewById(R.id.btn_cancel);

        new GsonBuilder().serializeNulls().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://cst438-project3.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderAPI.class);

        okButton.setOnClickListener(view -> {
            String title = mtitle.getText().toString();
            String url = murl.getText().toString();
            int uid = getIntent().getIntExtra("CURRENT_ID", 1);
            String useridStr = "https://cst438-project3.herokuapp.com/users/" + uid + "/";

            if (title.isEmpty()) {
                mtitle.setError("Title is Required");
            } else if (url.isEmpty()) {
                murl.setError("Url is required");
            } else {
                postItem(useridStr, title, category[0], url);
            }


        });

        cancelButton.setOnClickListener(view -> {
            Intent intent = new Intent(AddEditPost.this, HomeActivity.class);
            startActivity(intent);
        });

    }
    public void postItem(String user_id, String name, String category, String url) {
        String creator = getIntent().getStringExtra("CURRENT_USER");
        Post newItem = new Post(user_id, 0, name, category, url, creator);
        Call<Post> postCall = jsonPlaceHolderApi.createPost(newItem);
        postCall.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(@NonNull Call<Post> call, @NonNull Response<Post> response) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(AddEditPost.this);
                dialog.setMessage("The Item has been added");
                dialog.setTitle("POST success");
                dialog.setPositiveButton("OK", (dialog1, which) -> {});
                AlertDialog alertDialog=dialog.create();
                alertDialog.show();
            }

            @Override
            public void onFailure(@NonNull Call<Post> call, @NonNull Throwable t) {

            }
        });
    }
}