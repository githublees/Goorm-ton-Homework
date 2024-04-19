package goorm.notice.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class NoticeDTO {

    private String title;

    private String detail;

    public NoticeDTO(String title, String detail) {
        this.title = title;
        this.detail = detail;
    }
}
