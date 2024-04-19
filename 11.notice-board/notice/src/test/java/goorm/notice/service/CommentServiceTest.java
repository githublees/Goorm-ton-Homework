package goorm.notice.service;

import goorm.notice.domain.Comment;
import goorm.notice.domain.CommentStatus;
import goorm.notice.domain.Notice;
import goorm.notice.repository.CommentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@Transactional
@SpringBootTest
class CommentServiceTest {

    @Autowired CommentService commentService;
    @Autowired CommentRepository commentRepository;
    @Autowired NoticeService noticeService;

    @Test
    public void 댓글_등록() throws Exception {
        //given
        Notice notice = new Notice("title", "detail");
        Long saveId = noticeService.saveNotice(notice);

        //when
        Long commentId = commentService.comment(saveId, "댓글");

        //then
        Comment getComment = commentRepository.findOne(commentId);

        assertEquals(CommentStatus.OPEN, getComment.getCommentStatus(), "댓글 등록 시 상태는 OPEN");
        assertEquals("댓글", getComment.getDetail(), "현재 댓글 내용은 댓글");
    }

    @Test
    public void 댓글_수정() throws Exception {
        //given
        Notice notice = new Notice("title", "detail");
        Long saveId = noticeService.saveNotice(notice);

        Long commentId = commentService.comment(saveId, "댓글");

        Long updateId = commentService.updateComment(commentId, "detail");

        //when
        Comment getComment = commentRepository.findOne(updateId);

        //then
        assertEquals("detail", getComment.getDetail(), "업데이트된 댓글 내용은 detail");
    }

    @Test
    public void 댓글_삭제() throws Exception {
        //given
        Notice notice = new Notice("title", "detail");
        Long saveId = noticeService.saveNotice(notice);

        Long commentId = commentService.comment(saveId, "댓글");

        //when
        Comment comment = commentRepository.findOne(commentId);

        commentService.closeComment(commentId);

        //then
        assertEquals(CommentStatus.CLOSE, comment.getCommentStatus(), "현재 댓글은 CLOSE");
    }
}