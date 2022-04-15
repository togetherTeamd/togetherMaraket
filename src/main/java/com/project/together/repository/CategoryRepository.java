package com.project.together.repository;

import com.project.together.entity.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CategoryRepository {

    private final EntityManager em;

    public void save(Category category) {
        em.persist(category);
    }

    public List<Category> findById(Long categoryId) {
        return em.createQuery("select  c from Category c where c.id=:categoryId", Category.class)
                .setParameter("categoryId", categoryId)
                .getResultList();
    }

    public List<Category> findAll() {
        return em.createQuery("select c from Category c", Category.class)
                .getResultList();
    }

    /*public List<Category> findByItem(Long itemId) {
        return em.createQuery("select c from Category c where ")
    }*/
}
