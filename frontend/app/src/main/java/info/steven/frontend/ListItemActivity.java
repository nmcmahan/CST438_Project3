package info.steven.frontend;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListItemActivity
        extends AppCompatActivity
        implements AdapterView.OnItemSelectedListener {

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;

    //popup data
    private ImageView imageView;

    private TextView likesView;
    private TextView image_nameView;
    private TextView userView;
    private TextView category;


    private EditText searchUser;
    private EditText searchLikes;

    private static JsonPlaceHolderAPI jsonPlaceHolderAPI;

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
        Spinner categorySpinner = findViewById(R.id.search_category);

        categorySpinner.setOnItemSelectedListener(this);

        String[] items = { "Animal", "Beautiful", "Calming", "Horror", "Inspirational", "Weird"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        categorySpinner.setAdapter(adapter);

        //parameter search
        Button searchButton = findViewById(R.id.paramater_search_Button);

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
        Button viewAllButton = findViewById(R.id.view_all_Button);

        viewAllButton.setOnClickListener(v -> getAllItems());

        Button returnButton = findViewById(R.id.return_Button);

        returnButton.setOnClickListener(v -> goToHome());
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

        LinearLayout ll = findViewById(R.id.linear_layout);

        ll.removeAllViews();

        //call to get all posts
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(@NonNull Call<List<Post>> call, @NonNull Response<List<Post>> response) {
                List<Post> posts = response.body();

                for (Post post : posts) {
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

                    final String data = content;
                    final String url = post.getUrl();

                    final int postId = post.getId();
                    final int[] postLikes = {post.getLikes()};
                    Post updateLikesPost = post;

                    //Make a new button and add it to the app page
                    Button button = new Button(getApplicationContext());
                    button.setText("View Post?");
                    button.setTag(content);
                    button.setOnClickListener(view -> {
                        //create pop up window
                        dialogBuilder = new AlertDialog.Builder(ListItemActivity.this);
                        final View contactPopupView = getLayoutInflater().inflate(R.layout.popwindow, null);
                        //parse data
                        String[] textData = data.split("\n");

                        //display all of the data
                        ImageView imageView = contactPopupView.findViewById(R.id.image);
                        imageView.getLayoutParams().height = 1000;
                        imageView.getLayoutParams().width = 920;

                        Glide.with(ListItemActivity.this).load(url).into(imageView);

                        TextView userView = contactPopupView.findViewById(R.id.user);
                        userView.setText(textData[0]);
                        TextView image_nameView = contactPopupView.findViewById(R.id.image_name);
                        image_nameView.setText(textData[1]);
                        TextView category1 = contactPopupView.findViewById(R.id.category);
                        category1.setText(textData[2]);
                        TextView likesView = contactPopupView.findViewById(R.id.likes);
                        likesView.setText(textData[3]);

                        //create a like Button
                        Button likeButton = contactPopupView.findViewById(R.id.like_Button);
                        final int[] numofclicks = {0};

                        likeButton.setOnClickListener(v -> {
                            Call<Post> updateLike;
                            numofclicks[0]++;
                            if (numofclicks[0] % 2 == 1)
                            {
                                postLikes[0] = postLikes[0] + 1;
                                updateLikesPost.setLikes(postLikes[0]);
                                updateLike = jsonPlaceHolderAPI.updateLike(postId, updateLikesPost);
                            }
                            else
                            {
                                postLikes[0] = postLikes[0] - 1;
                                updateLikesPost.setLikes(postLikes[0]);
                                updateLike = jsonPlaceHolderAPI.updateLike(postId, updateLikesPost);
                            }

                            updateLike.enqueue(new Callback<Post>() {
                                @Override
                                public void onResponse(Call<Post> call, Response<Post> response) {
                                }

                                @Override
                                public void onFailure(Call<Post> call, Throwable t) {
                                    Toast.makeText(ListItemActivity.this,"postId " + postId, Toast.LENGTH_LONG).show();
                                }
                            });
                        });

                        dialogBuilder.setView(contactPopupView);
                        dialog = dialogBuilder.create();
                        dialog.show();
                    });
                    ll.addView(button);

                }
            }
            @Override
            public void onFailure(@NonNull Call<List<Post>> call, @NonNull Throwable t) {
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

        LinearLayout ll = findViewById(R.id.linear_layout);

        ll.removeAllViews();

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(@NonNull Call<List<Post>> call, @NonNull Response<List<Post>> response) {
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

                    final String data = content;
                    final String url = post.getUrl();

                    final int postId = post.getId();
                    final int[] postLikes = {post.getLikes()};
                    Post updateLikesPost = post;

                    //Make a new button and add it to the app page
                    Button button = new Button(getApplicationContext());
                    button.setText("View Post?");
                    button.setTag(content);
                    button.setOnClickListener(view -> {
                        //create pop up window
                        dialogBuilder = new AlertDialog.Builder(ListItemActivity.this);
                        final View contactPopupView = getLayoutInflater().inflate(R.layout.popwindow, null);
                        //parse data
                        String[] textData = data.split("\n");

                        //display all of the data
                        ImageView imageView = contactPopupView.findViewById(R.id.image);
                        imageView.getLayoutParams().height = 1000;
                        imageView.getLayoutParams().width = 920;

                        Glide.with(ListItemActivity.this).load(url).into(imageView);

                        TextView userView = contactPopupView.findViewById(R.id.user);
                        userView.setText(textData[0]);
                        TextView image_nameView = contactPopupView.findViewById(R.id.image_name);
                        image_nameView.setText(textData[1]);
                        TextView category1 = contactPopupView.findViewById(R.id.category);
                        category1.setText(textData[2]);
                        TextView likesView = contactPopupView.findViewById(R.id.likes);
                        likesView.setText(textData[3]);

                        //create a like Button
                        Button likeButton = contactPopupView.findViewById(R.id.like_Button);
                        final int[] numofclicks = {0};

                        likeButton.setOnClickListener(v -> {
                            Call<Post> updateLike;
                            numofclicks[0]++;
                            if (numofclicks[0] % 2 == 1)
                            {
                                postLikes[0] = postLikes[0] + 1;
                                updateLikesPost.setLikes(postLikes[0]);
                                updateLike = jsonPlaceHolderAPI.updateLike(postId, updateLikesPost);
                            }
                            else
                            {
                                postLikes[0] = postLikes[0] - 1;
                                updateLikesPost.setLikes(postLikes[0]);
                                updateLike = jsonPlaceHolderAPI.updateLike(postId, updateLikesPost);
                            }

                            updateLike.enqueue(new Callback<Post>() {
                                @Override
                                public void onResponse(Call<Post> call, Response<Post> response) {
                                }

                                @Override
                                public void onFailure(Call<Post> call, Throwable t) {
                                    Toast.makeText(ListItemActivity.this,"postId " + postId, Toast.LENGTH_LONG).show();
                                }
                            });
                        });

                        dialogBuilder.setView(contactPopupView);
                        dialog = dialogBuilder.create();
                        dialog.show();
                    });
                    ll.addView(button);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Post>> call, @NonNull Throwable t) {
                Toast.makeText(ListItemActivity.this,"Call Failure " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedCategory = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        selectedCategory = "";
    }

    private void goToHome() {
        Intent intent = new Intent(ListItemActivity.this, HomeActivity.class);
        int current_id = getIntent().getIntExtra("CURRENT_ID", 1);
        String currentUser = getIntent().getStringExtra("CURRENT_USER");
        intent.putExtra("CURRENT_ID", current_id);
        intent.putExtra("CURRENT_USER", currentUser);
        startActivity(intent);
    }
}
