package com.project.together.controller;

import com.project.together.config.auth.PrincipalDetails;
import com.project.together.entity.ChatRoom;
import com.project.together.entity.Room;
import com.project.together.entity.User;
import com.project.together.repository.ChatRoomRepository;
import com.project.together.repository.RoomRepository;
import com.project.together.service.RoomService;
import com.project.together.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/chat")
@Log4j2
public class RoomController {

    private final RoomService roomService;
    private final ChatRoomRepository roomRepository;
    private final UserService userService;
    //채팅방 목록 조회
    @GetMapping(value = "/rooms")
    public ModelAndView rooms(@AuthenticationPrincipal PrincipalDetails user, Model model){

        User loginUser = userService.findById(user.getUsername());

        model.addAttribute("user", loginUser);
        log.info("# All Chat Rooms");
        ModelAndView mv = new ModelAndView("chat/rooms");

        List<Room> roomList = loginUser.getRoomList();
        log.info(roomList);

        mv.addObject("list", roomList);

        return mv;
    }

    //채팅방 개설
    @GetMapping(value = "/roomMake")
    public String create(String owner){
        ChatRoom chatRoom = roomRepository.createChatRoom();
        Room room = new Room();
        room.setRoomId(chatRoom.getRoomId());
        roomService.save(room);

        return "redirect:/chat/room?roomId="+chatRoom.getRoomId();
    }

    //채팅방 조회
    @GetMapping("/room")
    public void getRoom(@AuthenticationPrincipal PrincipalDetails user, String roomId, Model model){

        User loginUser = userService.findById(user.getUsername());

        log.info("# get Chat Room, roomID : " + roomId);

        Room room = roomService.findOne(roomId);

        List<Room> roomList = loginUser.getRoomList();
        if(roomList==null)
        {
            roomList = new ArrayList<Room>();
            loginUser.setRoomList(roomList);
        }

        boolean check = true;
        for(Room i : roomList){
            if(i.getRoomId().equals(room.getRoomId())) {
                check = false;
                break;
            }
        }

        if(check)
            roomList.add(room);

        model.addAttribute("user", loginUser);
        model.addAttribute("room", room);
        /*
         상대 정보를 바탕으로 채팅방에 초대(알림 기능 걱정)
         */


    }
}
