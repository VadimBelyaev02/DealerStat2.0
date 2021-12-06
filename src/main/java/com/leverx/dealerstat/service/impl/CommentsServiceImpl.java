package com.leverx.dealerstat.service.impl;

import com.leverx.dealerstat.exception.NotFoundException;
import com.leverx.dealerstat.model.Comment;
import com.leverx.dealerstat.model.User;
import com.leverx.dealerstat.repository.CommentsRepository;
import com.leverx.dealerstat.service.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class CommentsServiceImpl implements CommentsService {

    private final CommentsRepository repository;

    @Autowired
    public CommentsServiceImpl(CommentsRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public List<Comment> getComments(Long userId) {
        return repository.findAllByAuthorId(userId);
    }

    @Override
    @Transactional
    public Comment getComment(Long commentId) {
        return repository.findById(commentId).orElseThrow(() -> {
            throw new NotFoundException("Comment is not found");
        });
    }

    @Override
    @Transactional
    public void deleteComment(Long commentId) {
        repository.deleteById(commentId);
    }

    @Override
    @Transactional
    public Comment save(Comment comment) {
        return repository.save(comment);
    }

    @Override
    @Transactional
    public User getAuthor(Long commentId) {
        Comment comment = repository.findById(commentId).orElseThrow(() -> {
            throw new NotFoundException("Comment is not found");
        });
        return comment.getAuthor();
    }

    @Override
    @Transactional(readOnly = true)
    public Double calculateRating(Long userId) {
        List<Comment> comments = repository.findAllByUserId(userId);
        return (comments.stream().map(Comment::getRate).reduce(0D, Double::sum))
                / (long) comments.size();
    }

    @Transactional(readOnly = true)
    public Map<User, Double> calculateAllRating() {
        List<Comment> comments = repository.findAll();
        Map<User, Integer> countOfRates = new HashMap<>();
        Map<User, Double> rating = new LinkedHashMap<>();
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
