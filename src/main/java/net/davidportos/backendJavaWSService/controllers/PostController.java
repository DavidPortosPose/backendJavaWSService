package net.davidportos.backendJavaWSService.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.davidportos.backendJavaWSService.dto.PostCreationDTO;
import net.davidportos.backendJavaWSService.dto.SnippetPostDTO;
import net.davidportos.backendJavaWSService.dto.UserDTO;
import net.davidportos.backendJavaWSService.models.requests.PostCreateRequestModel;
import net.davidportos.backendJavaWSService.models.responses.OperationStatusModel;
import net.davidportos.backendJavaWSService.models.responses.SnippetPostRestResponse;
import net.davidportos.backendJavaWSService.services.PostServiceInterface;
import net.davidportos.backendJavaWSService.services.UserServiceInterface;
import net.davidportos.backendJavaWSService.utils.Exposures;


@RestController
@RequestMapping("/posts")
public class PostController  {

    @Autowired
    PostServiceInterface postService;

    @Autowired
    UserServiceInterface userService;

    @Autowired
    ModelMapper mapper;

   
    
    @PostMapping
    public SnippetPostRestResponse createPost(@RequestBody @Valid PostCreateRequestModel createRequestModel) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getPrincipal().toString();

        PostCreationDTO postCreationDTO = mapper.map(createRequestModel, PostCreationDTO.class);

        postCreationDTO.setUserEmail(email);

       SnippetPostDTO postDTO = postService.createPost(postCreationDTO);

        SnippetPostRestResponse returnPost = mapper.map(postDTO, SnippetPostRestResponse.class);

        return returnPost;

    }

    @GetMapping(path= "/last") //localhost:8080/posts/last
    public List<SnippetPostRestResponse> lastPosts() {
        List<SnippetPostDTO> posts = postService.getLastPosts();

        List<SnippetPostRestResponse> returnPosts = new ArrayList<>();

        //convertir lista postDto a postRest
        for (SnippetPostDTO post : posts) {
            SnippetPostRestResponse postRest = mapper.map(post, SnippetPostRestResponse.class);
            returnPosts.add(postRest);
        }

        return returnPosts;
        
    }

    @GetMapping(path = "/{id}") //localhost:8080/posts/uuid
    public SnippetPostRestResponse getPost(@PathVariable String id) {
        SnippetPostDTO postDTO = postService.getPost(id);

        SnippetPostRestResponse postRestResponse = mapper.map(postDTO, SnippetPostRestResponse.class);

        
        //validar si el post es privado o si el post ya expiro
        if(postRestResponse.getExposureRestResponse().getId() == Exposures.PRIVATE || postRestResponse.getExpiredPost()) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            UserDTO user = userService.getUser(authentication.getPrincipal().toString());

            if(user.getId() != postDTO.getUser().getId()) {
                throw new RuntimeException("No have permises to realize this action");
            }
        }

        return postRestResponse;
    }

    @DeleteMapping(path="/{id}")
    public OperationStatusModel deletePost(@PathVariable String id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserDTO user = userService.getUser(authentication.getPrincipal().toString());

        OperationStatusModel operationStatusModel = new OperationStatusModel();
        operationStatusModel.setName("DELETE");

        postService.deletePost(id, user.getId());
        operationStatusModel.setResult("SUCESS");

        return operationStatusModel;

    }

    @PutMapping(path="/{id}")
    public SnippetPostRestResponse updatePost(@RequestBody @Valid PostCreateRequestModel postCreateRequestModel, @PathVariable String id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserDTO user = userService.getUser(authentication.getPrincipal().toString());

        PostCreationDTO postUpdateDTO = mapper.map(postCreateRequestModel, PostCreationDTO.class);

        SnippetPostDTO postDTO =  postService.updatePost(id, user.getId(), postUpdateDTO);

        SnippetPostRestResponse updatedPost = mapper.map(postDTO, SnippetPostRestResponse.class);
       

        return updatedPost;

    }
    
}
