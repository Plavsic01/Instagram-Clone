package com.plavsic.instagram.post.repository;

import com.plavsic.instagram.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> {
}
