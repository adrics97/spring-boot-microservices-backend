package com.example.users_api.controller;

import com.example.users_api.dto.UserRequest;
import com.example.users_api.dto.UserResponse;
import com.example.users_api.dto.UserUpdateRequest;
import com.example.users_api.model.User;
import com.example.users_api.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "Users", description = "Operations related to users")
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @Operation(summary = "Get all users")
    @GetMapping
    public Page<User> getAllUsers(@ParameterObject Pageable pageable) {
        return userService.getAll(pageable);
    }

    @GetMapping("/{id}")
    public UserResponse getById(@PathVariable Long id) {
        User u = userService.getById(id);
        return new UserResponse(u.getId(), u.getName(), u.getEmail());
    }


    @Operation(summary = "Create a new user")
    @PostMapping
    public User createUser(@Valid @RequestBody UserRequest request){
        return userService.create(request);
    }

    @Operation(summary = "Delete a user by id")
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
    }

    @Operation(summary = "Update a user by id")
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @Valid @RequestBody UserUpdateRequest request) {
        return userService.update(id, request);
    }

    @GetMapping("/search")
    public List<User> search(@RequestParam String email){
        return userService.searchByEmail(email);
    }

    @GetMapping("/search/advanced")
    public List<User> advancedSearch(
            @RequestParam String email,
            @RequestParam String name
    ) {
        return userService.searchAdvanced(email, name);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/ping")
    public String adminPing() {
        return "admin ok";
    }


}
