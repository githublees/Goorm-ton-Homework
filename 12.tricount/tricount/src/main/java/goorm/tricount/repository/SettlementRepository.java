package goorm.tricount.repository;

import goorm.tricount.domain.Join;
import goorm.tricount.domain.Settlement;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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
        String jpql = "select distinct s from Settlement s " +
                        "join fetch s.joins j " +
                        "where j.user.id = :id " +
                        "and j.joinStatus != 'LEAVE' " +
                        "and s.settlementStatus != 'DELETE'";

        return em.createQuery(jpql, Settlement.class)
                .setParameter("id", id)
                .getResultList();
    }

}
