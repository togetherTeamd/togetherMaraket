package com.project.together.repository;

import com.project.together.entity.Sell;
import com.project.together.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

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

    public List<Sell> findAll() {
        return em.createQuery("select s from Sell s", Sell.class)
                .getResultList();
    }

}
