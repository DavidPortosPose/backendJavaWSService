package net.davidportos.backendJavaWSService.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.davidportos.backendJavaWSService.dto.PostCreationDTO;
import net.davidportos.backendJavaWSService.dto.SnippetPostDTO;
import net.davidportos.backendJavaWSService.entities.ExposureEntity;
import net.davidportos.backendJavaWSService.entities.SnippetPostEntity;
import net.davidportos.backendJavaWSService.entities.UserEntity;
import net.davidportos.backendJavaWSService.repositories.ExposureRepository;
import net.davidportos.backendJavaWSService.repositories.PostRepository;
import net.davidportos.backendJavaWSService.repositories.UserRepository;
import net.davidportos.backendJavaWSService.utils.Exposures;

@Service
public class PostService implements PostServiceInterface{

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ExposureRepository exposureRepository;

    @Autowired
    ModelMapper mapper;



    @Override
    public SnippetPostDTO createPost(PostCreationDTO postCreationDTO) {

        UserEntity userEntity = userRepository.findByEmail(postCreationDTO.getUserEmail());
        ExposureEntity exposureEntity = exposureRepository.findById(postCreationDTO.getExposureId());

        SnippetPostEntity snippetPostEntity = new SnippetPostEntity();
        snippetPostEntity.setUser(userEntity);
        snippetPostEntity.setExposure(exposureEntity);
        snippetPostEntity.setTitle(snippetPostEntity.getTitle());
        snippetPostEntity.setContent(snippetPostEntity.getContent());
        snippetPostEntity.setPostId(UUID.randomUUID().toString());
        snippetPostEntity.setExpirationDate(new Date(System.currentTimeMillis()
         + (postCreationDTO.getExpirationTime() * 60000)));
        snippetPostEntity.setTitle(snippetPostEntity.getTitle());

        SnippetPostEntity createdPost = postRepository.save(snippetPostEntity);
        SnippetPostDTO returnPost = mapper.map(createdPost, SnippetPostDTO.class);

        
        
        return returnPost;
    }

    //convertir postEntities a postDTO
    @Override
    public List<SnippetPostDTO> getLastPosts() {

    List<SnippetPostEntity> postEntities = postRepository.getLastPublicPosts(Exposures.PUBLIC, new Date(System.currentTimeMillis()));

        List<SnippetPostDTO> postsDtos = new ArrayList<>();

        for (SnippetPostEntity snippetPostEntity : postEntities) {
            SnippetPostDTO postDto = mapper.map(snippetPostEntity, SnippetPostDTO.class);
            postsDtos.add(postDto);
        }
        
        return postsDtos;
    }

    @Override
    public SnippetPostDTO getPost(String postId) {

        SnippetPostEntity postEntity = postRepository.findByPostId(postId);
        SnippetPostDTO postDTO = mapper.map(postEntity, SnippetPostDTO.class);
        
        return postDTO;
    }

    @Override
    public void deletePost(String postId, long userId) {
        SnippetPostEntity postEntity = postRepository.findByPostId(postId);
        if(postEntity.getUser().getId() != userId) {
            throw new RuntimeException("You can´t delete this post");
        }
        postRepository.delete(postEntity);
    }

    @Override
    public SnippetPostDTO updatePost(String postId, long userId, PostCreationDTO postCreationDTO) {
        SnippetPostEntity postEntity = postRepository.findByPostId(postId);
        if(postEntity.getUser().getId() != userId) {
            throw new RuntimeException("You can´t delete this post");
        }

        ExposureEntity exposureEntity = exposureRepository.findById(postCreationDTO.getExposureId());

        postEntity.setExposure(exposureEntity);
        postEntity.setTitle(postCreationDTO.getTitle());
        postEntity.setContent(postCreationDTO.getContent());
        postEntity.setExpirationDate(new Date(System.currentTimeMillis()
         + (postCreationDTO.getExpirationTime() * 60000)));

        SnippetPostEntity updatedPost =  postRepository.save(postEntity);

        SnippetPostDTO postDTO = mapper.map(updatedPost, SnippetPostDTO.class);
         
        return postDTO;
    }
    
}
