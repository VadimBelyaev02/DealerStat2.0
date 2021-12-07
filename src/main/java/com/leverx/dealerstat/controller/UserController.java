package com.leverx.dealerstat.controller;

import com.leverx.dealerstat.dto.CommentDTO;
import com.leverx.dealerstat.dto.UserDTO;
import com.leverx.dealerstat.service.CommentService;
import com.leverx.dealerstat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
        return userService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO getUser(@PathVariable("id") Long id) {
        return userService.findById(id);
    }

    @GetMapping("/rating")
    @ResponseStatus(HttpStatus.OK)
    public Map<UserDTO, Double> getAllRatings() {
        return commentService.calculateAllRating();
    }

    @GetMapping("/{id}/rating")
    @ResponseStatus(HttpStatus.OK)
    public Map<UserDTO, Double> getTraderRating(@PathVariable("id") Long id) {
        UserDTO user = userService.findById(id);
        Double rating = commentService.calculateRating(id);
        return Collections.singletonMap(user, rating);
    }

    @GetMapping("/{id}/comments")
    @ResponseStatus(HttpStatus.OK)
    public List<CommentDTO> getUserComments(@PathVariable("id") Long id) {
        return commentService.getComments(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
    }


    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public UserDTO updateUser(@RequestBody UserDTO userDTO) {
        return userService.update(userDTO);
    }
}
