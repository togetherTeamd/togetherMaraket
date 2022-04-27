package com.project.together.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class ChatRoom {

    private String roomId;
    private Set<WebSocketSession> sessions = new HashSet<>();

    public static ChatRoom create(){
        ChatRoom room = new ChatRoom();

        room.roomId = UUID.randomUUID().toString();
        return room;
    }
}
