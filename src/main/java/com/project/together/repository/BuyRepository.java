package com.project.together.repository;

import com.project.together.entity.Buy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class BuyRepository {

    private final EntityManager em;

    public void save(Buy buy) {
        em.persist(buy);
    }

    public Buy findOne(Long id) {
     return em.find(Buy.class, id);
    }
}
