package goorm.notice.controller;

import goorm.notice.domain.Notice;
import goorm.notice.domain.NoticeDTO;
import goorm.notice.repository.NoticeRepository;
import goorm.notice.response.ApiResponse;
import goorm.notice.response.NoticeResponse;
import goorm.notice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;
    private final NoticeRepository noticeRepository;

    @PostMapping("/notice")
    public void add(@RequestBody NoticeDTO noticeDTO) {
        Long id = noticeService.saveNotice(noticeDTO);
    }

    @PutMapping("/notice/{noticeId}")
    public void update(
            @PathVariable Long noticeId,
            @RequestBody NoticeDTO noticeDTO) {

        Notice notice = noticeService.updateNotice(noticeId, noticeDTO.getTitle(), noticeDTO.getDetail());
    }

    @PostMapping("/notice/{noticeId}/close")
    public void delete(@PathVariable Long noticeId) {
        noticeService.closeNotice(noticeId);
    }

    @GetMapping("/notice/{noticeId}")
    public ApiResponse<NoticeResponse> getNotice(@PathVariable Long noticeId) {
        Notice notice = noticeRepository.findOne(noticeId);
        NoticeResponse noticeResponse = new NoticeResponse(notice.getTitle(), notice.getDetail());
        noticeResponse.addComments(notice);

        return makeApiResponse(noticeResponse);

    }

    @GetMapping("/notices")
    public ApiResponse<NoticeDTO> getNoticeOffset(
            @RequestParam("pageNo") int pageNo,
            @RequestParam("pageSize") int pageSize
    ) {
        List<NoticeDTO> byOffset = noticeRepository.findByOffset(pageNo, pageSize);

        for (NoticeDTO noticeDTO : byOffset) {
            System.out.println("noticeDTO = " + noticeDTO.getTitle() + "  " + noticeDTO.getDetail());
        }

        return makeApiResponse(byOffset);
    }

    @GetMapping("/notices/{noticeId}")
    public ApiResponse<NoticeDTO> getNoticeCursor(
            @PathVariable Long noticeId,
            @RequestParam("pageSize") int pageSize
    ) {
        List<NoticeDTO> byCursor = noticeRepository.findByCursor(noticeId, pageSize);

        return makeApiResponse(byCursor);
    }

    public <T> ApiResponse<T> makeApiResponse(List<T> results) {
        return new ApiResponse<>(results);
    }

    public <T> ApiResponse<T> makeApiResponse(T result) {
        return new ApiResponse<>(result);
    }
}
