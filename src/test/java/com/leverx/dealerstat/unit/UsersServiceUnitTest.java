package com.leverx.dealerstat.unit;

import com.leverx.dealerstat.exception.AlreadyExistsException;
import com.leverx.dealerstat.exception.NotFoundException;
import com.leverx.dealerstat.model.Comment;
import com.leverx.dealerstat.model.Role;
import com.leverx.dealerstat.model.User;
import com.leverx.dealerstat.repository.UsersRepository;
import com.leverx.dealerstat.service.impl.UsersServiceImpl;
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

@ExtendWith(MockitoExtension.class)
public class UsersServiceUnitTest {

    @InjectMocks
    private UsersServiceImpl usersService;

    @Mock
    private UsersRepository usersRepository;


    @Test
    public void shouldReturnUserById() {
        User user = new User();
        user.setId(7L);
        Mockito.when(usersRepository.findById(7L)).thenReturn(Optional.of(user));

        assertNotNull(usersService.findById(7L));
    }

    @Test
    public void shouldReturnAllUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User());

        Mockito.when(usersRepository.findAll()).thenReturn(users);

        assertNotNull(usersService.findAll());
    }

    @Test
    public void shouldReturnUserByEmail() {
        String email = "vadimbealev002@gmail.com";
        User user = new User();
        user.setEmail(email);

        Mockito.when(usersRepository.findByEmail(email)).thenReturn(Optional.of(user));

        assertEquals(usersService.findByEmail(email).getEmail(), user.getEmail());
    }

    @Test
    public void shouldThrowExceptionDuringSaving() {
        String email = "vadimbelaev002@gmail.com";
        User user = new User();
        user.setEmail(email);
        Mockito.when(usersRepository.existsByEmail(email)).thenReturn(true);
        assertThrows(AlreadyExistsException.class, () -> usersService.save(user));
    }

    @Test
    public void shouldReturnWhoBecomeTrader() {
        User user = new User();
        user.setId(1L);
        Mockito.when(usersRepository.save(user)).thenReturn(user);

        assertEquals(usersService.becomeTrader(user).getRole(), Role.TRADER);
    }

    @Test
    public void shouldReturnSavedUserIfDoesntExist() {
        User user = new User();
        user.setId(1L);

        Mockito.when(usersRepository.save(user)).thenReturn(user);

        assertEquals(usersService.save(user), user);
    }

    @Test
    public void shouldThrowExceptionIfUserDoesntExistById() {
        User user = new User();
        Long id = 1L;
        user.setId(id);

        Mockito.when(usersRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> usersService.findById(id));
    }


}

