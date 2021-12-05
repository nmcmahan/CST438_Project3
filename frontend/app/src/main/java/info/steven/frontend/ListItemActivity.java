package info.steven.frontend;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListItemActivity
        extends AppCompatActivity
        implements AdapterView.OnItemSelectedListener {

    private EditText searchUser;
    private EditText searchLikes;

    private Spinner categorySpinner;

    private static JsonPlaceHolderAPI jsonPlaceHolderAPI;

    private Button viewAllButton;

    private Button searchButton;

    public String selectedCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_item);

        selectedCategory = "";

        //Get inputs
        searchUser = findViewById(R.id.search_user);
        searchLikes = findViewById(R.id.search_likes);

        //make the dropdown
        categorySpinner = findViewById(R.id.search_category);

        categorySpinner.setOnItemSelectedListener(this);

        String[] items = { "Animal", "Beautiful", "Calming", "Horror", "Inspirational", "Weird"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        categorySpinner.setAdapter(adapter);

        //parameter search
        searchButton = findViewById(R.id.paramater_search_Button);

        searchButton.setOnClickListener(v -> {
            String user = searchUser.getText().toString();
            String likes = searchLikes.getText().toString();

            if (user.isEmpty())
            {
                user = "";
            }
            if (likes.isEmpty())
            {
                likes = "";
            }

            String category = selectedCategory;

            searchForItems(user, category, likes);
        });

        //view all items
        viewAllButton = findViewById(R.id.view_all_Button);

        viewAllButton.setOnClickListener(v -> {
            getAllItems();
        });
    }

    public void getAllItems()
    {
        new GsonBuilder().serializeNulls().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://cst438-project3.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderAPI = retrofit.create(JsonPlaceHolderAPI.class);

        Call<List<Post>> call = jsonPlaceHolderAPI.getAllPosts();

        LinearLayout ll = (LinearLayout)findViewById(R.id.linear_layout);

        ll.removeAllViews();

        //call to get all posts
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                List<Post> posts = response.body();
                ListIterator<Post> postListIterator = posts.listIterator();

                while(postListIterator.hasNext())
                {
                    Post post = postListIterator.next();

                    String username = post.getCreator();
                    String title = post.getName();
                    String category = post.getCategory();
                    int likes = post.getLikes();

                    String content = "";

                    content += "User: " + username + "\n";
                    content += "Title: " + title + "\n";
                    content += "Category: " + category + "\n";
                    content += "likes: " + likes + "\n";
                    content += "\n";

                    //make a new textview and add it to the linear layout

                    TextView newTextView = new TextView(getApplicationContext());

                    LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    newTextView.setLayoutParams(lparams);
                    newTextView.setText(content);
                    ll.addView(newTextView);
                    //Toast.makeText(ListItemActivity.this,"Title: " + title, Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Toast.makeText(ListItemActivity.this,"Call Failure " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void searchForItems(String user, String category, String likes)
    {

        new GsonBuilder().serializeNulls().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://cst438-project3.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolderAPI = retrofit.create(JsonPlaceHolderAPI.class);

        Call<List<Post>> call = jsonPlaceHolderAPI.searchForPosts(user, category, likes);

        LinearLayout ll = (LinearLayout)findViewById(R.id.linear_layout);

        ll.removeAllViews();

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                List<Post> posts = response.body();

                for (Post post : posts)
                {
                    String username = post.getCreator();
                    String title = post.getName();
                    String category = post.getCategory();
                    int likes = post.getLikes();

                    String content = "";

                    content += "User: " + username + "\n";
                    content += "Title: " + title + "\n";
                    content += "Category: " + category + "\n";
                    content += "likes: " + likes + "\n";
                    content += "\n";

                    //make a new textview and add it to the linear layout
                    TextView newTextView = new TextView(getApplicationContext());

                    LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    newTextView.setLayoutParams(lparams);
                    newTextView.setText(content);
                    ll.addView(newTextView);
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Toast.makeText(ListItemActivity.this,"Call Failure " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();

        selectedCategory = item;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        selectedCategory = "";
    }
}
