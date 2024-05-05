package goorm.tricount.repository;

import goorm.tricount.domain.Join;
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
}
