package com.project.together.repository;

import com.project.together.entity.Files;
import com.project.together.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.io.File;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Repository
public class FileRepository {
    private final EntityManager em;

    public void save(Files file) {
        em.persist(file);
    }

    public List<Files> findAll() {
        return em.createQuery("select f from Files f", Files.class)
                .getResultList();
    }

    public Files findById(Long id) {
        return em.find(Files.class, id);
    }
    public Optional<Files> findOne(Long fileId) {
        return this.findAll().stream()
                .filter(m -> m.getId().equals(fileId))
                .findFirst();
    }

    public List<Files> findByItem(Long itemId) {
        return em.createQuery("select f from Files f where f.itemId =: itemId", Files.class)
                .setParameter("itemId",itemId)
                .getResultList();
    }

}
