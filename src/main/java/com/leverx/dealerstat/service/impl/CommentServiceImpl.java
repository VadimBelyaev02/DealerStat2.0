package com.leverx.dealerstat.service.impl;

import com.leverx.dealerstat.dto.converter.CommentConverter;
import com.leverx.dealerstat.dto.converter.UserConverter;
import com.leverx.dealerstat.dto.CommentDTO;
import com.leverx.dealerstat.dto.UserDTO;
import com.leverx.dealerstat.entity.User;
import com.leverx.dealerstat.entity.enums.Permission;
import com.leverx.dealerstat.exception.AccessDeniedException;
import com.leverx.dealerstat.exception.AlreadyExistsException;
import com.leverx.dealerstat.exception.NotFoundException;
import com.leverx.dealerstat.entity.Comment;
import com.leverx.dealerstat.repository.CommentRepository;
import com.leverx.dealerstat.security.AuthenticatedUserFactory;
import com.leverx.dealerstat.service.CommentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentConverter commentConverter;
    private final UserConverter userConverter;
    private final AuthenticatedUserFactory userFactory;

    public CommentServiceImpl(CommentRepository commentRepository,
                              CommentConverter commentConverter,
                              UserConverter userConverter,
                              AuthenticatedUserFactory userFactory) {
        this.commentRepository = commentRepository;
        this.commentConverter = commentConverter;
        this.userConverter = userConverter;
        this.userFactory = userFactory;
    }


    @Override
    @Transactional(readOnly = true)
    public List<CommentDTO> getUserComments(Long userId) {
        return commentRepository.findAllByUserId(userId).stream()
                .map(commentConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CommentDTO getById(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> {
            throw new NotFoundException("Comment is not found");
        });
        return commentConverter.convertToDTO(comment);
    }

    @Override
    @Transactional
    public void delete(Long commentId) {
        if (!commentRepository.existsById(commentId)) {
            throw new NotFoundException("Comment is not found");
        }
        UserDTO user = userFactory.currentUser();
        if (!commentRepository.getById(commentId).getAuthor().getId().equals(user.getId())
                && !user.getRole().getPermissions().contains(Permission.DELETE)) {
            throw new AccessDeniedException("It's not your comment!");
        }
        commentRepository.deleteById(commentId);
    }

    @Override
    @Transactional
    public CommentDTO save(CommentDTO comment) {
        if (commentRepository.existsById(comment.getId())) {
            throw new AlreadyExistsException("Comment is already exists");
        }
        UserDTO author = userFactory.currentUser();
        if (comment.getUserId().equals(author.getId())) {
            throw new AccessDeniedException("You can't comment yourself!");
        }
        return commentConverter.convertToDTO(commentRepository.save(commentConverter.convertToModel(comment)));
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO getAuthor(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> {
            throw new NotFoundException("Comment is not found");
        });
        return userConverter.convertToDTO(comment.getAuthor());
    }

    @Override
    @Transactional(readOnly = true)
    public Map<Long, Double> getUserRating(Long userId) {
        List<Comment> comments = commentRepository.findAllByUserId(userId);
        if (comments.size() == 0) {
            throw new NotFoundException("No one comment was found");
        }
        Double userRating = (comments.stream().map(Comment::getRate).reduce(0D, Double::sum))
                / (long) comments.size();
        Long authorId = userConverter.convertToDTO(comments.get(0).getUser()).getId();

        return Collections.singletonMap(authorId, userRating);
    }

    @Transactional(readOnly = true)
    public Map<Long, Double> getRating() {
        List<Comment> comments = commentRepository.findAll();

        Map<Long, Integer> countOfRates = new HashMap<>();
        Map<Long, Double> rating = new HashMap<>();
        for (Comment comment : comments) {
            Long userId = comment.getUser().getId();
            if (rating.containsKey(userId)) {
                rating.put(userId, rating.get(userId) + comment.getRate());
                countOfRates.put(userId, countOfRates.get(userId) + 1);
            } else {
                rating.put(userId, comment.getRate());
                countOfRates.put(userId, 1);
            }
        }
        for (Map.Entry<Long, Double> entry : rating.entrySet()) {
            entry.setValue(entry.getValue() / countOfRates.get(entry.getKey()));
        }
        return rating;
//        List<CommentDTO> comments = commentRepository.findAll().stream()
//                .map(commentConverter::convertToDTO)
//                .collect(Collectors.toList());
//        Map<UserDTO, Integer> countOfRates = new HashMap<>();
//        Map<UserDTO, Double> rating = new LinkedHashMap<>();
//        for (CommentDTO comment : comments) {
//            User user = comment.getUserId();
//            if (rating.containsKey(user)) {
//                rating.put(user, rating.get(user) + comment.getRate());
//                countOfRates.put(user, countOfRates.get(user) + 1);
//            } else {
//                rating.put(user, comment.getRate());
//                countOfRates.put(user, 1);
//            }
//        }
//        for (Map.Entry<User, Double> entry : rating.entrySet()) {
//            entry.setValue(entry.getValue() / countOfRates.get(entry.getKey()));
//        }
//        return rating;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentDTO> getAll() {
        return commentRepository.findAll().stream()
                .map(commentConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CommentDTO update(CommentDTO commentDTO) {
        Long userId = userFactory.currentUser().getId();

        Comment comment = commentRepository.findById(commentDTO.getId()).orElseThrow(() -> {
            throw new NotFoundException("Comment is not found");
        });
        if (!comment.getAuthor().getId().equals(userId)) {
            throw new AccessDeniedException("It's not your comment!");
        }
        return commentConverter.convertToDTO(commentRepository.save(commentConverter.convertToModel(commentDTO)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentDTO> getUnapproved() {
        return commentRepository.findAllByApproved(false).stream()
                .map(commentConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentDTO> getApproved() {
        return commentRepository.findAll().stream()
                .filter(Comment::getApproved)
                .map(commentConverter::convertToDTO)
                .collect(Collectors.toList());
    }

}
