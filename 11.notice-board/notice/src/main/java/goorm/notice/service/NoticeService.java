package goorm.notice.service;

import goorm.notice.domain.Notice;
import goorm.notice.domain.NoticeDTO;
import goorm.notice.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;

    @Transactional
    public Long saveNotice(NoticeDTO noticeDTO) {
        Notice notice = new Notice(noticeDTO.getTitle(), noticeDTO.getDetail());
        noticeRepository.save(notice);
        return notice.getId();
    }

    @Transactional
    public void closeNotice(Long noticeId) {
        Notice notice = noticeRepository.findOne(noticeId);
        notice.close();
    }

    @Transactional
    public Notice updateNotice(Long noticeId, String title, String detail) {
        Notice notice = noticeRepository.findOne(noticeId);
        notice.setTitle(title);
        notice.setDetail(detail);
        return notice;
    }
}
