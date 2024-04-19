package goorm.notice.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Comment {

    @Id @GeneratedValue
    @JoinColumn(name = "COMMENT_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "NOTICE_ID")
    private Notice notice;

    private String detail;

    @Enumerated(EnumType.STRING)
    private CommentStatus commentStatus;

    protected Comment() {
    }

    public Comment(Notice notice, String detail) {
        this.notice = notice;
        this.detail = detail;
        this.commentStatus = CommentStatus.OPEN;
    }

    //==비즈니스 로직==/
    /**
     * 댓글 취소
     */
    public void close() {
        this.setCommentStatus(CommentStatus.CLOSE);
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void setCommentStatus(CommentStatus commentStatus) {
        this.commentStatus = commentStatus;
    }
}
