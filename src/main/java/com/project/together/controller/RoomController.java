package com.project.together.controller;

import com.project.together.entity.ChatRoom;
import com.project.together.entity.User;
import com.project.together.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/chat")
@Log4j2
public class RoomController {

    private final ChatRoomRepository repository;

    //채팅방 목록 조회
    @GetMapping(value = "/rooms")
    public ModelAndView rooms(@SessionAttribute(name = SessionConstants.LOGIN_USER, required = false) User loginUser,
                              Model model){
        model.addAttribute("user", loginUser);
        log.info("# All Chat Rooms");
        ModelAndView mv = new ModelAndView("chat/rooms");

        List<ChatRoom> roomList = loginUser.getRoomList();

        mv.addObject("list", roomList);

        return mv;
    }

    //채팅방 개설
    @GetMapping(value = "/roomMake")
    public String create(String owner){
        ChatRoom chatRoom = repository.createChatRoom();

        return "redirect:/chat/room?roomId="+chatRoom.getRoomId();
    }

    //채팅방 조회
    @GetMapping("/room")
    public void getRoom(@SessionAttribute(name = SessionConstants.LOGIN_USER, required = false) User loginUser,
                        String roomId, Model model){

        log.info("# get Chat Room, roomID : " + roomId);

        ChatRoom chatRoom = repository.findRoomById(roomId);

        List<ChatRoom> roomList = loginUser.getRoomList();
        if(!roomList.contains(chatRoom))
            roomList.add(chatRoom);

        model.addAttribute("user", loginUser);
        model.addAttribute("room", chatRoom);
    }
}
