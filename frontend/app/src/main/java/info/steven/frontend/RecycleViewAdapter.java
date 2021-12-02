package info.steven.frontend;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.MyViewHolder> {

    private static final String TAG = "RecycleView";
    List<Post> postList;
    Context context;

    public RecycleViewAdapter(List<Post> postList, Context context) {
        this.postList = postList;
        this.context = context;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_postPic;
        TextView tv_name;
        TextView tv_likes;
        TextView tv_category;
        TextView tv_username;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            iv_postPic = itemView.findViewById(R.id.iv_postPic);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_likes = itemView.findViewById(R.id.tv_likes);
            tv_category = itemView.findViewById(R.id.tv_category);
            tv_username = itemView.findViewById(R.id.tv_username);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_line_post, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv_name.setText(postList.get(position).getName());
        holder.tv_likes.setText(String.valueOf(postList.get(position).getLikes()));
        holder.tv_category.setText(postList.get(position).getCategory());
        holder.tv_username.setText(postList.get(position).getUser_id());
        Glide.with(this.context).load(postList.get(position).getUrl()).into(holder.iv_postPic);
        Log.d(TAG, "onBindViewHolder: " + postList.get(position).getUrl());
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }
}
