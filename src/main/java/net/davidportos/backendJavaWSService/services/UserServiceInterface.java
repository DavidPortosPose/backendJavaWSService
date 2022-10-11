package net.davidportos.backendJavaWSService.services;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import net.davidportos.backendJavaWSService.dto.SnippetPostDTO;
import net.davidportos.backendJavaWSService.dto.UserDTO;

public interface UserServiceInterface extends UserDetailsService {

    public UserDTO createUser(UserDTO user);
    
    public UserDTO getUser(String email);

    public List<SnippetPostDTO> getUserPosts(String email);

    
}
