package goorm.tricount.repository;

import goorm.tricount.domain.Expense;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ExpenseRepository {

    private final EntityManager em;

    public void save(Expense expense) {
        em.persist(expense);
    }

    public Expense findOne(Long id) {
        return em.find(Expense.class, id);
    }

    public List<Expense> findAllBySettlementId(Long id) {
        String jpql = "select e from Expense e where e.settlement.id = :id";

        return em.createQuery(jpql, Expense.class)
                .setParameter("id", id)
                .getResultList();
    }
}
