package com.springApp.Jpa;

import com.springApp.Jpa.entity.User;
import com.springApp.Jpa.repository.UserJpaRepository;
import com.springApp.Jpa.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserJpaRepository jpaRepository;

    @InjectMocks
    private UserService userService;

    private User testUser;

    @BeforeEach
    void setUp(){
        testUser = new User();
        testUser.setId(1L);
        testUser.setFirstName("Monica");
        testUser.setAge(27);
        testUser.setUsername("marinescum");
        testUser.setEmail("marinescumonica@yahoo.com");
    }

    @Test
    void saveAll_ShouldReturnSavedUsers(){
        // given
        List<User> users = Arrays.asList(testUser);

        //when
        when(jpaRepository.saveAll(users))
                .thenReturn(users);

        Iterable<User> saveUsers = userService.saveAll(users);

        //then
        assertNotNull(saveUsers);
        verify(jpaRepository).saveAll(users);
    }

    @Test
    void findAllUsers_ShouldReturnAllUsers(){
        List<User> users = Arrays.asList(testUser);

        when(jpaRepository.findAll())
                .thenReturn(users);

        Iterable<User> foundUsers = userService.findAllUsers();

        assertNotNull(foundUsers);
        verify(jpaRepository).findAll();
    }

    @Test
    void deleteUser_ShouldDeleteUserById(){
        Long userId = 1L;

        doNothing().when(jpaRepository).deleteById(userId);

        userService.deleteUser(userId);
        verify(jpaRepository).deleteById(userId);
    }

    @Test
    void findUserByUsername_ShouldReturnUser_WhenUserExists(){
        when(jpaRepository.findByUsername("marinescum"))
                .thenReturn(Optional.of(testUser));

        Optional<User> foundUser = userService.findByUsername("marinescum");

        assertTrue(foundUser.isPresent());
        assertEquals("marinescum", foundUser.get().getUsername());

        verify(jpaRepository).findByUsername("marinescum");
    }

    @Test
    void findUserByUsername_ShouldReturnUser_WhenUserNotFound(){
        when(jpaRepository.findByUsername("unknown"))
                .thenReturn(Optional.empty());
        Optional<User> notFound = userService.findByUsername("unknown");
        assertFalse(notFound.isPresent());
        verify(jpaRepository).findByUsername("unknown");
    }
}
