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
}
