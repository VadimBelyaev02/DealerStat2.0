package com.leverx.dealerstat.repository;

import com.leverx.dealerstat.model.Comment;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentsRepository {

   Optional<Comment> findById(Long id);

   List<Comment> findAllByAuthorId(Long id);

   void deleteById(Long id);

   List<Comment> findAllByApproved(boolean approved);

   List<Comment> findAllByUserId(Long id);

   void save(Comment comment);

   List<Comment> findAll();

}
