 package com.example.crudapp;

 import android.content.Intent;
 import android.os.Bundle;
 import android.widget.Button;
 import android.widget.TextView;

 import androidx.appcompat.app.AppCompatActivity;
 import androidx.lifecycle.ViewModelProvider;

 import com.example.crudapp.architecture.PostViewModel;

 public class ShowPostActivity extends AppCompatActivity {

    TextView postTitleView, postDescView, postAuthorView, postDateView, upvoteCountView, downvoteCountView;
    Button deleteBtn, editBtn;
     private PostViewModel postViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_post);

        postTitleView = findViewById(R.id.title_text_view);
        postDescView = findViewById(R.id.desc_text_view);
        postAuthorView = findViewById(R.id.author_text_view);
        postDateView = findViewById(R.id.date_text_view);
        upvoteCountView = findViewById(R.id.upvote_count_view);
        downvoteCountView = findViewById(R.id.downvote_count_view);
        deleteBtn = findViewById(R.id.delete_btn);
        editBtn = findViewById(R.id.edit_btn);

        postViewModel = new ViewModelProvider(this).get(PostViewModel.class);

        Intent intent = getIntent();
        String title = intent.getStringExtra("POST_TITLE");
        String desc = intent.getStringExtra("POST_DESC");
        String author = intent.getStringExtra("POST_AUTHOR");
        String date =  intent.getStringExtra("POST_DATE");
        int upvotes = intent.getIntExtra("POST_UPVOTE_COUNT",0);
        int downvotes = intent.getIntExtra("POST_DOWNVOTE_COUNT",0);

        postTitleView.setText(title);
        postDescView.setText(desc);
        postAuthorView.setText(author);
        postDateView.setText(date);
        upvoteCountView.setText(""+upvotes);
        downvoteCountView.setText(""+downvotes);

        int id = intent.getIntExtra("POST_ID",-1);

        editBtn.setOnClickListener(view -> {

            Intent editIntent = new Intent(this, AddPostActivity.class);
            editIntent.putExtra("ACTION_TYPE","EDIT");
            editIntent.putExtra("POST_ID",id);
            editIntent.putExtra("POST_TITLE",title);
            editIntent.putExtra("POST_DESC",desc);
            editIntent.putExtra("POST_AUTHOR",author);
            editIntent.putExtra("POST_DATE",date);
            editIntent.putExtra("POST_UPVOTE_COUNT",upvotes);
            editIntent.putExtra("POST_DOWNVOTE_COUNT",downvotes);
            startActivity(editIntent);
            this.finish();

        });

        deleteBtn.setOnClickListener(view -> {
            PostModel post = new PostModel(title,desc,author,date,upvotes,downvotes);
            post.setId(id);
            postViewModel.delete(post);
            startActivity(new Intent(this,MainActivity.class));
            this.finish();
        });

    }
}