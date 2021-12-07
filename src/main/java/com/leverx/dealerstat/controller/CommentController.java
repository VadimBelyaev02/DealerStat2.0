package com.leverx.dealerstat.controller;

import com.leverx.dealerstat.dto.CommentDTO;
import com.leverx.dealerstat.dto.UserDTO;
import com.leverx.dealerstat.exception.AccessDeniedException;
import com.leverx.dealerstat.security.AuthenticatedUserFactory;
import com.leverx.dealerstat.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CommentDTO getComment(@PathVariable("id") Long commentId) {
        return commentService.getComment(commentId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDTO addComment(@RequestBody CommentDTO commentDTO) {
        return commentService.save(commentDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteComment(@PathVariable("id") Long commentId) {
        commentService.deleteComment(commentId);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public CommentDTO updateComment(@RequestBody CommentDTO commentDTO) {
        return commentService.updateComment(commentDTO);

    }

    @GetMapping("/unapproved")
    @ResponseStatus(HttpStatus.OK)
    public List<CommentDTO> getUnapprovedComments() {
        return commentService.getUnapprovedComments();
    }

}
