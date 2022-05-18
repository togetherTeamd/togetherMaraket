package com.project.together.repository;

import com.project.together.entity.Room;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class RoomRepository {
    private final EntityManager em;

    public void save(Room room) {
        em.persist(room);
    }

    public Room findById(Long id) {
        return em.find(Room.class, id);
    }

    public Room findOne(String roomId) {
        List<Room> roomList = em.createQuery("select i from Room i where i.roomId =: roomId")
                .setParameter("roomId", roomId)
            .getResultList();
        return roomList.get(0);
    }

    public List<Room> findAll() {
        return em.createQuery("select s from Room s", Room.class)
                .getResultList();
    }
}
