package com.leverx.dealerstat.unit;

import com.leverx.dealerstat.exception.NotFoundException;
import com.leverx.dealerstat.entity.Comment;
import com.leverx.dealerstat.entity.User;
import com.leverx.dealerstat.repository.CommentRepository;
import com.leverx.dealerstat.service.impl.CommentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CommentServiceUnitTest {

    @InjectMocks
    private CommentServiceImpl commentsService;

    @Mock
    private CommentRepository commentRepository;


    @Test
    public void shouldReturnCommentById() {
        Comment comment = new Comment();
        comment.setId(1L);
        Mockito.when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));

        assertNotNull(commentsService.getComment(1L));
    }

    @Test
    public void shouldReturnAllComments() {
        List<Comment> comments = new ArrayList<>();
        comments.add(new Comment());
        comments.add(new Comment());

        Mockito.when(commentRepository.findAll()).thenReturn(comments);

        assertNotNull(commentsService.findAll());
        assertEquals(commentsService.findAll().size(), 2);
    }

    @Test
    public void shouldReturnAllCommentsByUserId() {
        List<Comment> comments = new ArrayList<>();
        User user = new User();
        user.setId(1L);
        Comment comment = new Comment();
        Comment comment1 = new Comment();
        comment.setId(1L);
        comment.setAuthor(user);
        comment1.setId(2L);
        comment1.setAuthor(user);
        comments.add(comment);
        comments.add(comment1);

        Mockito.when(commentRepository.findAllByUserId(1L)).thenReturn(comments);

        assertEquals(commentsService.getComments(1L).size(), 2);
    }


    @Test
    public void shouldReturnSavedComment() {
        Comment comment = new Comment();
        comment.setId(1L);

        Mockito.when(commentRepository.save(comment)).thenReturn(comment);

        assertEquals(commentsService.save(comment), comment);
    }

    @Test
    public void shouldReturnUserByCommentId() {
        User user = new User();
        user.setId(1L);
        Comment comment = new Comment();
        comment.setId(1L);
        comment.setAuthor(user);

        Mockito.when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));

        assertEquals(commentsService.getAuthor(comment.getId()), user);
    }

    @Test
    public void shouldCalculateRatingOfAllUsers() {
        List<Comment> comments = new ArrayList<>();
        Comment comment1 = new Comment();
        Comment comment2 = new Comment();
        Comment comment3 = new Comment();
        User user1 = new User();
        User user2 = new User();
        comment1.setRate(1.5);
        comment1.setUser(user1);
        comment2.setRate(2.5);
        comment3.setRate(5D);
        comment2.setUser(user2);
        comment3.setUser(user2);
        comments.add(comment1);
        comments.add(comment2);
        comments.add(comment3);

        Mockito.when(commentRepository.findAll()).thenReturn(comments);

        Map<User, Double> rating = new HashMap<>();
        rating.put(user1, 1.5D);
        rating.put(user2, (2.5 + 5D) / 2);

        assertEquals(commentsService.calculateAllRating(), rating);
    }

    @Test
    public void shouldCalculateUserRatingById() {
        User user = new User();
        user.setId(1L);
        List<Comment> comments = new ArrayList<>();
        Comment comment1 = new Comment();
        Comment comment2 = new Comment();
        Comment comment3 = new Comment();
        comment1.setRate(4D);
        comment1.setUser(user);
        comment2.setRate(1D);
        comment2.setUser(user);
        comment3.setRate(5D);
        comment3.setUser(user);
        comments.add(comment1);
        comments.add(comment2);
        comments.add(comment3);

        Mockito.when(commentRepository.findAllByUserId(user.getId())).thenReturn(comments);

        assertEquals(commentsService.calculateRating(user.getId()), (4D + 1D + 5D) / 3);
    }

    @Test
    public void shouldThrowExceptionDuringUpdating() {
        Comment comment = new Comment();
        comment.setId(1L);

        Mockito.when(commentRepository.findById(comment.getId())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> commentsService.updateComment(comment, 1L));
    }
}
//
//    List<Comment> getComments(Long userId);
//
//    Comment getComment(Long commentId);
//
//    void deleteComment(Long commentId);
//
//    void save(Comment comment);
//
//    User getAuthor(Long commentId);
//
//    Double calculateRating(Long userId);
//
//    Map<User, Double> calculateAllRating(Boolean ascending);
//
//    List<Comment> findAll();
//
//    void updateComment(Comment comment, Long id);
//
//    List<Comment> getUnapprovedComments();
//
//    void approveComment(Long commentId);