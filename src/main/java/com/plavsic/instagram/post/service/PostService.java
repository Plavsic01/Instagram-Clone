package com.plavsic.instagram.post.service;

import com.plavsic.instagram.post.domain.Post;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {

    void createPost(UserDetails currentUser, MultipartFile file, String description);
    void updatePost(Post post);
    void deletePost(Post post);
    List<Post> getPosts();
    Post getPost(Long id);
}