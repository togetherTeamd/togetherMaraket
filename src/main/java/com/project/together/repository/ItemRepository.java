package com.project.together.repository;

import com.project.together.entity.Item;
import com.project.together.entity.ItemStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item) {
        if (item.getId() == null) {
            em.persist(item);
        } else {
            em.merge(item);
        }
    }
    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findByName(String name) {
        return em.createQuery("select i from Item i where i.name like :name")
                .setParameter("name", "%"+name+"%")
                .getResultList();
    }

    public List<Item> findByManner(String name) {
        return em.createQuery("select i from Item i where i.mannerItem =: score and i.name like :name")
                .setParameter("score", 1L)
                .setParameter("name","%"+name+"%")
                .getResultList();
    }

    public List<Item> findBySeller(String sellerId) {
        return em.createQuery("select i from Item i where i.seller =: sellerId", Item.class)
                .setParameter("sellerId", sellerId)
                .getResultList();
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }

    public List<Item> findSellingItem() {
        return em.createQuery("select i from Item i where i.itemStatus =: itemStatus", Item.class)
                .setParameter("itemStatus", ItemStatus.SELLING)
                .getResultList();
    }

    public List<Item> findByBuyer(String buyerId) {
        return em.createQuery("select i from Item i where i.buyer =: buyerId", Item.class)
                .setParameter("buyerId", buyerId)
                .getResultList();
    }

}
