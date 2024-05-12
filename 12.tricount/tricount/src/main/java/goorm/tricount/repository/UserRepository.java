package goorm.tricount.repository;

import goorm.tricount.domain.User;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager em;

    public void save(User user) {
        em.persist(user);
    }

    public User findOne(Long id) {
        return em.find(User.class, id);
    }

    public Optional<User> findByUserId(String userId, String password) {
        String jpql = "select u from User u where u.userName = :userId and u.password = :password";
        return em.createQuery(jpql, User.class)
                .setParameter("userId", userId)
                .setParameter("password", password)
                .getResultList()
                .stream()
                .findFirst();
    }
}
