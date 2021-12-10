package com.leverx.dealerstat.dto.converter;

import com.leverx.dealerstat.dto.CommentDTO;
import com.leverx.dealerstat.entity.Comment;
import com.leverx.dealerstat.entity.User;
import com.leverx.dealerstat.repository.CommentRepository;
import com.leverx.dealerstat.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

import static java.util.Objects.isNull;

@Component
public class CommentConverter {

    private final UserRepository userRepository;

    public CommentConverter(UserRepository userRepository) {
        this.userRepository = userRepository;
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
        final User author = userRepository.getById(commentDTO.getAuthorId());
        final User user = userRepository.getById(commentDTO.getUserId());
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


