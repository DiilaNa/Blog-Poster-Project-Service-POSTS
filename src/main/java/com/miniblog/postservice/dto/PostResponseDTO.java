package com.miniblog.postservice.dto;

import java.time.LocalDateTime;

public class PostResponseDTO {
    private String id;
    private String title;
    private String content;
    private Long authorId;
    private String coverImageUrl;
    private LocalDateTime createdAt;

    // Constructors
    public PostResponseDTO() {}

    public PostResponseDTO(String id, String title, String content, Long authorId, String coverImageUrl, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.authorId = authorId;
        this.coverImageUrl = coverImageUrl;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Long getAuthorId() { return authorId; }
    public void setAuthorId(Long authorId) { this.authorId = authorId; }

    public String getCoverImageUrl() { return coverImageUrl; }
    public void setCoverImageUrl(String coverImageUrl) { this.coverImageUrl = coverImageUrl; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
