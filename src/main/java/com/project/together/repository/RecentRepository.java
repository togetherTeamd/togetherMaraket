package com.project.together.repository;

import com.project.together.entity.Recent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class RecentRepository {

    private final EntityManager em;

    public void save(Recent recent) {
        em.persist(recent);
    }

    public List<Recent> findByUserIdx(Long userIdx) {
        return em.createQuery("select r from Recent r where r.userIdx =: userIdx order by r.id desc ")
                .setParameter("userIdx",userIdx)
                .getResultList();
    }

    public void deleteRecent(Long id) {
        em.createQuery("delete from Recent r where r.id =: id")
                .setParameter("id", id)
                .executeUpdate();
    }

    public void deleteDuplicate(Long itemId, Long userIdx) {
        em.createQuery("delete from Recent r where r.itemId =: itemId and r.userIdx =: userIdx")
                .setParameter("itemId", itemId )
                .setParameter("userIdx", userIdx)
                .executeUpdate();
    }
}
