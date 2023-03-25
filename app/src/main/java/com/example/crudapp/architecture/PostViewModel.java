package com.example.crudapp.architecture;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.crudapp.PostModel;

import java.util.List;

public class PostViewModel extends AndroidViewModel {

    private PostRepository postRepository;
    private LiveData<List<PostModel>> allPosts;

    private static final String SUCCESS = "SUCCESS";

    public PostViewModel(@NonNull Application application) {
        super(application);
        this.postRepository = new PostRepository(application);
        this.allPosts = postRepository.getAllPosts();
    }


    public String insert(PostModel post) {

        String res = validatePost(post);

        if (!res.equals(SUCCESS))
            return res;

        postRepository.insert(post);
        return SUCCESS;
    }

    public String update(PostModel post) {

        String res = validatePost(post);

        if (!res.equals(SUCCESS))
            return res;

        postRepository.update(post);
        return SUCCESS;
    }

    public void delete(PostModel post) {
        postRepository.delete(post);
    }

    public LiveData<List<PostModel>> getAllPosts() {
        return allPosts;
    }

    public String validatePost(PostModel post) {
        if (post.getTitle().isEmpty()) {
            return "Post Title Cannot be Empty!";
        }
        if (post.getDescription().isEmpty()) {
            return "Post Description Cannot be Empty!";
        }
        if (post.getAuthor().isEmpty()) {
            return "Post Author Cannot be Empty!";
        }
        if (post.getTitle().length() > 100) {
            return "Post Title length should be less than 100 characters";
        }
        if (post.getAuthor().length() > 50) {
            return "Post AAuthor name should be less than 100 characters";
        }

        return SUCCESS;
    }

}
