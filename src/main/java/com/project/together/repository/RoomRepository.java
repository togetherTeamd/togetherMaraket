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

    public List<Room> findOne(String roomId) {
        return em.createQuery("select i from Room i where i.roomId =: roomId")
                .setParameter("roomId", roomId)
                .getResultList();
    }

    public List<Room> findAll() {
        return em.createQuery("select s from Room s", Room.class)
                .getResultList();
    }

    public void removeRoom(String roomId) {
        em.createQuery("delete from Room r where r.roomId =: roomId")
                .setParameter("roomId", roomId)
                .executeUpdate();
    }

    public List<Room> findByUserAndItem(String userId, String sellerId, Long itemIdx) {
        return em.createQuery("select r from Room r where r.userId =: userId and r.sellerId =: sellerId and " +
                "r.itemIdx =: itemIdx")
                .setParameter("userId", userId)
                .setParameter("sellerId", sellerId)
                .setParameter("itemIdx", itemIdx)
                .getResultList();
    }

    public List<Room> findByUserIdx(String userId) {
        return em.createQuery("select r from Room r where r.sellerId =: userId or r.userId =: userId")
                .setParameter("userId", userId)
                .getResultList();
    }
}
