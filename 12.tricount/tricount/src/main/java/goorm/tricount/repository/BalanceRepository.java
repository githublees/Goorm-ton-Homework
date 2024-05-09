package goorm.tricount.repository;

import goorm.tricount.domain.Balance;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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

    public Optional<Balance> findBySettlementId(Long id) {
        return findAllBySettlementId(id).stream()
                .findFirst();
    }

    public List<Balance> findAllBySettlementId(Long id) {
        String jpql = "select b from Balance b where b.settlement.id = :id and b.balanceStatus != 'DELETE'";

        return em.createQuery(jpql, Balance.class)
                .setParameter("id", id)
                .getResultList();
    }
}
