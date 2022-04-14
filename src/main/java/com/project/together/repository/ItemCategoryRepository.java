package com.project.together.repository;

import com.project.together.entity.ItemCategory;
import com.project.together.entity.Wish;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemCategoryRepository {

    private final EntityManager em;

    public void save(ItemCategory itemCategory) {
        em.persist(itemCategory);
    }

    public List<ItemCategory> findAll() {
        return em.createQuery("select ic from ItemCategory ic", ItemCategory.class)
                .getResultList();
    }
}
