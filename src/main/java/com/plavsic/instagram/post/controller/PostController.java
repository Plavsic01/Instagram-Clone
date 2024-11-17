package com.plavsic.instagram.post.controller;


import com.plavsic.instagram.post.dto.comment.CommentRequest;
import com.plavsic.instagram.post.dto.comment.CommentResponse;
import com.plavsic.instagram.post.dto.comment.CreatedCommentResponse;
import com.plavsic.instagram.post.dto.comment.UpdatedCommentResponse;
import com.plavsic.instagram.post.dto.post.PostAndUserResponse;
import com.plavsic.instagram.post.dto.post.PostResponse;
import com.plavsic.instagram.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    /*
     * POSTS
     */

    // view posts by user
    @GetMapping("/{username}")
    public ResponseEntity<List<PostResponse>> getUserPosts(
            @AuthenticationPrincipal UserDetails currentUser,
            @PathVariable String username) {
        return new ResponseEntity<>(postService.getUserPosts(currentUser,username),HttpStatus.OK);
    }

    // view one post by id
//    @GetMapping("/post/{postId}")
//    public ResponseEntity<PostResponse> getPostById(@PathVariable Long postId) {
//        return new ResponseEntity<>(postService.getPost(postId),HttpStatus.OK);
//    }

    // view all posts that current user follows
    @GetMapping
    public ResponseEntity<Page<PostAndUserResponse>> findAllByOrderByCreatedAtDesc(
            @AuthenticationPrincipal UserDetails currentUser,
            @RequestParam int page,
            @RequestParam int size
    )  {
        Page<PostAndUserResponse> posts = postService.findPostsByUsersIFollow(currentUser,page,size);
        return new ResponseEntity<>(posts,HttpStatus.OK);
    }

    // create post
    @PostMapping
    public ResponseEntity<PostResponse> createPost(
            @AuthenticationPrincipal UserDetails currentUser,
            @RequestParam(value = "description",required = true) String description,
            @RequestParam(value = "file",required = true) MultipartFile file) {
        if(description == null || description.isEmpty() || file == null || file.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        PostResponse postResponse = postService.createPost(currentUser,file,description);
        return new ResponseEntity<>(postResponse, HttpStatus.CREATED);
    }
    // update post
    @PutMapping("/post/{postId}")
    public ResponseEntity<String> updatePost(
            @AuthenticationPrincipal UserDetails currentUser,
            @PathVariable Long postId,
            @RequestParam(value = "description",required = true) String description){
        postService.updatePost(currentUser,postId,description);
        return new ResponseEntity<>("Successfully updated post!",HttpStatus.OK);
    }

    // delete post
    @DeleteMapping("/post/{postId}")
    public ResponseEntity<String> deletePost(
            @AuthenticationPrincipal UserDetails currentUser,
            @PathVariable Long postId) {
        postService.deletePost(currentUser,postId);
        return new ResponseEntity<>("Successfully deleted post!",HttpStatus.OK);
    }

    /**
     * COMMENTS
     */

    // get comments for specific post
    @GetMapping("/comments/{postId}")
    public ResponseEntity<List<CommentResponse>> getComments(@PathVariable Long postId){
        return new ResponseEntity<>(postService.getComments(postId),HttpStatus.OK);
    }

    // create comment for specific post
    @PostMapping("/add-comment/{postId}")
    public ResponseEntity<CreatedCommentResponse> addComment(
            @AuthenticationPrincipal UserDetails currentUser,
            @RequestBody CommentRequest commentRequest,
            @PathVariable Long postId) {
        CreatedCommentResponse comment = postService.createComment(currentUser,postId,commentRequest);
        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

    // update comment based on their ID
    @PutMapping("/edit-comment/{commentId}")
    public ResponseEntity<UpdatedCommentResponse> editComment(
            @AuthenticationPrincipal UserDetails currentUser,
            @RequestBody CommentRequest commentRequest,
            @PathVariable Long commentId){
        UpdatedCommentResponse updatedComment = postService.updateComment(currentUser,commentId,commentRequest);
        return new ResponseEntity<>(updatedComment,HttpStatus.OK);
    }

    // remove comment based on their ID
    @DeleteMapping("/remove-comment/{commentId}")
    public ResponseEntity<String> removeComment(
            @AuthenticationPrincipal UserDetails currentUser,
            @PathVariable Long commentId) {
        postService.removeComment(currentUser,commentId);
        return new ResponseEntity<>("Comment Removed!", HttpStatus.CREATED);
    }



    /*
     * LIKE POSTS, UNLIKE POSTS
     */


    // like specific post
    @PostMapping("/like-post/{postId}")
    public ResponseEntity<Boolean> likePost(
            @AuthenticationPrincipal UserDetails currentUser,
            @PathVariable Long postId
    ){
        return new ResponseEntity<>(postService.likePost(currentUser,postId), HttpStatus.CREATED);
    }

    // remove like from specific post
    @DeleteMapping("/unlike-post/{postId}")
    public ResponseEntity<Boolean> unlikePost(
            @AuthenticationPrincipal UserDetails currentUser,
            @PathVariable Long postId
    ){
        return new ResponseEntity<>(postService.unlikePost(currentUser,postId), HttpStatus.CREATED);
    }
}
