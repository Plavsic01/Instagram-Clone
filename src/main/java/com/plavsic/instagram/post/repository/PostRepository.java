package com.plavsic.instagram.post.repository;

import com.plavsic.instagram.post.domain.Post;
import com.plavsic.instagram.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {
    List<Post> findByCreatedBy(User createdBy);
}
