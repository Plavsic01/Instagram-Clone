# Instagram Clone Backend

This repository contains the backend implementation for an Instagram clone. The backend is built with **Spring Boot** and provides a range of functionalities such as user authentication, post management, comments, likes, and following systems.

### Features:

- **Authentication & Authorization**: Role-based login and registration using JWT tokens.
- **Posts**: Create, update, delete, and retrieve posts.
- **Comments**: Create, update, delete, and fetch comments for specific posts.
- **Likes**: Like and unlike posts.
- **Followers/Following**: Follow and unfollow users.
- **User Management**: Fetch and delete users.
- **Search**: Search for users by username.

### API Endpoints:

#### **Authentication & User Management**:

- **POST** `/api/v1/auth/login`: Login a user.
- **POST** `/api/v1/auth/signup`: Register a new user.
- **GET** `/api/v1/users`: Search users by query parameters (used for searching).
- **GET** `/api/v1/users/{username}`: Fetch user by username.
- **DELETE** `/api/v1/users/{username}`: Delete a user.
- **POST** `/follow/{userId}`: Follow a user.
- **POST** `/unfollow/{userId}`: Unfollow a user.

#### **Post Management**:

- **GET** `/api/v1/posts`: Fetch all posts with pagination.
- **GET** `/api/v1/posts/{username}`: Fetch posts of a specific user.
- **POST** `/api/v1/posts`: Create a new post.
- **PUT** `/api/v1/posts/post/{postId}`: Edit an existing post.
- **DELETE** `/api/v1/posts/post/{postId}`: Delete a post.

#### **Comment Management**:

- **GET** `/api/v1/posts/comments/{postId}`: Fetch all comments for a specific post.
- **POST** `/api/v1/posts/add-comment/{postId}`: Add a comment to a post.
- **PUT** `/api/v1/posts/edit-comment/{postId}`: Edit a comment.
- **DELETE** `/api/v1/posts/remove-comment/{postId}`: Delete a comment.

#### **Likes Management**:

- **POST** `/api/v1/posts/like-post/{postId}`: Like a post.
- **DELETE** `/api/v1/posts/unlike-post/{postId}`: Unlike a post.

#### **File Uploads**:

- **GET** `/api/uploads/images/{imageName}`: Fetch images uploaded to the server.

### Security:

- The backend uses **JWT** for securing endpoints, ensuring that only authenticated users can access certain routes.
- Role-based access control ensures that only authorized users can perform specific actions like deleting posts or managing other users.

### Technologies Used:

- **Spring Boot**: For building RESTful APIs.
- **JWT**: For user authentication and authorization.
- **MySQL** (or replaceable with any relational DB): Stores user and post data.
- **Spring Security**: Secures endpoints and provides role-based access control.
- **JPA (Java Persistence API)**: For database interaction.
- **Lombok**: For reducing boilerplate code.

### Setup Instructions:

1. **Clone the repository:**

   ```bash
   git clone https://github.com/Plavsic01/Instagram-Clone-Backend.git
   ```

2. **Run Docker Compose:**

   ```bash
   docker-compose up
   ```

3. **Run Application:**

   ```bash
   mvn spring-boot:run
   ```
