package com.example.crudapp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crudapp.architecture.PostViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PostListAdapter extends RecyclerView.Adapter<PostListAdapter.ViewHolder> {

    private List<PostModel> postData = new ArrayList<>();
    private Context context;
    private PostViewModel postViewModel;

    public void updatePostList(List<PostModel> postData) {
        this.postData.clear();
        this.postData.addAll(postData);
        Collections.sort(postData, new Comparator<PostModel>() {
            @Override
            public int compare(PostModel post1, PostModel post2) {
                return Integer.compare(post2.getUpvote(), post1.getUpvote());
            }
        });
        Log.d("db", "list updated");
        notifyDataSetChanged();
    }

    public PostListAdapter(PostViewModel postViewModel) {
        this.postViewModel = postViewModel;
    }

    @NonNull
    @Override
    public PostListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.post_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PostListAdapter.ViewHolder holder, int position) {
        final PostModel post = postData.get(position);
        holder.postTitle.setText(post.getTitle());
        holder.postDesc.setText(post.getDescription());
        holder.upvote.setText(String.valueOf(post.getUpvote()));
        holder.downvote.setText(String.valueOf(post.getDownvote()));

        holder.showPostButton.setOnClickListener(view -> {
            Intent intent = new Intent(context, ShowPostActivity.class);
            intent.putExtra("POST_ID", post.getId());
            intent.putExtra("POST_TITLE", post.getTitle());
            intent.putExtra("POST_DESC", post.getDescription());
            intent.putExtra("POST_AUTHOR", post.getAuthor());
            intent.putExtra("POST_DATE", post.getDate());
            intent.putExtra("POST_UPVOTE_COUNT", post.getUpvote());
            intent.putExtra("POST_DOWNVOTE_COUNT", post.getDownvote());
            context.startActivity(intent);
        });

        holder.upvoteBtn.setOnClickListener(view -> {
            post.setUpvote(post.getUpvote() + 1);
            postViewModel.update(post);
        });

        holder.downvoteBtn.setOnClickListener(view -> {
            post.setDownvote(post.getDownvote() + 1);
            postViewModel.update(post);
        });

    }

    @Override
    public int getItemCount() {
        return postData != null ? postData.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView postTitle, postDesc, upvote, downvote;
        public Button showPostButton;
        public ImageButton upvoteBtn, downvoteBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.postTitle = itemView.findViewById(R.id.post_title);
            this.postDesc = itemView.findViewById(R.id.post_desc);
            this.showPostButton = itemView.findViewById(R.id.show_post_btn);
            this.upvote = itemView.findViewById(R.id.upvote_count);
            this.downvote = itemView.findViewById(R.id.downvote_count);
            this.upvoteBtn = itemView.findViewById(R.id.upvote_btn);
            this.downvoteBtn = itemView.findViewById(R.id.downvote_btn);
        }
    }

}
