package goorm.tricount.repository;

import goorm.tricount.domain.Balance;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BalanceRepository {

    private final EntityManager em;

    public void save(Balance balance) {
        em.persist(balance);
    }

    public void saveAll(List<Balance> balances) {
        for (Balance balance : balances) {
            em.persist(balance);
        }
    }

    public List<Balance> findAllBySettlementId(Long id) {
        String jpql = "select b from Balance b where b.settlement.id = :id";

        return em.createQuery(jpql, Balance.class)
                .setParameter("id", id)
                .getResultList();
    }
}
