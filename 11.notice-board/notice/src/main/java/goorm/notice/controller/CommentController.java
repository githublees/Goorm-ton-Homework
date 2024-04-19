package goorm.notice.controller;

import goorm.notice.request.CommentReq;
import goorm.notice.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comment")
    public void add(
            @RequestParam Long noticeId,
            @RequestBody CommentReq comment
    ) {
        commentService.comment(noticeId, comment.getDetail());
    }

    @PutMapping("/comment/{commentId}")
    public void update(
            @PathVariable Long commentId,
            @RequestBody CommentReq comment
    ) {
        commentService.updateComment(commentId, comment.getDetail());
    }

    @PostMapping("/comment/{commentId}/close")
    public void delete(@PathVariable Long commentId) {
        commentService.closeComment(commentId);
    }
}
