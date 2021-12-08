package com.leverx.dealerstat.service;

import com.leverx.dealerstat.dto.CommentDTO;
import com.leverx.dealerstat.dto.UserDTO;
import com.leverx.dealerstat.entity.Comment;
import com.leverx.dealerstat.entity.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CommentService {

    List<CommentDTO> getUserComments(Long userId);

    CommentDTO getCommentById(Long commentId);

    void deleteComment(Long commentId);

    CommentDTO save(CommentDTO comment);

    UserDTO getAuthor(Long commentId);

    Double calculateRating(Long userId);

    Map<UserDTO, Double> calculateAllRating();

    List<CommentDTO> getAll();

    CommentDTO updateComment(CommentDTO comment);

    List<CommentDTO> getUnapprovedComments();

    List<CommentDTO> findAllApproved();
}
