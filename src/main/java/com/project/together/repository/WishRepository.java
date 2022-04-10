package com.project.together.repository;

import com.project.together.entity.Wish;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class WishRepository {

    private final EntityManager em;

    public void save(Wish wish) {
        em.persist(wish);
    }

    public List<Wish> findByUser(Long userIdx) {
       return em.createQuery("select w from Wish w where w.user.userIdx =: userIdx " +
                       "and w.isCancel =: number")
                .setParameter("userIdx", userIdx)
               .setParameter("number", 0)
               .getResultList();
    }

    public List<Wish> findByItem(Long itemId, Long userIdx) {
        return em.createQuery("select w from Wish w where w.wishItem.item.id =: itemId " +
                        "and w.user.userIdx =: userIdx")
                .setParameter("itemId", itemId)
                .setParameter("userIdx", userIdx)
                .getResultList();
    }
}
