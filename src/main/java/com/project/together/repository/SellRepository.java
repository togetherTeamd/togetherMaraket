package com.project.together.repository;

import com.project.together.entity.Sell;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class SellRepository {

    private final EntityManager em;

    public void save(Sell sell) {
        em.persist(sell);
    }

    public Sell findOne(Long id) {
        return em.find(Sell.class, id);
    }
}
