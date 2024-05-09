package goorm.tricount.repository;

import goorm.tricount.domain.Join;
import goorm.tricount.response.SettlementRes;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JoinRepository {

    private final EntityManager em;

    public void save(Join join) {
        em.persist(join);
    }

    public List<Join> findByUserId(Long id) {
        String jpql = "select j from Join j where j.user.id = :id";

        return em.createQuery(jpql, Join.class)
                .setParameter("id", id)
                .getResultList();
    }
}
