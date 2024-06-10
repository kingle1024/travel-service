package com.travel.api.controller;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.travel.api.service.CommentService;
import com.travel.api.vo.Comment_mst;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/comment")
    public Comment_mst addComment(@RequestBody Comment_mst comment) {
        comment.setAuthor("user_"+ UUID.randomUUID().toString().substring(0, 5));
        comment.setInsertDts(LocalDateTime.now());

        return commentService.save(comment);
    }
}
