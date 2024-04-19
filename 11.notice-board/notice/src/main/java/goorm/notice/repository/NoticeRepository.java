package goorm.notice.repository;

import goorm.notice.domain.Notice;
import goorm.notice.domain.NoticeDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class NoticeRepository {

    private final EntityManager em;

    public void save(Notice notice) {
        em.persist(notice);
    }

    public Notice findOne(Long id) {
        return em.find(Notice.class, id);
    }

    public List<Notice> findAll() {
        return em.createQuery("select n from Notice n", Notice.class)
                .getResultList();
    }

    public List<NoticeDTO> findByOffset(int pageNo, int pageSize) {

        return em.createQuery(
                "select new goorm.notice.domain.NoticeDTO(n.title, n.detail) from Notice n where n.noticeStatus != 'CLOSE' order by n.id desc"
                        , NoticeDTO.class
                )
                .setMaxResults(pageSize)
                .setFirstResult((pageNo - 1) * pageSize)
                .getResultList();
    }
    public List<NoticeDTO> findByCursor(Long noticeId, int pageSize) {

        String jpql = "select new goorm.notice.domain.NoticeDTO(n.title, n.detail) from Notice n";

        if(noticeId != null) {
            jpql += " where n.id < :id and";
        } else {
            jpql += " where";
        }

        jpql += " n.noticeStatus != 'CLOSE' order by n.id desc";

        TypedQuery<NoticeDTO> query = em.createQuery(jpql, NoticeDTO.class)
                .setMaxResults(pageSize);

        if(noticeId != null) {
            query.setParameter("id", noticeId);
        }

        return query.getResultList();
    }
}
