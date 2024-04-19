package goorm.notice.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Notice {

    @Id @GeneratedValue
    @JoinColumn(name = "NOTICE_ID")
    private Long id;

    private String title;

    @Lob
    private String detail;

    @Enumerated(EnumType.STRING)
    private NoticeStatus noticeStatus;

    @OneToMany(mappedBy = "notice")
    private List<Comment> comments = new ArrayList<>();

    protected Notice() {
    }

    public Notice(String title, String detail) {
        this.title = title;
        this.detail = detail;
        this.noticeStatus = NoticeStatus.OPEN;
    }

    /**
     * 게시글 삭제
     */
    public void close() {
        if(this.noticeStatus == NoticeStatus.CLOSE) {
            throw new IllegalStateException("이미 삭제한 게시글 입니다.");
        }

        this.setNoticeStatus(NoticeStatus.CLOSE);
        for (Comment comment : comments) {
            comment.close();
        }
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void setNoticeStatus(NoticeStatus noticeStatus) {
        this.noticeStatus = noticeStatus;
    }

}
