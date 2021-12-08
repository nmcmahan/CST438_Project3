package info.steven.frontend;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ViewImageActivity extends AppCompatActivity {

    private TextView imageTitle;
    private TextView imageLikes;
    private TextView imageCategory;
    private TextView imageCreator;

    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);

    }
}
