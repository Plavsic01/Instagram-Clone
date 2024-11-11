package com.plavsic.instagram.post.service;

import com.plavsic.instagram.post.domain.Comment;
import com.plavsic.instagram.post.domain.Post;
import com.plavsic.instagram.post.dto.comment.CommentRequest;
import com.plavsic.instagram.post.dto.comment.CommentResponse;
import com.plavsic.instagram.post.dto.post.PostResponse;
import com.plavsic.instagram.post.exception.CommentNotFoundException;
import com.plavsic.instagram.post.exception.PostNotFoundException;
import com.plavsic.instagram.post.repository.CommentRepository;
import com.plavsic.instagram.post.repository.PostRepository;
import com.plavsic.instagram.user.domain.User;
import com.plavsic.instagram.user.exception.UserNotFoundException;
import com.plavsic.instagram.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    @Value("${file.upload-dir}")
    private String uploadDir;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;


    /*
     * POSTS
     */

    @Override
    public List<PostResponse> getUserPosts(String username) {
        User user = getUserByUsername(username);
        return postRepository.findByCreatedBy(user).stream().map(post -> new PostResponse(post.getId(),
                post.getDescription(),
                post.getCreatedAt(),
                post.getImageUrl(),
                post.getNumberOfLikes().size()
        )).toList();
    }

    @Override
    public PostResponse getPost(Long id) {
        Post post = getPostById(id);
        return new PostResponse(post.getId(),post.getDescription(),post.getCreatedAt(),post.getImageUrl(),null);
    }

    @Override
    @Transactional
    public void createPost(UserDetails currentUser,MultipartFile file,String description) {
        String imageUrl = saveImage(uploadDir,file);
        User user = getUserByUsername(currentUser.getUsername());
        Post newPost = new Post();
        newPost.setId(null);
        newPost.setDescription(description);
        newPost.setCreatedAt(LocalDateTime.now());
        newPost.setImageUrl(imageUrl);
        user.addPost(newPost);
        postRepository.save(newPost);
    }

    @Override
    @Transactional
    public void updatePost(UserDetails currentUser,Long postId,String description) {
        User user = getUserByUsername(currentUser.getUsername());
        Post post = getPostById(postId);
        if(!user.getUsername().equals(post.getCreatedBy().getUsername())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        post.setDescription(description);
        postRepository.save(post);
    }

    @Override
    public void deletePost(UserDetails currentUser,Long postId) {
        Post post = getPostById(postId);
        if(!post.getCreatedBy().getUsername().equals(currentUser.getUsername())){
               throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        postRepository.delete(post);
    }

    @Override
    public void likePost(UserDetails currentUser, Long postId) {
        postLikeOrUnlike(currentUser,postId,true);
    }

    @Override
    public void unlikePost(UserDetails currentUser, Long postId) {
        postLikeOrUnlike(currentUser,postId,false);
    }




    /*
     * COMMENTS
     */

    @Override
    public List<CommentResponse> getComments(Long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        if(comments.isEmpty()) {
            throw new CommentNotFoundException("Comments not found!");
        }

        return comments.stream().map(comment ->

                        new CommentResponse(
                                comment.getId(),
                                comment.getCreatedBy().getUsername(),
                                comment.getCreatedBy().getProfilePictureUrl(),
                                comment.getContent(),
                                comment.getCreatedAt()
                                ))
                .toList();
    }

    @Override
    public void createComment(UserDetails currentUser, Long postId, CommentRequest commentRequest) {
        User user = getUserByUsername(currentUser.getUsername());
        Post post = getPostById(postId);
        Comment comment = new Comment();
        comment.setId(null);
        comment.setCreatedAt(LocalDateTime.now());
        comment.setCreatedBy(user);
        comment.setContent(commentRequest.content());
        post.addComment(comment);
        commentRepository.save(comment);
    }


    @Override
    public void updateComment(UserDetails currentUser, Long commentId, CommentRequest commentRequest) {
        User user = getUserByUsername(currentUser.getUsername());
        Comment comment = getCommentById(commentId);
        if(!user.getUsername().equals(comment.getCreatedBy().getUsername())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        comment.setContent(commentRequest.content());
        commentRepository.save(comment);
    }

    @Override
    public void removeComment(UserDetails currentUser,Long commentId) {
        Comment comment = getCommentById(commentId);
        comment.getPost().removeComment(comment);
        commentRepository.save(comment);
    }




    private User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
    }

    private Post getPostById(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new PostNotFoundException("Post not found!"));
    }

    private Comment getCommentById(Long id) {
        return commentRepository.findById(id).orElseThrow(() -> new CommentNotFoundException("Comment not found!"));
    }

    private void postLikeOrUnlike(UserDetails currentUser, Long postId,boolean isLike) {
        User user = getUserByUsername(currentUser.getUsername());
        Post post = getPostById(postId);
        if(isLike){
            if (post.isLikedBy(user)) {
                throw new ResponseStatusException(HttpStatus.NO_CONTENT,"User has already liked that post!");
            }
            post.addLike(user);
            postRepository.save(post);
            return;
        }

        if(!post.isLikedBy(user)) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT,"User has not liked that post!");
        }
        post.removeLike(user);
        postRepository.save(post);
    }


    private String saveImage(String uploadDir,MultipartFile file) {
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            try {
                Files.createDirectories(uploadPath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        String fileName = file.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);
        try {
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return filePath.getFileName().toString();
    }
}

