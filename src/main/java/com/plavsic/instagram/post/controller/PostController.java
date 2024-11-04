package com.plavsic.instagram.post.controller;


import com.plavsic.instagram.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // pregled objava

    // pregled jedne objave

    // kreiranje objave
    @PostMapping
    public ResponseEntity<String> createPost(
            @AuthenticationPrincipal UserDetails currentUser,
            @RequestParam(value = "description",required = true) String description,
            @RequestParam(value = "file",required = true) MultipartFile file
                                            ) {
        if(description == null || description.isEmpty() || file == null || file.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        postService.createPost(currentUser,file,description);
        return new ResponseEntity<>("Post Created!", HttpStatus.CREATED);
    }
    // izmena objave

    // brisanje objave
}
