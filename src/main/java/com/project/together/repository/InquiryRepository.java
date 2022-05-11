package com.project.together.repository;

import com.project.together.entity.Inquiry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class InquiryRepository {

    private final EntityManager em;

    public void save(Inquiry inquiry) {

        if (inquiry.getId() == null) {
            em.persist(inquiry);
        } else {
            em.merge(inquiry);
        }
    }

    public List<Inquiry> findAll() {
        return em.createQuery("select i from Inquiry i", Inquiry.class)
                .getResultList();
    }

    public Inquiry findOne(Long id) {
        return em.find(Inquiry.class, id);
    }

    public List<Inquiry> findByUserIdx(Long userIdx) {
        return em.createQuery("select i from Inquiry i where i.userIdx =: userIdx")
                .setParameter("userIdx",userIdx)
                .getResultList();
    }
}
