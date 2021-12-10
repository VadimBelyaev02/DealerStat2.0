package com.leverx.dealerstat.unit;

import com.leverx.dealerstat.dto.CommentDTO;
import com.leverx.dealerstat.dto.UserDTO;
import com.leverx.dealerstat.dto.converter.CommentConverter;
import com.leverx.dealerstat.dto.converter.UserConverter;
import com.leverx.dealerstat.exception.NotFoundException;
import com.leverx.dealerstat.entity.Comment;
import com.leverx.dealerstat.entity.User;
import com.leverx.dealerstat.repository.CommentRepository;
import com.leverx.dealerstat.security.AuthenticatedUserFactory;
import com.leverx.dealerstat.service.impl.CommentServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("UserService test")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
public class CommentServiceUnitTest {

    @InjectMocks
    private CommentServiceImpl commentsService;

    @Mock
    private CommentConverter commentConverter;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private AuthenticatedUserFactory userFactory;

    @Mock
    private UserConverter userConverter;


    @Test
    public void Given_ServiceTriesFindCommentById_When_GetCommentId_Then_FoundCommentReturned() {
        Long id = 1L;
        Comment comment = new Comment();
        CommentDTO commentDTO = new CommentDTO();
        comment.setId(id);

        Mockito.when(commentRepository.findById(id)).thenReturn(Optional.of(comment));
        Mockito.when(commentConverter.convertToDTO(comment)).thenReturn(commentDTO);

        assertEquals(commentsService.getById(id), commentDTO);

        Mockito.verify(commentRepository, Mockito.only()).findById(id);
        Mockito.verify(commentConverter, Mockito.only()).convertToDTO(comment);

    }

    @Test
    public void Given_ServiceTriesFindAllComments_When_GetNothing_Then_AllFoundCommentsReturned() {
        List<Comment> comments = new ArrayList<>();
        List<CommentDTO> commentDTOS = new ArrayList<>();
        Comment comment = new Comment();
        CommentDTO commentDTO = new CommentDTO();
        comments.add(comment);
        comments.add(comment);
        commentDTOS.add(commentDTO);
        commentDTOS.add(commentDTO);

        Mockito.when(commentRepository.findAll()).thenReturn(comments);
        Mockito.when(commentConverter.convertToDTO(comment)).thenReturn(commentDTO);

        assertEquals(commentsService.getAll(), commentDTOS);

        Mockito.verify(commentRepository, Mockito.only()).findAll();
        Mockito.verify(commentConverter, Mockito.times(2)).convertToDTO(comment);
    }

    @Test
    public void Given_ServiceTriesFindAllComments_When_GetNothing_Then_EmptyListReturned() {
        List<Comment> comments = new ArrayList<>();
        List<CommentDTO> commentDTOS = new ArrayList<>();
        Comment comment = new Comment();

        Mockito.when(commentRepository.findAll()).thenReturn(comments);

        assertEquals(commentsService.getAll(), commentDTOS);

        Mockito.verify(commentRepository, Mockito.only()).findAll();
        Mockito.verify(commentConverter, Mockito.never()).convertToDTO(comment);
    }

    @Test
    public void Given_ServiceTriesFindAllCommentsByUserId_When_GetUserId_Then_AllCommentsReturned() {
        Long id = 1L;
        List<Comment> comments = new ArrayList<>();
        List<CommentDTO> commentDTOS = new ArrayList<>();
        User user = new User();
        user.setId(id);
        Comment comment = new Comment();
        CommentDTO commentDTO = new CommentDTO();
        comment.setId(id);
        comment.setAuthor(user);
        commentDTO.setId(id);
        commentDTO.setAuthorId(id);
        comments.add(comment);
        comments.add(comment);
        commentDTOS.add(commentDTO);
        commentDTOS.add(commentDTO);

        Mockito.when(commentRepository.findAllByUserId(id)).thenReturn(comments);
        Mockito.when(commentConverter.convertToDTO(comment)).thenReturn(commentDTO);

        assertEquals(commentsService.getUserComments(id), commentDTOS);

        Mockito.verify(commentRepository, Mockito.only()).findAllByUserId(id);
        Mockito.verify(commentConverter, Mockito.times(2)).convertToDTO(comment);
    }


