package com.miniblog.postservice.mapper;

import com.miniblog.postservice.dto.PostRequestDTO;
import com.miniblog.postservice.dto.PostResponseDTO;
import com.miniblog.postservice.model.Post;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {

    public Post toEntity(PostRequestDTO requestDTO) {
        if (requestDTO == null) {
            return null;
        }
        Post post = new Post();
        post.setTitle(requestDTO.getTitle());
        post.setContent(requestDTO.getContent());
        post.setAuthorId(requestDTO.getAuthorId());
        return post;
    }

    public PostResponseDTO toResponseDTO(Post post) {
        if (post == null) {
            return null;
        }
        PostResponseDTO responseDTO = new PostResponseDTO();
        responseDTO.setId(post.getId());
        responseDTO.setTitle(post.getTitle());
        responseDTO.setContent(post.getContent());
        responseDTO.setAuthorId(post.getAuthorId());
        responseDTO.setCoverImageUrl(post.getCoverImageUrl());
        responseDTO.setCreatedAt(post.getCreatedAt());
        return responseDTO;
    }
}
