package com.leverx.dealerstat.unit;

import com.leverx.dealerstat.dto.UserDTO;
import com.leverx.dealerstat.dto.converter.UserConverter;
import com.leverx.dealerstat.entity.User;
import com.leverx.dealerstat.exception.AlreadyExistsException;
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
    public void Given_ServiceTriesToFindUser_When_GetUserId_Then_FOundUserIsReturned() {
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
    public void Given_ServiceTriesToFindAllUsers_When_GetNoOneField_Then_FoundUsersIsReturned() {
        List<User> users = new ArrayList<>();
        List<UserDTO> userDTOS = new ArrayList<>();
        User user = new User();
        UserDTO userDTO = new UserDTO();
        users.add(user);
        users.add(user);

        userDTOS.add(userDTO);
        userDTOS.add(userDTO);

        Mockito.when(userRepository.findAll()).thenReturn(users);
        Mockito.when(userConverter.convertToDTO(user)).thenReturn(userDTO);

        assertEquals(userService.findAll(), userDTOS);

        Mockito.verify(userRepository, Mockito.times(1)).findAll();
        Mockito.verify(userConverter, Mockito.times(2)).convertToDTO(user);
    }

    @Test
    public void Given_ServiceTriesToFindUser_When_GetUserEmail_Then_FoundUserIsReturned() {
        String email = "vadimbealev002@gmail.com";
        User user = new User();
        UserDTO userDTO = new UserDTO();
        user.setEmail(email);
        userDTO.setEmail(email);

        Mockito.when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        Mockito.when(userConverter.convertToDTO(user)).thenReturn(userDTO);

        assertEquals(userService.findByEmail(email), userDTO);

        Mockito.verify(userRepository, Mockito.times(1)).findByEmail(email);
        Mockito.verify(userConverter, Mockito.times(1)).convertToDTO(user);
    }

    @Test
    public void Given_ServiceTriesToFindUserById_When_UserIsNotFound_Then_ThrowException() {
        User user = new User();
        UserDTO userDTO = new UserDTO();
        Long id = 2L;
        userDTO.setId(id);

        Mockito.when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.findById(id));

        Mockito.verify(userRepository, Mockito.times(1)).findById(id);
        Mockito.verify(userConverter, Mockito.never()).convertToDTO(user);
    }

    @Test
    public void Given_ServiceTriesToUpdateUser_When_UserIsNotFound_Then_ThrowException() {
        String email = "vadimbelaev002@gmail.com";
        UserDTO userDTO = new UserDTO();
        User user = new User();

        Mockito.when(userRepository.existsByEmail(email)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> userService.update(userDTO));

        Mockito.verify(userRepository, Mockito.times(1)).existsByEmail(email);
        Mockito.verify(userConverter, Mockito.never()).convertToDTO(user);
    }

    @Test
    public void Given_ServiceTriesToDeleteUserById_When_GetUserId_Then_DeleteUser() {
        String email = "vadimbelaev002@gmail.com";
        UserDTO userDTO = new UserDTO();
        User user = new User();

        Mockito.when(userRepository.existsByEmail(email)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> userService.update(userDTO));

        Mockito.verify(userRepository, Mockito.times(1)).existsByEmail(email);
        Mockito.verify(userConverter, Mockito.never()).convertToDTO(user);
    }

}

/*
    List<UserDTO> findAll();

    UserDTO findById(Long id);

    UserDTO findByEmail(String email);

    UserDTO update(UserDTO userDTO);

    void delete(Long id);
 */