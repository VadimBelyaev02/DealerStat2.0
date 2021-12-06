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
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;
    private final AuthenticatedUserFactory userFactory;

    @Autowired
    public CommentController(CommentService commentService,
                             AuthenticatedUserFactory userFactory) {
        this.commentService = commentService;
        this.userFactory = userFactory;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CommentDTO getComment(@PathVariable("id") Long commentId) {
        return commentService.getComment(commentId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDTO addComment(@RequestBody CommentDTO commentDTO) {
        UserDTO author = userFactory.currentUser();
        if (commentDTO.getUserId().equals(author.getId())) {
            throw new AccessDeniedException("You can't comment youself!");
        }
        commentDTO.setAuthorId(author.getId());
        return commentService.save(commentDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteComment(@PathVariable("id") Long commentId) {
        Long userId = userFactory.currentUser().getId();

        if (!commentService.getAuthor(commentId).getId().equals(userId)) {
            throw new AccessDeniedException("It's not your comment!");
        }
        commentService.deleteComment(commentId);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public CommentDTO updateComment(@RequestBody CommentDTO commentDTO) {
        Long userId = userFactory.currentUser().getId();
        if (!commentService.getAuthor(commentDTO.getId()).getId().equals(userId)) {
            throw new AccessDeniedException("It's not your comment!");
        }
        commentDTO.setAuthorId(userId);
        return commentService.updateComment(commentDTO);

    }

    @GetMapping("/unapproved")
    @ResponseStatus(HttpStatus.OK)
    public List<CommentDTO> getUnapprovedComments() {
        return commentService.getUnapprovedComments();
    }

    @PutMapping("/approve")
    @ResponseStatus(HttpStatus.OK)
    public CommentDTO approveComment(@RequestBody CommentDTO commentDTO) {
        return commentService.approveComment(commentDTO);
    }
}
