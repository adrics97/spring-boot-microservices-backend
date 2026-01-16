package com.example.users_api.controller;

import com.example.users_api.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false) // desactiva security solo en tests
@ActiveProfiles("dev") // usa H2
class UserControllerIT {

    @Autowired MockMvc mockMvc;
    @Autowired UserRepository userRepository;

    @BeforeEach
    void cleanDb() {
        userRepository.deleteAll();
    }

    @Test
    void createUser_returns201AndBody() throws Exception {
        String body = """
            {
              "name": "pepe",
              "email": "pepe@example.com"
            }
            """;

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                // si tu controller devuelve 200 en vez de 201, cambia a isOk()
                .andExpect(status().isCreated())
                // ajusta estos jsonPath a tu UserResponse real
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.email").value("pepe@example.com"));
    }
}
