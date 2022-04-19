package com.project.together.repository;

import com.project.together.entity.ReviewContents;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReviewContentsRepository {
    private final EntityManager em;

    public void save(ReviewContents reviewContents) {
        em.persist(reviewContents);
    }

    public List<ReviewContents> findAll() {
        return em.createQuery("select rc from ReviewContents rc", ReviewContents.class)
                .getResultList();
    }

    public ReviewContents findOne(Long id) {
        return em.find(ReviewContents.class, id);
    }
}
