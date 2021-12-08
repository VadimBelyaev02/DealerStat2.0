package com.leverx.dealerstat.controller;

import com.leverx.dealerstat.dto.CommentDTO;
import com.leverx.dealerstat.dto.UserDTO;
import com.leverx.dealerstat.exception.AccessDeniedException;
import com.leverx.dealerstat.exception.NotValidException;
import com.leverx.dealerstat.security.AuthenticatedUserFactory;
import com.leverx.dealerstat.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
        return commentService.getCommentById(commentId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CommentDTO> getAllComments() {
        return commentService.findAllApproved();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDTO addComment(@RequestBody @Valid CommentDTO commentDTO, BindingResult result) {
        if (result.hasErrors()) {
            throw new NotValidException(result.getAllErrors().toString());
        }
        return commentService.save(commentDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteComment(@PathVariable("id") Long commentId) {
        commentService.deleteComment(commentId);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public CommentDTO updateComment(@RequestBody @Valid CommentDTO commentDTO, BindingResult result) {
        if (result.hasErrors()) {
            result.getFieldError();
            throw new NotValidException(result.getAllErrors().toString());
        }
        return commentService.updateComment(commentDTO);
    }

    @GetMapping("/unapproved")
    @ResponseStatus(HttpStatus.OK)
    public List<CommentDTO> getUnapprovedComments() {
        return commentService.getUnapprovedComments();
    }
}
