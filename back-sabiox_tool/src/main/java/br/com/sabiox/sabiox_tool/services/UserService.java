package br.com.sabiox.sabiox_tool.services;

import br.com.sabiox.sabiox_tool.model.User;
import br.com.sabiox.sabiox_tool.repository.UserRepository;
import br.com.sabiox.sabiox_tool.util.dtos.request.UserRequestDTO;
import br.com.sabiox.sabiox_tool.util.dtos.response.UserResponseDTO;
import br.com.sabiox.sabiox_tool.util.mappers.UserMapper;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponseDTO create(UserRequestDTO userRequestDTO) { 
        User user = new User();
        user.setActive(true);

        BeanUtils.copyProperties(userRequestDTO, user);
        User userSaved = userRepository.save(user);

        return UserMapper.toDto(userSaved);
    }

    public UserResponseDTO read(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found."));
       
        return UserMapper.toDto(user);
    }

    public List<UserResponseDTO> readAll() {
        List<User> users = userRepository.findAll();
        return UserMapper.toDtoList(users);
    }

    public List<UserResponseDTO> readAllAtivos() {
        List<User> users = userRepository.findAll()
                .stream().filter(User::isActive)
                .toList();

        return UserMapper.toDtoList(users);
    }

    public UserResponseDTO update(Long id, UserRequestDTO userRequestDTO) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found."));
        
        BeanUtils.copyProperties(userRequestDTO, user);
        User userUpdated = userRepository.save(user);
        return UserMapper.toDto(userUpdated);
    }

    public void delete(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found."));

        user.setActive(false);
        userRepository.save(user);
        
        // userRepository.delete(user);
    }
}