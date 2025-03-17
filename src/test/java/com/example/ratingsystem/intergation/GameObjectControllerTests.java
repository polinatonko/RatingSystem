package com.example.ratingsystem.intergation;

import com.example.ratingsystem.domain.dtos.auth.AuthRequestDto;
import com.example.ratingsystem.domain.dtos.auth.TokenDto;
import com.example.ratingsystem.domain.dtos.gameobject.GameObjectUpdateDto;
import com.example.ratingsystem.domain.entities.Game;
import com.example.ratingsystem.domain.entities.GameObject;
import com.example.ratingsystem.domain.entities.User;
import com.example.ratingsystem.domain.entities.UserInfo;
import com.example.ratingsystem.domain.enums.UserRole;
import com.example.ratingsystem.repository.GameObjectRepository;
import com.example.ratingsystem.repository.GameRepository;
import com.example.ratingsystem.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
class GameObjectControllerTests {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GameObjectRepository objectRepository;
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    private GameObject object;

    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @BeforeEach
    void setUp() {
        clear();

        var userDetails = new UserInfo("name", "surname",
                passwordEncoder.encode("password"), "name@gmail.com", UserRole.ROLE_SELLER);
        var user = new User(userDetails);
        user.setEnabled(true);
        userRepository.save(user);

        var game = new Game("Game", "Game description");
        gameRepository.save(game);

        object = new GameObject("Game object", "Health +5, protection +10", user, game);
        objectRepository.save(object);
    }

    @Test
    void patchObjectForbidden() throws Exception {
        var dto = new GameObjectUpdateDto();

        mockMvc.perform(patch("/objects/" + object.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isForbidden());
    }

    @Test
    void authorizeAndPatchObjectSuccess() throws Exception {
        var dto = new GameObjectUpdateDto();
        dto.setTitle("updated");

        var authDto = new AuthRequestDto("name@gmail.com", "password");
        var response = mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authDto)))
                .andReturn()
                .getResponse();
        var tokenDto = objectMapper.readValue(response.getContentAsString(), TokenDto.class);

        mockMvc.perform(patch("/objects/" + object.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto))
                .header("Authorization", "Bearer " + tokenDto.token()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("updated"));
    }

    private void clear() {
        userRepository.deleteAll();
        gameRepository.deleteAll();
        objectRepository.deleteAll();
    }
}