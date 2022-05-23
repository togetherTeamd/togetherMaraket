package com.project.together.controller;

import com.project.together.config.auth.PrincipalDetails;
import com.project.together.entity.ChatMessage;
import com.project.together.entity.Room;
import com.project.together.entity.User;
import com.project.together.repository.ChatRoomRepository;
import com.project.together.service.RoomService;
import com.project.together.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class StompChatController {

    private final SimpMessagingTemplate template; //특정 Broker로 메세지를 전달

    private final RoomService roomService;
    private final ChatRoomRepository roomRepository;
    private final UserService userService;

    @MessageMapping(value = "/chat/enter")
    public void enter(ChatMessage message){
        message.setMessage(message.getWriter() + "님이 채팅방에 참여하였습니다.");
        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }

    @MessageMapping(value = "/chat/message")
    public void message(ChatMessage message){
        SimpleDateFormat format = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");
        Date time = new Date();
        String time2 = format.format(time);
        String printMessage = message.getMessage() + "     (" + time2 + ")";
        message.setMessage(printMessage);
        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }

    @MessageMapping(value = "/chat/out")
    public void out(ChatMessage message){
        message.setMessage(message.getWriter() + "님이 채팅방에 퇴장했습니다.");
        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }
}
