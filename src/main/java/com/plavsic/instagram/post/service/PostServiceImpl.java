package com.plavsic.instagram.post.service;

import com.plavsic.instagram.post.domain.Post;
import com.plavsic.instagram.post.repository.PostRepository;
import com.plavsic.instagram.user.domain.User;
import com.plavsic.instagram.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    @Override
    public List<Post> getPosts() {
        return List.of();
    }

    @Override
    public Post getPost(Long id) {
        return null;
    }

    @Override
    @Transactional
    public void createPost(UserDetails currentUser,MultipartFile file,String description) {
        String imageUrl = saveImage(uploadDir,file);
        User user = userRepository.findByUsername(currentUser.getUsername()).get();
        Post newPost = new Post();
        newPost.setId(null);
        newPost.setDescription(description);
        newPost.setCreatedAt(LocalDateTime.now());
        newPost.setImageUrl(imageUrl);
        user.addPost(newPost);
        postRepository.save(newPost);
    }

    @Override
    public void updatePost(Post post) {

    }

    @Override
    public void deletePost(Post post) {

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

        return filePath.toString();
    }
}

