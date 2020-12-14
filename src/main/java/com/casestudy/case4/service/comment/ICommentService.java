package com.casestudy.case4.service.comment;

import com.casestudy.case4.model.Comment;
import com.casestudy.case4.model.Hotel;
import com.casestudy.case4.service.GeneralService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

public interface ICommentService  extends GeneralService<Comment> {
    Page<Comment> findAllByHotelId(Long id, Pageable pageable);
}
