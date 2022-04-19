package com.project.together.repository;

import com.project.together.entity.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReviewRepository {

    private final EntityManager em;

    public void save(Review review) {
        em.persist(review);
    }

    public List<Review> findAll() {
        return em.createQuery("select r from Review r", Review.class)
                .getResultList();
    }

    public Review findOne(Long id) {
        return em.find(Review.class, id);
    }

    public List<Review> findByUser(Long userIdx) {
        return em.createQuery("select r from Review r where r.user.userIdx =: userIdx")
                .setParameter("userIdx", userIdx)
                .getResultList();
    }
}
