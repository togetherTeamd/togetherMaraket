package com.project.together.service;

import com.project.together.entity.Room;
import com.project.together.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoomService {
    private final RoomRepository roomRepository;

    @Transactional
    public void save(Room room) {
        roomRepository.save(room);
    }

    public Room findOne(String id) {
        return roomRepository.findOne(id).get(0);
    }

    public Room findById(Long id) { return roomRepository.findById(id); }

    public List<Room> findAll() {return roomRepository.findAll();}

    @Transactional
    public void checkoutRoom(String userId, String roomId) {
        Room room = roomRepository.findOne(roomId).get(0);
        if (userId.equals(room.getUserId())) {
            room.setUserId(null);
        } else {
            room.setSellerId(null);
        }
        if (room.getUserId() == null && room.getSellerId() == null)
            roomRepository.removeRoom(roomId);

        //return room.getId();
    }

    public Room findByUserAndItem(String userId, String sellerId, Long itemIdx) {
        if(roomRepository.findByUserAndItem(userId, sellerId, itemIdx).isEmpty())
            return null;
        return roomRepository.findByUserAndItem(userId, sellerId, itemIdx).get(0);
    }

    public List<Room> findByUserIdx(String userId) {return roomRepository.findByUserIdx(userId);}
}