    @Test
    public void Given_ServiceTriesSaveComment_When_GetComment_Then_SavedCommentReturned() {
        Long id = 1L;
        Comment comment = new Comment();
        CommentDTO commentDTO = new CommentDTO();
        UserDTO author = new UserDTO();
        commentDTO.setUserId(id);
        commentDTO.setId(id);

        Mockito.when(commentRepository.save(comment)).thenReturn(comment);
        Mockito.when(userFactory.currentUser()).thenReturn(author);
        Mockito.when(commentConverter.convertToDTO(comment)).thenReturn(commentDTO);
        Mockito.when(commentConverter.convertToModel(commentDTO)).thenReturn(comment);

        assertEquals(commentsService.save(commentDTO), commentDTO);

        Mockito.verify(commentRepository, Mockito.only()).save(comment);
        Mockito.verify(commentConverter, Mockito.times(1)).convertToDTO(comment);
        Mockito.verify(commentConverter, Mockito.times(1)).convertToModel(commentDTO);
        Mockito.verify(userFactory, Mockito.only()).currentUser();
    }

    @Test
    public void Given_ServiceTriesFindAuthorByCommentId_When_GetCommentId_Then_UserReturned() {
        Long id = 1L;
        User author = new User();
        UserDTO authorDTO = new UserDTO();
        author.setId(id);
        Comment comment = new Comment();
        comment.setId(id);
        comment.setAuthor(author);

        Mockito.when(commentRepository.findById(id)).thenReturn(Optional.of(comment));
        Mockito.when(userConverter.convertToDTO(author)).thenReturn(authorDTO);

        assertEquals(commentsService.getAuthor(id), authorDTO);

        Mockito.verify(commentRepository, Mockito.only()).findById(id);
        Mockito.verify(userConverter, Mockito.only()).convertToDTO(author);
    }

    @Test
    public void Given_ServiceTriesCalculateRating_When_GetNothing_Then_RatingReturned() {
        Long userId1 = 1L;
        Long userId2 = 2L;
        List<Comment> comments = new ArrayList<>();
        Comment comment1 = new Comment();
        Comment comment2 = new Comment();
        Comment comment3 = new Comment();
        User user1 = new User();
        User user2 = new User();
        user1.setId(userId1);
        user2.setId(userId2);

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

        Map<Long, Double> rating = new HashMap<>();
        rating.put(1L, 1.5D);
        rating.put(2L, (2.5 + 5D) / 2);
        Map<Long, Double> rating2 = commentsService.getRating();
        assertEquals(rating2, rating);

        Mockito.verify(commentRepository, Mockito.only()).findAll();
    }

    @Test
    public void Given_ServiceTriesCalculateUserRating_When_GetUserId_Then_RatingReturned() {
        Long userId = 1L;
        User user = new User();
        UserDTO userDTO = new UserDTO();
        user.setId(userId);
        userDTO.setId(userId);
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

        Map<Long, Double> userRating = Collections.singletonMap(userId, (4D + 1D + 5D) / 3);

        Mockito.when(commentRepository.findAllByUserId(userId)).thenReturn(comments);
        Mockito.when(userConverter.convertToDTO(user)).thenReturn(userDTO);

        assertEquals(commentsService.getUserRating(userId), userRating);

        Mockito.verify(commentRepository, Mockito.only()).findAllByUserId(userId);
        Mockito.verify(userConverter, Mockito.only()).convertToDTO(user);
    }

    @Test
    public void Given_ServiceTriesUpdateComment_When_CommentNotFound_Then_ThrowException() {
        Long commentId = 1L;
        Comment comment = new Comment();
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(commentId);
        UserDTO userDTO = new UserDTO();
        comment.setId(commentId);

        Mockito.when(commentRepository.findById(commentId)).thenReturn(Optional.ofNullable(null));
        Mockito.when(userFactory.currentUser()).thenReturn(userDTO);

        assertThrows(NotFoundException.class, () -> commentsService.update(commentDTO));

        Mockito.verify(userFactory, Mockito.only()).currentUser();
        Mockito.verify(commentRepository, Mockito.only()).findById(commentId);
    }
}
