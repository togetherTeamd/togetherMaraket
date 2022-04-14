package com.project.together.repository;

import com.project.together.entity.Wish;
import com.project.together.entity.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;


@Repository
@RequiredArgsConstructor
public class WishRepository {

    private final EntityManager em;

    public void save(Wish wish) {
        em.persist(wish);
    }

    public List<Wish> findByUser(Long userIdx) {
       return em.createQuery("select w from Wish w where w.user.userIdx =: userIdx", Wish.class)
                .setParameter("userIdx", userIdx)
               .getResultList();
    }

    public List<Wish> findByItem(Long itemId, Long userIdx) {
        return em.createQuery("select w from Wish w where w.id =: itemId " +
                        "and w.user.userIdx =: userIdx", Wish.class)
                .setParameter("itemId", itemId)
                .setParameter("userIdx", userIdx)
                .getResultList();
    }

    public void wishCancel(Long userIdx, Long wishId) {
        em.createQuery("delete from Wish w where  w.id =: wishId and w.user.userIdx =: userIdx")
                .setParameter("userIdx", userIdx)
                .setParameter("wishId", wishId)
                .executeUpdate();
    }

    public List<Wish> findByUserItem(Long userIdx, Long wishId) {

       return em.createQuery("select w from Wish w where w.user.userIdx =: userIdx and w.id =: wishId", Wish.class)
                .setParameter("userIdx", userIdx)
                .setParameter("wishId", wishId)
                .getResultList();
    }

    /*public List<Item> findByWishId(Long wishId) {
        em.createQuery("select w from Wish w where w.id =: wishId ")
    }*/
}
