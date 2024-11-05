package com.plavsic.instagram.user.domain;

import com.plavsic.instagram.post.domain.Post;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String profilePictureUrl;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.PERSIST)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id",referencedColumnName = "id"))
    private Set<Role> roles;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
            name = "user_relationships",
            joinColumns = @JoinColumn(name = "follower_id"),
            inverseJoinColumns = @JoinColumn(name = "following_id")
    )
    @ToString.Exclude
    private Set<User> following = new HashSet<>();

    @ManyToMany(mappedBy = "following")
    @ToString.Exclude
    private Set<User> followers = new HashSet<>();

    @OneToMany(mappedBy = "createdBy",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<Post> posts = new HashSet<>();


    public void addPost(Post post) {
        this.posts.add(post);
        post.setCreatedBy(this);
    }

    public void follow(User user) {
        this.following.add(user);
        user.followers.add(this);
    }

    public void unfollow(User user) {
        this.following.remove(user);
        user.followers.remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


}
