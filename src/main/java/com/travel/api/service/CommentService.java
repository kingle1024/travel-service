package com.travel.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.api.common.UtilityTravel;
import com.travel.api.repository.CommentRepository;
import com.travel.api.vo.Comment_mst;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<Comment_mst> getCommentsByProductCd(String productCd) {
        List<Comment_mst> items = commentRepository.findByProductCdOrderByInsertDtsDesc(
            productCd);

        for(Comment_mst comment : items) {
            final String maskAuthor = UtilityTravel.maskAuthor(comment.getAuthor());
            comment.setAuthor(maskAuthor);
        }

        return items;
    }

    public Comment_mst save(Comment_mst comment) {
        return commentRepository.save(comment);
    }
}
