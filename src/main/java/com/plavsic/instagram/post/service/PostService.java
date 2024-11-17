package com.plavsic.instagram.post.service;

import com.plavsic.instagram.post.dto.comment.CommentRequest;
import com.plavsic.instagram.post.dto.comment.CommentResponse;
import com.plavsic.instagram.post.dto.comment.CreatedCommentResponse;
import com.plavsic.instagram.post.dto.comment.UpdatedCommentResponse;
import com.plavsic.instagram.post.dto.post.PostAndUserResponse;
import com.plavsic.instagram.post.dto.post.PostResponse;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {

    PostResponse createPost(UserDetails currentUser, MultipartFile file, String description);
    void updatePost(UserDetails currentUser,Long postId,String description);
    void deletePost(UserDetails currentUser,Long postId);
    List<PostResponse> getUserPosts(UserDetails currentUser,String username);
    Page<PostAndUserResponse> findPostsByUsersIFollow(UserDetails currentUser, int page, int size);
//    PostResponse getPost(Long id);
    boolean likePost(UserDetails currentUser,Long postId);
    boolean unlikePost(UserDetails currentUser, Long postId);
    CreatedCommentResponse createComment(UserDetails currentUser, Long postId, CommentRequest commentRequest);
    UpdatedCommentResponse updateComment(UserDetails currentUser, Long commentId, CommentRequest commentRequest);
    void removeComment(UserDetails currentUser, Long commentId);
    List<CommentResponse> getComments(Long postId);

}