package com.project.together.repository;

import com.project.together.entity.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MessageRepository {
    private final EntityManager em;

    public void save(Message message) { em.persist(message); }

    public Message findById(Long id) { return em.find(Message.class, id); }

    public List<Message> findByRoomId(String roomId) {
        return em.createQuery("select i from Message i where i.roomId =: roomId")
                .setParameter("roomId", roomId)
                .getResultList();
    }
}
