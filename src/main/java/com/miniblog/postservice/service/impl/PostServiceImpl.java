package com.miniblog.postservice.service.impl;

import com.miniblog.postservice.dto.PostRequestDTO;
import com.miniblog.postservice.dto.PostResponseDTO;
import com.miniblog.postservice.exception.PostNotFoundException;
import com.miniblog.postservice.mapper.PostMapper;
import com.miniblog.postservice.model.Post;
import com.miniblog.postservice.repository.PostRepository;
import com.miniblog.postservice.service.PostService;
import com.miniblog.postservice.service.StorageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final StorageService storageService;
    private final PostMapper postMapper;

    public PostServiceImpl(PostRepository postRepository, StorageService storageService, PostMapper postMapper) {
        this.postRepository = postRepository;
        this.storageService = storageService;
        this.postMapper = postMapper;
    }

    @Override
    public PostResponseDTO createPost(PostRequestDTO requestDTO, MultipartFile coverImage) {
        Post post = postMapper.toEntity(requestDTO);

        if (coverImage != null && !coverImage.isEmpty()) {
            String imageUrl = storageService.uploadFile(coverImage);
            post.setCoverImageUrl(imageUrl);
        }

        Post savedPost = postRepository.save(post);
        return postMapper.toResponseDTO(savedPost);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostResponseDTO> getAllPosts() {
        return postRepository.findAll().stream()
                .map(postMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public PostResponseDTO getPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("Post not found with ID: " + id));
        return postMapper.toResponseDTO(post);
    }

    @Override
    public void deletePost(Long id) {
        if (!postRepository.existsById(id)) {
            throw new PostNotFoundException("Post not found with ID: " + id);
        }
        postRepository.deleteById(id);
    }
}
