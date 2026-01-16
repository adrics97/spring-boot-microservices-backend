package com.example.users_api.service;

import com.example.users_api.dto.UserRequest;
import com.example.users_api.model.User;
import com.example.users_api.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void getById_whenExists_returnsUser() {
        User u = new User();
        u.setId(1L);
        u.setName("pepe");
        u.setEmail("pepe@example.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(u));

        User result = userService.getById(1L);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getEmail()).isEqualTo("pepe@example.com");
        verify(userRepository).findById(1L);
    }

    @Test
    void create_savesUser() {
        UserRequest toSave = new UserRequest();
        toSave.setName("ana");
        toSave.setEmail("ana@example.com");

        when(userRepository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        User saved = userService.create(toSave);

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(captor.capture());
        assertThat(captor.getValue().getEmail()).isEqualTo("ana@example.com");
        assertThat(saved.getName()).isEqualTo("ana");
    }
}
