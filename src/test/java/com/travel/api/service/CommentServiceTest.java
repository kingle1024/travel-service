package com.travel.api.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.travel.api.repository.CommentRepository;
import com.travel.api.vo.Comment_mst;

class CommentServiceTest {
    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentService commentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetCommentsByProductCd() {
        // Given
        Comment_mst comment1 = new Comment_mst();
        comment1.setAuthor("John Doe");
        comment1.setProductCd("P001");

        Comment_mst comment2 = new Comment_mst();
        comment2.setAuthor("Jane Smith");
        comment2.setProductCd("P001");

        when(commentRepository.findByProductCdOrderByInsertDtsDesc("P001"))
            .thenReturn(Arrays.asList(comment1, comment2));

        // When
        List<Comment_mst> comments = commentService.getCommentsByProductCd("P001");

        // Then
        assertEquals(2, comments.size());
        assertEquals("J*******", comments.get(0).getAuthor()); // assuming maskAuthor masks to "**** Doe"
        assertEquals("J*********", comments.get(1).getAuthor()); // assuming maskAuthor masks to "**** Smith"
    }

    @Test
    void testSave() {
        // Given
        Comment_mst comment = new Comment_mst();
        comment.setAuthor("John Doe");
        comment.setProductCd("P001");

        when(commentRepository.save(any(Comment_mst.class))).thenReturn(comment);

        // When
        Comment_mst savedComment = commentService.save(comment);

        // Then
        assertNotNull(savedComment);
        assertEquals("John Doe", savedComment.getAuthor());
        verify(commentRepository, times(1)).save(comment);
    }
}
