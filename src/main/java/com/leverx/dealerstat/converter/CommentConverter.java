package com.leverx.dealerstat.converter;

import com.leverx.dealerstat.dto.CommentDTO;
import com.leverx.dealerstat.entity.Comment;
import com.leverx.dealerstat.entity.User;
import com.leverx.dealerstat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class CommentConverter {

    private final UserService userService;

    @Autowired
    public CommentConverter(UserService userService) {
        this.userService = userService;
    }

    public CommentDTO convertToDTO(final Comment comment) {
        final String message = comment.getMessage();
        final Double rate = comment.getRate();
        final Long authorId = comment.getAuthor().getId();
        final Long userId = comment.getUser().getId();
        return CommentDTO.builder()
                .message(message)
                .rate(rate)
                .authorId(authorId)
                .userId(userId)
                .build();
    }

    public Comment convertToModel(final CommentDTO commentDTO) {
        final String message = commentDTO.getMessage();
        final Double rate = commentDTO.getRate();
        final User author = userService.findById(commentDTO.getAuthorId());
        final boolean approved = false;
        final Date createdAt = commentDTO.getCreatedAt() == null ? new Date() : commentDTO.getCreatedAt();
        final User user = userService.findById(commentDTO.getUserId());
        return Comment.builder()
                .message(message)
                .rate(rate)
                .approved(approved)
                .creatingDate(createdAt)
                .author(author)
                .user(user)
                .build();
    }
}


