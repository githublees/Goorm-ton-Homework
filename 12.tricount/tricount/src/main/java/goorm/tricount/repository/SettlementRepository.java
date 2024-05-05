package goorm.tricount.repository;

import goorm.tricount.domain.Settlement;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SettlementRepository {

    private final EntityManager em;

    public void save(Settlement settlement) {
        em.persist(settlement);
    }

    public Settlement findOne(Long id) {
        return em.find(Settlement.class, id);
    }
    public List<Settlement> findAllByUserId(Long id) {
        String jpql = "select s from Settlement s " +
                        "join s.joins j " +
                        "join j.user u " +
                        "where u.id = :id " +
                        "and s.settlementStatus != 'DELETE'";

        return em.createQuery(jpql, Settlement.class)
                .setParameter("id", id)
                .getResultList();
    }

}
