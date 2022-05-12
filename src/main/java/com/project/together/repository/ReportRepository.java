package com.project.together.repository;

import com.project.together.entity.Report;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReportRepository {

    private final EntityManager em;

    public void save(Report report) {
        em.persist(report);
    }

    public List<Report> findAll() {
        return em.createQuery("select r from Report r", Report.class)
                .getResultList();
    }
}
