package com.leverx.dealerstat.repository;

import com.leverx.dealerstat.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentsRepository extends JpaRepository<Comment, Long> {

   Optional<Comment> findById(Long id);

   List<Comment> findAllByAuthorId(Long id);

   void deleteById(Long id);

   List<Comment>findAllByApproved(boolean approved);

   List<Comment> findAllByUserId(Long id);
   //@Query(nativeQuery = true,
    //       value = "SELECT user SUM(comments.rate) FROM comments JOIN users ON comments.user_id = users.id")// +
                   //" group by comments.user_id order by comments.rate")
  // Optional<Map<User, Double>> findTopByRate();

 //   Optional<List<Comment>> findAll();
}
