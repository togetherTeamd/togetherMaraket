package com.project.together.service;

import com.project.together.entity.Message;
import com.project.together.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MessageService {
    private final MessageRepository messageRepository;

    @Transactional
    public void save(Message message) { messageRepository.save(message); }

    public Message findById(Long id) { return messageRepository.findById(id); }

    public List<Message> findByRoomId(String roomId) { return messageRepository.findByRoomId(roomId); }
}
