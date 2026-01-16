package com.example.users_api.service;

import com.example.users_api.dto.UserRequest;
import com.example.users_api.dto.UserUpdateRequest;
import com.example.users_api.exception.EmailAlreadyExistsException;
import com.example.users_api.model.User;
import com.example.users_api.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import com.example.users_api.exception.UserNotFoundException;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public Page<User> getAll(Pageable pageable){
        return userRepository.findAll(pageable);
    }

    public User getById(Long id) {
        return  userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

    }


    public User create (UserRequest request){

        log.info("Creating user with email={}", request.getEmail());

        if (userRepository.existsByEmail(request.getEmail())) {
            log.warn("Cannot create user. Email already exists: {}", request.getEmail());
            throw new EmailAlreadyExistsException("Email already exists");
        }
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        User saved = userRepository.save(user);
        log.info("User created with id={}", saved.getId());
        return saved;
    }

    public void deleteById(Long id){
        log.info("Deleting user id={}", id);
        if (!userRepository.existsById(id)){
            log.warn("Cannot delete. User not found id={}", id);
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
        log.info("User deleted id={}", id);
    }

    public User update(Long id, UserUpdateRequest request){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        if (userRepository.existsByEmailAndIdNot(request.getEmail(), id)){
            throw  new EmailAlreadyExistsException("Email already exist");
        }

        user.setName(request.getName());
        user.setEmail(request.getEmail());

        return userRepository.save(user);
    }

    public List<User> searchByEmail(String email) {
        return userRepository.findByEmailContainingIgnoreCase(email);
    }

    public List<User> searchAdvanced(String email, String name) {
        return userRepository.searchByEmailAndName(email, name);
    }


}
