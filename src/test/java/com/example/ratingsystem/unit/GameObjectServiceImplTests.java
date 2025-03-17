package com.example.ratingsystem.unit;

import com.example.ratingsystem.domain.entities.Game;
import com.example.ratingsystem.domain.entities.GameObject;
import com.example.ratingsystem.domain.entities.User;
import com.example.ratingsystem.domain.entities.UserInfo;
import com.example.ratingsystem.domain.enums.UserRole;
import com.example.ratingsystem.repository.GameObjectRepository;
import com.example.ratingsystem.service.game.GameObjectServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class GameObjectServiceImplTests {
    @Mock
    private GameObjectRepository objectRepository;
    @InjectMocks
    private GameObjectServiceImpl objectService;
    private GameObject object;
    private User user;

    @BeforeEach
    void setUp() {
        var userDetails = new UserInfo(
                UUID.fromString("b6fcda68-2d03-4a9b-a799-1d20ec9f30e9"), "FirstName",
                "LastName", "user1@gmail.com", "user1", UserRole.ROLE_SELLER);
        user = new User(userDetails);

        var game = new Game("Game", "Game description");
        game.setId(UUID.randomUUID());

        object = new GameObject("Game object", "Health +5, protection +10", user, game);
    }

    @Test
    void testCreateObject() {
        var savedObject = new GameObject("Game object", "Health +5, protection +10",
                object.getUser(), object.getGame());
        savedObject.setId(UUID.randomUUID());
        when(objectRepository.save(object)).thenReturn(savedObject);

        var result = objectService.create(object);
        assertNotNull(result);
        assertThat(result.getId()).isEqualTo(savedObject.getId());
        verify(objectRepository, times(1)).save(object);
    }

    @Test
    void testGetAllByUserId() {
        Mockito.when(objectRepository.findByUserId(user.getId())).thenReturn(List.of(object));

        var userObjects = objectService.getByUserId(user.getId());

        assertNotNull(userObjects);
        verify(objectRepository, times(1)).findByUserId(user.getId());
    }
}