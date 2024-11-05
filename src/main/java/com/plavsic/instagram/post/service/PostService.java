package com.plavsic.instagram.post.service;

import com.plavsic.instagram.post.domain.Post;
import com.plavsic.instagram.post.dto.CommentRequest;
import com.plavsic.instagram.post.dto.CommentResponse;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {

    void createPost(UserDetails currentUser, MultipartFile file, String description);
    void updatePost(Post post);
    void deletePost(Post post);
    List<Post> getPosts();
    Post getPost(Long id);
    void createComment(UserDetails currentUser,Long postId, CommentRequest commentRequest);
    void likePost(UserDetails currentUser,Long postId);
    void unlikePost(UserDetails currentUser, Long postId);
    void removeComment(UserDetails currentUser, Long commentId);
    List<CommentResponse> getComments(Long postId);
//    void likeComment(Long commentId);
//    void unlikeComment(Long commentId);
}