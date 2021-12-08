package com.leverx.dealerstat.controller;

import com.leverx.dealerstat.dto.CommentDTO;
import com.leverx.dealerstat.dto.UserDTO;
import com.leverx.dealerstat.exception.NotValidException;
import com.leverx.dealerstat.service.CommentService;
import com.leverx.dealerstat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final CommentService commentService;

    @Autowired
    public UserController(UserService userService,
                          CommentService commentService1) {
        this.userService = userService;
        this.commentService = commentService1;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserDTO> getAllUsers() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO getUser(@PathVariable("id") Long id) {
        return userService.getById(id);
    }

    @GetMapping("/rating")
    @ResponseStatus(HttpStatus.OK)
    public Map<UserDTO, Double> getRating() {
        return commentService.getRating();
    }

    @GetMapping("/{id}/rating")
    @ResponseStatus(HttpStatus.OK)
    public Map<UserDTO, Double> getTraderRating(@PathVariable("id") Long id) {
        return commentService.getUserRating(id);
    }

    @GetMapping("/{id}/comments")
    @ResponseStatus(HttpStatus.OK)
    public List<CommentDTO> getUserComments(@PathVariable("id") Long id) {
        return commentService.getUserComments(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
    }


    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public UserDTO updateUser(@RequestBody @Valid UserDTO userDTO, BindingResult result) {
        if (result.hasErrors()) {
            throw new NotValidException(result.getAllErrors().toString());
        }
        return userService.update(userDTO);
    }
}
