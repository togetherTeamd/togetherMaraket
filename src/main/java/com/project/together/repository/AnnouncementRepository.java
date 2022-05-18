package com.project.together.repository;

import com.project.together.entity.Announcement;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AnnouncementRepository {

    private final EntityManager em;

    public void save(Announcement announcement) {

        if (announcement.getId() == null) {
            em.persist(announcement);
        } else {
            em.merge(announcement);
        }
    }

    public List<Announcement> findAll() {
        return em.createQuery("select i from Announcement i", Announcement.class)
                .getResultList();
    }

    public Announcement findOne(Long id) {
        return em.find(Announcement.class, id);
    }

    /*public List<Inquiry> findByUserIdx(Long userIdx) {
        return em.createQuery("select i from Announcement i where i.userIdx =: userIdx")
                .setParameter("userIdx",userIdx)
                .getResultList();
    }*/

    public void announcementCancel(Long aId) {
//        em.createQuery("delete from Wish w where  w.id =: wishId and w.user.userIdx =: userIdx")
//                .setParameter("userIdx", userIdx)
//                .setParameter("wishId", wishId)
//                .executeUpdate();

        em.createQuery("delete from Announcement a where a.id =: aId")
                .setParameter("aId", aId)
                .executeUpdate();
    }
}
