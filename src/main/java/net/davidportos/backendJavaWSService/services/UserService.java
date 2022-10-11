package net.davidportos.backendJavaWSService.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import net.davidportos.backendJavaWSService.dto.SnippetPostDTO;
import net.davidportos.backendJavaWSService.dto.UserDTO;
import net.davidportos.backendJavaWSService.entities.SnippetPostEntity;
import net.davidportos.backendJavaWSService.entities.UserEntity;
import net.davidportos.backendJavaWSService.repositories.PostRepository;
import net.davidportos.backendJavaWSService.repositories.UserRepository;
import net.davidportos.exceptions.EmailException;

@Service
public class UserService implements UserServiceInterface {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    ModelMapper mapper;


    
    @Override
    public UserDTO createUser(UserDTO user) {

        if(userRepository.findByEmail(user.getEmail())  != null) 
            throw new EmailException("This user already exists");
       
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);

        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        
        UUID userId = UUID.randomUUID();
        userEntity.setUserId(userId.toString());


        UserEntity storedUserDetails = userRepository.save(userEntity);

        UserDTO userToReturn = new UserDTO();
        BeanUtils.copyProperties(storedUserDetails, userToReturn);


        return userToReturn;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
       UserEntity userEntity =  userRepository.findByEmail(email);

       if(userEntity == null) {
        throw new UsernameNotFoundException(email);
       }

        return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), new ArrayList<>());
    }


    @Override
    public UserDTO getUser(String email) {
        UserEntity userEntity =  userRepository.findByEmail(email);

       if(userEntity == null) {
        throw new UsernameNotFoundException(email);
       }

       UserDTO returnUser = new UserDTO();

       BeanUtils.copyProperties(userEntity, returnUser);

        return returnUser;
    }


    @Override
    public List<SnippetPostDTO> getUserPosts(String email) {
       UserEntity userEntity = userRepository.findByEmail(email);

       List<SnippetPostEntity> posts = postRepository.getByUserIdOrderByCreatedDateDesc(userEntity.getId());

       List<SnippetPostDTO> postDTOs = new ArrayList<>();

       for (SnippetPostEntity post : posts) {
            SnippetPostDTO postDTO = mapper.map(post, SnippetPostDTO.class);
            postDTOs.add(postDTO);
       }
        return postDTOs;
    }
    
}
