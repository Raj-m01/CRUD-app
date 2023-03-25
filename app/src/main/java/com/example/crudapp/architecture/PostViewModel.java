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

    public PostViewModel(@NonNull Application application){
        super(application);
        this.postRepository = new PostRepository(application);
        this.allPosts = postRepository.getAllPosts();
    }


    public void insert(PostModel post){
        postRepository.insert(post);
    }

    public void update(PostModel post){
        postRepository.update(post);
    }

    public void delete(PostModel post){
        postRepository.delete(post);
    }

    public LiveData<List<PostModel>> getAllPosts(){
        return allPosts;
    }

}
