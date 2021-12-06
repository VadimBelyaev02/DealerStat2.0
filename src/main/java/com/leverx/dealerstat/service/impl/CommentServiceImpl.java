package com.leverx.dealerstat.service.impl;

import com.leverx.dealerstat.converter.CommentConverter;
import com.leverx.dealerstat.converter.UserConverter;
import com.leverx.dealerstat.dto.CommentDTO;
import com.leverx.dealerstat.dto.UserDTO;
import com.leverx.dealerstat.exception.AlreadyExistsException;
import com.leverx.dealerstat.exception.NotFoundException;
import com.leverx.dealerstat.entity.Comment;
import com.leverx.dealerstat.entity.User;
import com.leverx.dealerstat.repository.CommentRepository;
import com.leverx.dealerstat.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository repository;
    private final CommentConverter commentConverter;
    private final UserConverter userConverter;

    @Autowired
    public CommentServiceImpl(CommentRepository repository, CommentConverter commentConverter, UserConverter userConverter) {
        this.repository = repository;
        this.commentConverter = commentConverter;
        this.userConverter = userConverter;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentDTO> getComments(Long userId) {
        return repository.findAllByAuthorId(userId).stream()
                .map(commentConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CommentDTO getComment(Long commentId) {
        Comment comment = repository.findById(commentId).orElseThrow(() -> {
            throw new NotFoundException("Comment is not found");
        });
        return commentConverter.convertToDTO(comment);
    }

    @Override
    @Transactional
    public void deleteComment(Long commentId) {
        if (!repository.existsById(commentId)) {
            throw new NotFoundException("Comment is not found");
        }
        repository.deleteById(commentId);
    }

    @Override
    @Transactional
    public CommentDTO save(CommentDTO comment) {
        if (repository.existsById(comment.getId())) {
            throw new AlreadyExistsException("Comment is already exists");
        }
        return commentConverter.convertToDTO(repository.save(commentConverter.convertToModel(comment)));
    }

    @Override
    @Transactional
    public UserDTO getAuthor(Long commentId) {
        Comment comment = repository.findById(commentId).orElseThrow(() -> {
            throw new NotFoundException("Comment is not found");
        });
        return userConverter.convertToDTO(comment.getAuthor());
    }

    @Override
    @Transactional(readOnly = true)
    public Double calculateRating(Long userId) {
        List<Comment> comments = repository.findAllByUserId(userId);
        return (comments.stream().map(Comment::getRate).reduce(0D, Double::sum))
                / (long) comments.size();
    }

    @Transactional(readOnly = true)
    public Map<UserDTO, Double> calculateAllRating() {
        List<CommentDTO> comments = repository.findAll().stream()
                .map(commentConverter::convertToDTO)
                .collect(Collectors.toList());
        Map<UserDTO, Integer> countOfRates = new HashMap<>();
        Map<UserDTO, Double> rating = new LinkedHashMap<>();
        for (CommentDTO comment : comments) {
            User user = comment.getUserId();
            if (rating.containsKey(user)) {
                rating.put(user, rating.get(user) + comment.getRate());
                countOfRates.put(user, countOfRates.get(user) + 1);
            } else {
                rating.put(user, comment.getRate());
                countOfRates.put(user, 1);
            }
        }
        for (Map.Entry<User, Double> entry : rating.entrySet()) {
            entry.setValue(entry.getValue() / countOfRates.get(entry.getKey()));
        }
        return rating;
    }

    @Override
    @Transactional
    public List<CommentDTO> findAll() {
        return repository.findAll().stream()
                .map(commentConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CommentDTO updateComment(CommentDTO comment) {
        if (!repository.existsById(comment.getId())) {
            throw new NotFoundException("Comment is not found or is not approved");
        }
        return commentConverter.convertToDTO(repository.save(commentConverter.convertToModel(comment)));
    }

    @Override
    @Transactional
    public List<CommentDTO> getUnapprovedComments() {
        return repository.findAllByApproved(false).stream()
                .map(commentConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CommentDTO approveComment(CommentDTO comment) {
        if (!repository.existsById(comment.getId())) {
            throw new NotFoundException("Comment is not found");
        };
        return commentConverter.convertToDTO(repository.save(commentConverter.convertToModel(comment)));
    }
}
