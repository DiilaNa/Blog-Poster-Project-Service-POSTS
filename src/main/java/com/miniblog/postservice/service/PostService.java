package com.miniblog.postservice.service;

import com.miniblog.postservice.dto.PostRequestDTO;
import com.miniblog.postservice.dto.PostResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {
    PostResponseDTO createPost(PostRequestDTO requestDTO, MultipartFile coverImage);
    List<PostResponseDTO> getAllPosts();
    PostResponseDTO getPostById(Long id);
    void deletePost(Long id);
}
