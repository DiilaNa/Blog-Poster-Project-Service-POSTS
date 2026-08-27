package com.miniblog.postservice.dto;

public class PostRequestDTO {
    private String title;
    private String content;
    private Long authorId;

    // Constructors
    public PostRequestDTO() {}

    public PostRequestDTO(String title, String content, Long authorId) {
        this.title = title;
        this.content = content;
        this.authorId = authorId;
    }

    // Getters and Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Long getAuthorId() { return authorId; }
    public void setAuthorId(Long authorId) { this.authorId = authorId; }
}
