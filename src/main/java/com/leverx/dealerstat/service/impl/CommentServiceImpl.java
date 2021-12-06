package com.leverx.dealerstat.service.impl;

import com.leverx.dealerstat.converter.CommentsConverter;
import com.leverx.dealerstat.converter.UsersConverter;
import com.leverx.dealerstat.dto.CommentDTO;
import com.leverx.dealerstat.dto.UserDTO;
import com.leverx.dealerstat.exception.AlreadyExistsException;
import com.leverx.dealerstat.exception.NotFoundException;
import com.leverx.dealerstat.entity.Comment;
import com.leverx.dealerstat.entity.User;
import com.leverx.dealerstat.repository.CommentsRepository;
import com.leverx.dealerstat.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentsRepository repository;
    private final CommentsConverter commentsConverter;
    private final UsersConverter usersConverter;

    @Autowired
    public CommentServiceImpl(CommentsRepository repository, CommentsConverter commentsConverter, UsersConverter usersConverter) {
        this.repository = repository;
        this.commentsConverter = commentsConverter;
        this.usersConverter = usersConverter;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentDTO> getComments(Long userId) {
        return repository.findAllByAuthorId(userId).stream()
                .map(commentsConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CommentDTO getComment(Long commentId) {
        Comment comment = repository.findById(commentId).orElseThrow(() -> {
            throw new NotFoundException("Comment is not found");
        });
        return commentsConverter.convertToDTO(comment);
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
        return commentsConverter.convertToDTO(repository.save(commentsConverter.convertToModel(comment)));
    }

    @Override
    @Transactional
    public UserDTO getAuthor(Long commentId) {
        Comment comment = repository.findById(commentId).orElseThrow(() -> {
            throw new NotFoundException("Comment is not found");
        });
        return usersConverter.convertToDTO(comment.getAuthor());
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
        List<Comment> comments = repository.findAll();
        Map<UserDTO, Integer> countOfRates = new HashMap<>();
        Map<UserDTO, Double> rating = new LinkedHashMap<>();
        for (Comment comment : comments) {
            User user = comment.getUser();
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
    public List<Comment> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public Comment updateComment(Comment comment, Long id) {
        Comment commentFromDB = repository.findById(id).orElse(null);
        if (commentFromDB == null || !commentFromDB.getApproved()) {
            throw new NotFoundException("Comment is not found or is not approved");
        }
        commentFromDB.setMessage(comment.getMessage());
        commentFromDB.setRate(comment.getRate());
        return repository.save(commentFromDB);
    }

    @Override
    @Transactional
    public List<Comment> getUnapprovedComments() {
        return repository.findAllByApproved(false);
    }

    @Override
    @Transactional
    public Comment approveComment(Long commentId) {
        Comment commentFromDB = repository.findById(commentId).orElseThrow(() -> {
            throw new NotFoundException("Comment is not found");
        });
        commentFromDB.setApproved(true);
        return repository.save(commentFromDB);
    }
}
