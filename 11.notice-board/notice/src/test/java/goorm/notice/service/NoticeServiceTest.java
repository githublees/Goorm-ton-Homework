package goorm.notice.service;

import goorm.notice.domain.Notice;
import goorm.notice.domain.NoticeDTO;
import goorm.notice.domain.NoticeStatus;
import goorm.notice.repository.NoticeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class NoticeServiceTest {

    @Autowired NoticeService noticeService;
    @Autowired NoticeRepository noticeRepository;

    @Test
    public void 게시판_등록() throws Exception {
        //given
        NoticeDTO notice = new NoticeDTO("title", "detail");

        //when
        Long saveId = noticeService.saveNotice(notice);

        //then
        assertEquals(notice, noticeRepository.findOne(saveId));
    }

    @Test
    public void 게시글_목록_조회_커서() throws Exception {
        //given
        for (int i = 1; i <= 12; i++) {
            NoticeDTO notice = new NoticeDTO("title" + i, "detail");
            noticeService.saveNotice(notice);
        }

        //when
        List<NoticeDTO> notices = noticeRepository.findByCursor(null, 5);

        //then
        assertEquals(notices.size(), 5, "커서로 목록을 가져올 때 size 는 5이다.");
    }


    @Test
    public void 게시글_목록_조회_오프셋() throws Exception {
        //given
        for (int i = 1; i <= 12; i++) {
            NoticeDTO notice = new NoticeDTO("title" + i, "detail");
            noticeService.saveNotice(notice);
        }

        //when
        List<NoticeDTO> pageOne = noticeRepository.findByOffset(1, 5);

        //then
        assertEquals(pageOne.size(), 5, "오프셋으로 목록을 가져올 때 size 는 5이다.");
    }

    @Test
    public void 게시글_삭제() throws Exception {
        //given
        NoticeDTO notice1 = new NoticeDTO("title", "detail");
        NoticeDTO notice2 = new NoticeDTO("title", "detail");

        Long saveId1 = noticeService.saveNotice(notice1);
        Long saveId2 = noticeService.saveNotice(notice2);

        //when
        noticeService.closeNotice(saveId2);

        Notice openNotice = noticeRepository.findOne(saveId1);
        Notice closeNotice = noticeRepository.findOne(saveId2);

        //then
        assertEquals(closeNotice.getNoticeStatus(), NoticeStatus.CLOSE);
        assertNotEquals(openNotice.getNoticeStatus(), closeNotice.getNoticeStatus());
    }

    @Test
    public void 게시글_수정() throws Exception {
        //given
        NoticeDTO notice = new NoticeDTO("title", "detail");
        Long saveId = noticeService.saveNotice(notice);

        //when
        noticeService.updateNotice(saveId, "title2", "detail");

        //then
        assertEquals("title2", noticeRepository.findOne(saveId).getTitle());
    }
}