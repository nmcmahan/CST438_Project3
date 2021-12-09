package info.steven.frontend;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.TextView;

public class Pop extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.popwindow);

        //display data
        ImageView imageView = findViewById(R.id.image);

        TextView likesView = findViewById(R.id.likes);
        TextView image_nameView = findViewById(R.id.image_name);
        TextView userView = findViewById(R.id.user);
        TextView category = findViewById(R.id.category);


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width *.8),(int)(height*.6));
    }
}
