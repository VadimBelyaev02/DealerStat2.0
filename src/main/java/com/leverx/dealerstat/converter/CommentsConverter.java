package com.leverx.dealerstat.converter;

import com.leverx.dealerstat.dto.CommentDTO;
import com.leverx.dealerstat.model.Comment;
import com.leverx.dealerstat.model.User;
import com.leverx.dealerstat.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class CommentsConverter {

    private final UsersService usersService;

    @Autowired
    public CommentsConverter(UsersService usersService) {
        this.usersService = usersService;
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
        final User author = usersService.findById(commentDTO.getAuthorId());
        final boolean approved = false;
        final Date createdAt = commentDTO.getCreatedAt() == null ? new Date() : commentDTO.getCreatedAt();
        final User user = usersService.findById(commentDTO.getUserId());
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


