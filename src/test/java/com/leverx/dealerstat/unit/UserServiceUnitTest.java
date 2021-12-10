package com.leverx.dealerstat.unit;

import com.leverx.dealerstat.dto.UserDTO;
import com.leverx.dealerstat.dto.converter.UserConverter;
import com.leverx.dealerstat.entity.User;
import com.leverx.dealerstat.exception.AlreadyExistsException;
import com.leverx.dealerstat.exception.NotFoundException;
import com.leverx.dealerstat.repository.UserRepository;
import com.leverx.dealerstat.service.impl.UserServiceImpl;
import org.junit.jupiter.api.*;
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
    public void Given_ServiceTriesToFindUser_When_GetUserId_Then_FoundUserIsReturned() {
        Long id = 1L;
        String email = "vadimbelaev002@gmail.com";
        User user = new User();
        UserDTO userDTO = new UserDTO();
        user.setId(id);
        user.setEmail(email);

        Mockito.when(userRepository.findById(id)).thenReturn(Optional.of(user));

        Mockito.when(userConverter.convertToDTO(user)).thenReturn(userDTO);

        assertEquals(userService.getById(id), userDTO);

        Mockito.verify(userRepository, Mockito.only()).findById(id);
        Mockito.verify(userConverter, Mockito.only()).convertToDTO(user);
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

        assertEquals(userService.getAll(), userDTOS);

        Mockito.verify(userRepository, Mockito.only()).findAll();
        Mockito.verify(userConverter, Mockito.times(2)).convertToDTO(user);
    }

    @Test
    public void Given_ServiceTriesToFindUserByEmail_When_GetUser_Then_FoundUserIsReturned() {
        String email = "vadimbealev002@gmail.com";
        User user = new User();
        UserDTO userDTO = new UserDTO();
        user.setEmail(email);
        userDTO.setEmail(email);

        Mockito.when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        Mockito.when(userConverter.convertToDTO(user)).thenReturn(userDTO);

        assertEquals(userService.getByEmail(email), userDTO);

        Mockito.verify(userRepository, Mockito.only()).findByEmail(email);
        Mockito.verify(userConverter, Mockito.only()).convertToDTO(user);
    }

    @Test
    public void Given_ServiceTriesToFindUserById_When_UserIsNotFound_Then_ThrowException() {
        User user = new User();
        UserDTO userDTO = new UserDTO();
        Long id = 2L;
        userDTO.setId(id);

        Mockito.when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.getById(id));

        Mockito.verify(userRepository, Mockito.only()).findById(id);
        Mockito.verify(userConverter, Mockito.never()).convertToDTO(user);
    }

    @Test
    public void Given_ServiceTriesToUpdateUser_When_UserIsNotFound_Then_ThrowException() {
        Long id = 1L;
        UserDTO userDTO = new UserDTO();
        userDTO.setId(id);
        User user = new User();
        user.setId(id);

        Mockito.when(userRepository.existsById(id)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> userService.update(userDTO));

        Mockito.verify(userRepository, Mockito.only()).existsById(id);
        Mockito.verify(userConverter, Mockito.never()).convertToDTO(user);
    }

    @Test
    public void Given_ServiceTriesToDeleteUserById_When_UserIsNotFound_Then_ThrowException() {
        Long id = 1L;

        Mockito.when(userRepository.existsById(id)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> userService.delete(id));

        Mockito.verify(userRepository, Mockito.only()).existsById(id);
        Mockito.verify(userRepository, Mockito.never()).deleteById(id);
    }

    @Test
    public void Given_ServiceTriesUpdateUser_When_GetUser_Then_UpdatedUserReturned() {
        Long id = 1L;
        User user = new User();
        UserDTO userDTO = new UserDTO();
        user.setId(id);
        userDTO.setId(id);

        Mockito.when(userRepository.existsById(id)).thenReturn(true);
        Mockito.when(userConverter.convertToDTO(user)).thenReturn(userDTO);
        Mockito.when(userConverter.convertToModel(userDTO)).thenReturn(user);
        Mockito.when(userRepository.save(user)).thenReturn(user);

        assertEquals(userService.update(userDTO), userDTO);

        Mockito.verify(userRepository, Mockito.times(1)).existsById(id);
        Mockito.verify(userConverter, Mockito.times(1)).convertToDTO(user);
        Mockito.verify(userRepository, Mockito.times(1)).save(user);
        Mockito.verify(userConverter, Mockito.times(1)).convertToModel(userDTO);

    }
}

/*

 */