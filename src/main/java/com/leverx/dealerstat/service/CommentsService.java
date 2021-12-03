package com.leverx.dealerstat.service;

import com.leverx.dealerstat.model.Comment;
import com.leverx.dealerstat.model.User;

import java.util.List;
import java.util.Map;

public interface CommentsService {

    List<Comment> getComments(Long userId);

    Comment getComment(Long commentId);

    void deleteComment(Long commentId);

    void addComment(Comment comment);

    User getAuthor(Long commentId);

    Double calculateRating(Long userId);

    Map<User, Double> calculateAllRating(Boolean ascending);

    List<Comment> findAll();

    void updateComment(Comment comment, Long id);

    List<Comment> getUnapprovedComments();

    void approveComment(Long commentId);
}
