package com.leverx.dealerstat.dto.converter;

import com.leverx.dealerstat.dto.CommentDTO;
import com.leverx.dealerstat.entity.Comment;
import com.leverx.dealerstat.entity.User;
import com.leverx.dealerstat.repository.CommentRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Objects;

import static java.util.Objects.isNull;

@Component
public class CommentConverter {

    private final CommentRepository commentRepository;

    public CommentConverter(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public CommentDTO convertToDTO(final Comment comment) {
        final Long id = comment.getId();
        final String message = comment.getMessage();
        final Double rate = comment.getRate();
        final Long authorId = comment.getAuthor().getId();
        final Long userId = comment.getAuthor().getId();
        final LocalDate createdAt = isNull(comment.getCreatingDate()) ? LocalDate.now() : comment.getCreatingDate();
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
    }

    public Comment convertToModel(final CommentDTO commentDTO) {
        final Long id = commentDTO.getId();
        final String message = commentDTO.getMessage();
        final Double rate = commentDTO.getRate();
        final User author = commentRepository.getById(commentDTO.getId()).getAuthor();
        final User user = commentRepository.getById(commentDTO.getUserId()).getUser();
        final boolean approved = commentDTO.isApproved();
        final LocalDate createdAt = commentDTO.getCreatedAt();
        return Comment.builder()
                .id(id)
                .message(message)
                .rate(rate)
                .approved(approved)
                .creatingDate(createdAt)
                .author(author)
                .user(user)
                .build();
    }
}


