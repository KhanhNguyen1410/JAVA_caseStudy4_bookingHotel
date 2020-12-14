package com.casestudy.case4.service.comment;

import com.casestudy.case4.model.Comment;
import com.casestudy.case4.model.Hotel;
import com.casestudy.case4.repository.comment.ICommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentServiceIPL implements ICommentService {

    @Autowired
    private ICommentRepository iCommentRepository;

    @Override
    public Iterable<Comment> findAll() {
        return iCommentRepository.findAll();
    }

    @Override
    public Comment save(Comment comment) {
        return iCommentRepository.save(comment);
    }

    @Override
    public Optional<Comment> findById(Long id) {
        return iCommentRepository.findById(id);
    }

    @Override
    public void remove(Long id) {
        iCommentRepository.deleteById(id);
    }

    @Override
    public Page<Comment> findAllByHotelId(Long id, Pageable pageable) {
        return iCommentRepository.findAllByHotelId(id, pageable);
    }
}
