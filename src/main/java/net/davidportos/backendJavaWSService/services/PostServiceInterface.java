package net.davidportos.backendJavaWSService.services;

import java.util.List;

import net.davidportos.backendJavaWSService.dto.PostCreationDTO;
import net.davidportos.backendJavaWSService.dto.SnippetPostDTO;

public interface PostServiceInterface {

    public SnippetPostDTO createPost(PostCreationDTO postCreationDTO);
    
    public List<SnippetPostDTO> getLastPosts();
    
    public SnippetPostDTO getPost(String postId);

    public void deletePost(String postId, long userId);

    public SnippetPostDTO updatePost(String postId, long userId, PostCreationDTO postCreationDTO);


    
}
