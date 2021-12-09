package com.leverx.dealerstat.service;

import com.leverx.dealerstat.dto.CommentDTO;
import com.leverx.dealerstat.dto.UserDTO;

import java.util.List;
import java.util.Map;

public interface CommentService {

    List<CommentDTO> getUserComments(Long userId);

    CommentDTO getById(Long commentId);

    void delete(Long commentId);

    CommentDTO save(CommentDTO comment);

    UserDTO getAuthor(Long commentId);

    Map<Long, Double> getUserRating(Long userId);

    Map<Long, Double> getRating();

    List<CommentDTO> getAll();

    CommentDTO update(CommentDTO comment);

    List<CommentDTO> getUnapproved();

    List<CommentDTO> getApproved();
}
