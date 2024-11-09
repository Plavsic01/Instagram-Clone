package com.plavsic.instagram.post.service;

import com.plavsic.instagram.post.dto.comment.CommentRequest;
import com.plavsic.instagram.post.dto.comment.CommentResponse;
import com.plavsic.instagram.post.dto.post.PostResponse;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {

    void createPost(UserDetails currentUser, MultipartFile file, String description);
    void updatePost(UserDetails currentUser,Long postId,String description);
    void deletePost(UserDetails currentUser,Long postId);
    List<PostResponse> getUserPosts(String username);
    PostResponse getPost(Long id);
    void likePost(UserDetails currentUser,Long postId);
    void unlikePost(UserDetails currentUser, Long postId);
    void createComment(UserDetails currentUser,Long postId, CommentRequest commentRequest);
    void updateComment(UserDetails currentUser,Long commentId, CommentRequest commentRequest);
    void removeComment(UserDetails currentUser, Long commentId);
    List<CommentResponse> getComments(Long postId);

}