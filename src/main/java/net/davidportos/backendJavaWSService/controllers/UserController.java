package net.davidportos.backendJavaWSService.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.davidportos.backendJavaWSService.dto.UserDTO;
import net.davidportos.backendJavaWSService.models.requests.UserDetailRequestModel;
import net.davidportos.backendJavaWSService.models.responses.SnippetPostRestResponse;
import net.davidportos.backendJavaWSService.models.responses.UserRestResponse;
import net.davidportos.backendJavaWSService.services.UserServiceInterface;
import net.davidportos.backendJavaWSService.dto.SnippetPostDTO;

@RestController
@RequestMapping("/users") //localhost:8080/users
public class UserController {

    @Autowired
    UserServiceInterface userService;

    @Autowired
    ModelMapper mapper;

    
  
    @GetMapping(produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}) //Soporte para envio datos en XML y JSON
    public UserRestResponse getUser() {
       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

       String email = authentication.getPrincipal().toString();

       UserDTO userDTO = userService.getUser(email);

      UserRestResponse userRestResponse = mapper.map(userDTO, UserRestResponse.class);
       
       return userRestResponse;
    }

    @PostMapping(produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public UserRestResponse createUser(@RequestBody @Valid UserDetailRequestModel userDetails) {
       
        UserRestResponse userToReturn = new UserRestResponse();
        
        UserDTO userDTO = new UserDTO();

        BeanUtils.copyProperties(userToReturn, userDTO);

        UserDTO createdUserDTO = userService.createUser(userDTO);

        BeanUtils.copyProperties(createdUserDTO, userToReturn);

        return userToReturn;

    }

    @GetMapping(path = "/posts") //localhost:8080/users/posts
    public List<SnippetPostRestResponse> getPosts() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getPrincipal().toString();

        List<SnippetPostDTO> posts = userService.getUserPosts(email);

        List<SnippetPostRestResponse> returnPosts = new ArrayList<>();

        //convertir lista postDto a postRest
        for (SnippetPostDTO post : posts) {
            SnippetPostRestResponse postRest = mapper.map(post, SnippetPostRestResponse.class);
            if(postRest.getExpirationDate().compareTo(new Date(System.currentTimeMillis())) < 0) {
                postRest.setExpiredPost(true);
            }
            returnPosts.add(postRest);
        }

        return returnPosts;
    }


}
