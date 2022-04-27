package com.project.together.repository;

import com.project.together.entity.Address;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MapRepository {

    private final EntityManager em;

    public void save(Address address) { em.persist(address);}

    public List<Address> findById(String id) {
        return em.createQuery("select m from position m where m.userId =:id",Address.class)
                .setParameter("id", id)
                .getResultList();
    }
    public List<Address> findAll() {
        return em.createQuery("select m from Address m", Address.class)
                .getResultList();
    }
}
