package goorm.notice.service;

import goorm.notice.domain.Comment;
import goorm.notice.domain.Notice;
import goorm.notice.repository.CommentRepository;
import goorm.notice.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final NoticeRepository noticeRepository;
    private final CommentRepository commentRepository;

    /**
     * 댓글 생성
     */
    @Transactional
    public Long comment(Long noticeId, String detail) {

        //엔티티 조회
        Notice notice = noticeRepository.findOne(noticeId);

        //댓글 생성
        Comment comment = new Comment(notice, detail);

        commentRepository.save(comment);

        return comment.getId();
    }

    @Transactional
    public Long updateComment(Long commentId, String detail) {
        Comment comment = commentRepository.findOne(commentId);
        comment.setDetail(detail);
        return comment.getId();
    }

    @Transactional
    public void closeComment(Long commentId) {
        Comment comment = commentRepository.findOne(commentId);
        comment.close();
    }
}
