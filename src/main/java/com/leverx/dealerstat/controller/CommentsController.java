package com.leverx.dealerstat.controller;

import com.leverx.dealerstat.converter.CommentsConverter;
import com.leverx.dealerstat.converter.UsersConverter;
import com.leverx.dealerstat.dto.CommentDTO;
import com.leverx.dealerstat.dto.UserDTO;
import com.leverx.dealerstat.model.Comment;
import com.leverx.dealerstat.model.User;
import com.leverx.dealerstat.security.AuthenticatedUserFactory;
import com.leverx.dealerstat.service.CommentsService;
import com.leverx.dealerstat.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class CommentsController {

    private final UsersService usersService;
    private final CommentsService commentsService;
    private final CommentsConverter commentsConverter;
    private final UsersConverter usersConverter;
    private final AuthenticatedUserFactory userFactory;

    @Autowired
    public CommentsController(@Qualifier("userServiceImpl") UsersService usersService,
                              CommentsService commentsService,
                              CommentsConverter commentsConverter,
                              UsersConverter usersConverter,
                              AuthenticatedUserFactory userFactory) {
        this.usersService = usersService;
        this.commentsService = commentsService;
        this.commentsConverter = commentsConverter;
        this.usersConverter = usersConverter;
        this.userFactory = userFactory;
    }

    @GetMapping("/users/{id}/comments")
    public ResponseEntity<List<CommentDTO>> getUserComments(@PathVariable("id") Long id) {
        List<CommentDTO> commentDTOS = commentsService.getComments(id).stream()
                .map(commentsConverter::convertToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(commentDTOS);
    }

    @GetMapping("/comments/{commentId}")
    public ResponseEntity<CommentDTO> getComment(@PathVariable("commentId") Long commentId) {
        CommentDTO commentDTO = commentsConverter.convertToDTO(commentsService.getComment(commentId));
        return ResponseEntity.ok(commentDTO);
    }

//    @GetMapping("/rating/{sortBy}")
//    public ResponseEntity<?> getAllComments(
//            @QuerydslPredicate(root = Comment.class)Predicate predicate,
//            @PathVariable("sortBy") String sortBy,
//            Pageable pageable) {
//        Sort sort = pageable.getSort();
//        if (sort == null) {
//            return null;
//        }
//      //  pageable = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), sort);
//
//        List<CommentDTO> commentDTOS = commentsService.findAll().stream()
//                .map(commentsConverter::convertToDTO).collect(Collectors.toList());
//        return ResponseEntity.ok(commentDTOS);
//    }


    @PostMapping("/comments")
    public ResponseEntity<?> addComment(@RequestBody CommentDTO commentDTO) {
        User author = userFactory.currentUser();
        if (commentDTO.getUserId().equals(author.getId())) {
            return new ResponseEntity<>("You can't comment yourself!", HttpStatus.FORBIDDEN);
        }
        commentDTO.setAuthorId(author.getId());
        Comment comment = commentsConverter.convertToModel(commentDTO);
        commentsService.addComment(comment);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable("commentId") Long commentId) {
        Long userId = userFactory.currentUser().getId();

        if (!commentsService.getAuthor(commentId).getId().equals(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        commentsService.deleteComment(commentId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/comments/{id}")
    public ResponseEntity<?> updateComment(@RequestBody CommentDTO commentDTO,
                                                    @PathVariable("id") Long id) {
        Long userId = userFactory.currentUser().getId();

        if (!commentsService.getAuthor(id).getId().equals(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        commentDTO.setAuthorId(userId);
        commentsService.updateComment(commentsConverter.convertToModel(commentDTO), id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/users/{id}/rating")
    public ResponseEntity<Double> getTraderRating(@PathVariable("id") Long id) {
        Double rate = commentsService.calculateRating(id);
        return ResponseEntity.ok(rate);
    }

    @GetMapping("/rating")
    public ResponseEntity<Map<UserDTO, Double>> getAllRatings(@RequestParam("sort") Boolean ascending) {
        Map<User, Double> rating = commentsService.calculateAllRating(ascending);
        Map<UserDTO, Double> result = rating.keySet().stream()
                .collect(Collectors.toMap(usersConverter::convertToDTO, rating::get));
        return ResponseEntity.ok(result);
    }

    @GetMapping("/comments/unapproved")
    public ResponseEntity<List<CommentDTO>> getUnapprovedComments() {
        List<CommentDTO> commentDTOS = commentsService.getUnapprovedComments()
                .stream().map(commentsConverter::convertToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(commentDTOS);
    }

    @PostMapping("/comments/approve")
    public ResponseEntity<?> approveComment(@RequestParam("id") Long commentId) {
        commentsService.approveComment(commentId);
        return ResponseEntity.ok().build();
    }
}
