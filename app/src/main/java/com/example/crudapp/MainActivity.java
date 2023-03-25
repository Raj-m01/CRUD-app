package com.example.crudapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crudapp.architecture.PostViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private PostViewModel postViewModel;
    private FloatingActionButton addPostBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        addPostBtn = findViewById(R.id.fab_add_post_btn);

        final PostListAdapter adapter = new PostListAdapter();

        // Get the ViewModel and observe the data
        postViewModel = new ViewModelProvider(this).get(PostViewModel.class);
        postViewModel.getAllPosts().observe(this, new Observer<List<PostModel>>() {
            @Override
            public void onChanged(List<PostModel> posts) {
                adapter.updatePostList(posts);
            }
        });


        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        addPostBtn.setOnClickListener(view -> {

            Intent intent = new Intent(MainActivity.this, AddPostActivity.class);
            intent.putExtra("ACTION_TYPE","ADD");
            startActivity(intent);
        });



    }
}