package com.leverx.dealerstat.unit;

import com.leverx.dealerstat.dto.UserDTO;
import com.leverx.dealerstat.dto.converter.UserConverter;
import com.leverx.dealerstat.entity.User;
import com.leverx.dealerstat.exception.NotFoundException;
import com.leverx.dealerstat.repository.UserRepository;
import com.leverx.dealerstat.service.impl.UserServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("UserService test")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
public class UserServiceUnitTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserConverter userConverter;

    @Mock
    private UserRepository userRepository;

    @Test
    public void Given_ServiceTriesToFindUser_When_GetUserId_Then_UserIsReturned() {
        Long id = 1L;
        String email = "vadimbelaev002@gmail.com";
        User user = new User();
        UserDTO userDTO = new UserDTO();
        user.setId(id);
        user.setEmail(email);
        userDTO.setId(id);
        userDTO.setEmail(email);

        Mockito.when(userRepository.findById(id)).thenReturn(Optional.of(user));

        Mockito.when(userConverter.convertToDTO(user)).thenReturn(userDTO);

        assertEquals(userService.findById(id), userDTO);

        Mockito.verify(userRepository, Mockito.times(1)).findById(id);
        Mockito.verify(userConverter, Mockito.times(1)).convertToDTO(user);
    }

    @Test
    public void Given_ServiceTriesToFindAllUsers_When_GetNoOneField_Then_UsersIsReturned() {
        List<User> users = new ArrayList<>();
        users.add(new User());

        Mockito.when(userRepository.findAll()).thenReturn(users);

        assertNotNull(userService.findAll());
    }

    @Test
    public void Given_ServiceTriesToFindUser_When_GetUserEmail_Then_UserIsReturned() {
        String email = "vadimbealev002@gmail.com";
        User user = new User();
        user.setEmail(email);

        Mockito.when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        assertEquals(userService.findByEmail(email).getEmail(), user.getEmail());
    }

    @Test
    public void Given_ServiceTriesToSaveUser_When_UserIsAlreadyExists_Then_ThrowException() {
        String email = "vadimbelaev002@gmail.com";
        User user = new User();
        user.setEmail(email);
        Mockito.when(userRepository.existsByEmail(email)).thenReturn(true);
       // assertThrows(AlreadyExistsException.class, () -> usersService.save(user));
    }


    @Test
    public void Given_ServiceTriesToFindUser_When_UserIsNotFound_Then_ThrowException() {
        User user = new User();
        Long id = 1L;
        user.setId(id);

        Mockito.when(userRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.findById(2L));
    }

    @Test
    public void Given_ServiceTriesToUpdateUser_When_UserIsNotFound_Then_ThrowException() {
        UserDTO user = new UserDTO();
        Long id = 1L;

        Mockito.when(userRepository.findById(2L)).thenReturn(Optional.empty());

    //    assertThrows(NotFoundException.class, () -> usersService.update(2L));
    }

}

/*
    List<UserDTO> findAll();

    UserDTO findById(Long id);

    UserDTO findByEmail(String email);

    UserDTO update(UserDTO userDTO);

    void delete(Long id);
 */