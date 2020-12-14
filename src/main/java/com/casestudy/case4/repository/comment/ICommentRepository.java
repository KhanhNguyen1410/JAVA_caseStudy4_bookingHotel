package com.casestudy.case4.repository.comment;

import com.casestudy.case4.model.Comment;
import com.casestudy.case4.model.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ICommentRepository extends JpaRepository<Comment, Long> {
    @Query(value = "select * from comment r where r.hotel_id = :id", nativeQuery = true)
    Page<Comment> findAllByHotelId(@Param("id") Long id, Pageable pageable);
}
