package goorm.notice.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import goorm.notice.domain.Comment;
import goorm.notice.domain.CommentStatus;
import goorm.notice.domain.Notice;
import goorm.notice.domain.NoticeDTO;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class NoticeResponse {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String title;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String detail;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<CommentResponse> result = new ArrayList<>();

    public NoticeResponse(String title, String detail) {
        this.title = title;
        this.detail = detail;
    }

    public void addComments (Notice notice) {
        for (Comment comment : notice.getComments()) {
            if (comment.getCommentStatus() == CommentStatus.CLOSE) continue;
            CommentResponse commentResponse = new CommentResponse();
            commentResponse.setDetail(comment.getDetail());
            result.add(commentResponse);
        }
    }
}
