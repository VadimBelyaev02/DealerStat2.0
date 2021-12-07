package com.leverx.dealerstat.converter;

import com.leverx.dealerstat.dto.CommentDTO;
import com.leverx.dealerstat.dto.UserDTO;
import com.leverx.dealerstat.entity.Comment;
import com.leverx.dealerstat.entity.User;
import com.leverx.dealerstat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;

@Component
public class CommentConverter {

    private final UserConverter userConverter;


    public CommentDTO convertToDTO(final Comment comment) {
        final Long id = comment.getId();
        final String message = comment.getMessage();
        final Double rate = comment.getRate();
        final UserDTO author = userConverter.comment.getAuthor().getId();
        final Long userId = comment.getUser().getId();
        final LocalDate createdAt = comment.getCreatingDate();
        final boolean approved = comment.getApproved();
        return CommentDTO.builder()
                .id(id)
                .message(message)
                .rate(rate)
                .authorId(authorId)
                .userId(userId)
                .createdAt(createdAt)
                .approved(approved)
                .build();
        /*
            private String message;
    private Double rate;
    private Long userId;
    private Long authorId;
    private Date createdAt;
    private boolean approved;
         */
    }

    public Comment convertToModel(final CommentDTO commentDTO) {
        final Long id = commentDTO.getId()
        final String message = commentDTO.getMessage();
        final Double rate = commentDTO.getRate();
        final User author = user.findById(commentDTO.getAuthorId());
        final boolean approved = false;
        final Date createdAt = commentDTO.getCreatedAt() == null ? new Date() : commentDTO.getCreatedAt();
        final User user = userService.findById(commentDTO.getUserId());
        return Comment.builder()
                .id
                .message(message)
                .rate(rate)
                .approved(approved)
                .creatingDate(createdAt)
                .author(author)
                .user(user)
                .build();
    }
}


