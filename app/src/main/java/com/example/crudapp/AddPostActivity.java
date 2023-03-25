package com.example.crudapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.crudapp.architecture.PostViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddPostActivity extends AppCompatActivity {

    EditText titleEditText, descEditText, authorEditText;
    Button submitPostBtn;
    private PostViewModel postViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_post);

        postViewModel = new ViewModelProvider(this).get(PostViewModel.class);

        titleEditText = findViewById(R.id.title_edit_text);
        descEditText = findViewById(R.id.desc_edit_text);
        authorEditText = findViewById(R.id.author_edit_text);
        submitPostBtn = findViewById(R.id.submit_post_btn);

        Intent intent = getIntent();
        String actionType =intent.getStringExtra("ACTION_TYPE");


        if(actionType.equals("EDIT")){
            titleEditText.setText(intent.getStringExtra("POST_TITLE"));
            descEditText.setText(intent.getStringExtra("POST_DESC"));
            authorEditText.setText(intent.getStringExtra("POST_AUTHOR"));
        }


        submitPostBtn.setOnClickListener(view -> {

            String title = titleEditText.getText().toString().trim();
            String desc = descEditText.getText().toString().trim();
            String author = authorEditText.getText().toString().trim();

            String curDate = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
            PostModel post = new PostModel(title, desc, author, curDate,0,0);

            if(title.isEmpty() || desc.isEmpty() || author.isEmpty()){
                Toast.makeText(this, "Enter All Details", Toast.LENGTH_SHORT).show();
            }else{

                if(actionType.equals("ADD")) {
                    postViewModel.insert(post);
                    Toast.makeText(this, "Post Added", Toast.LENGTH_SHORT).show();
                }else{
                    int id = intent.getIntExtra("POST_ID",-1);
                    post.setId(id);
                    postViewModel.update(post);
                    Toast.makeText(this, "Post Updated", Toast.LENGTH_SHORT).show();
                }

                startActivity(new Intent(this,MainActivity.class));
                this.finish();

            }
        });

    }

}