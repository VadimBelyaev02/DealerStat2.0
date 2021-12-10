package com.leverx.dealerstat.repository;

import com.leverx.dealerstat.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByApproved(boolean approved);

    List<Comment> findAllByUserId(Long id);

}
