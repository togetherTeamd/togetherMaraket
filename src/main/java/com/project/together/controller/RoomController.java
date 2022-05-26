package com.project.together.controller;

import com.project.together.config.auth.PrincipalDetails;
import com.project.together.entity.ChatRoom;
import com.project.together.entity.Room;
import com.project.together.entity.User;
import com.project.together.entity.Item;
import com.project.together.repository.ChatRoomRepository;
import com.project.together.repository.RoomRepository;
import com.project.together.repository.UserRepository;
import com.project.together.service.ItemService;
import com.project.together.service.RoomService;
import com.project.together.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/chat")
@Log4j2
public class RoomController {

    private final RoomService roomService;
    private final ChatRoomRepository roomRepository;
    private final UserService userService;
    private final ItemService itemService;
    //채팅방 목록 조회
    @GetMapping(value = "/rooms")
    public ModelAndView rooms(@AuthenticationPrincipal PrincipalDetails user, Model model){

        User loginUser = userService.findById(user.getUsername());
        model.addAttribute("user", loginUser);
        log.info("# All Chat Rooms");
        ModelAndView mv = new ModelAndView("chat/rooms");

        List<Room> roomList = roomService.findByUserIdx(loginUser.getUserId());//loginUser.getRoomList();
        log.info(roomList);

        List<Item> itemList = itemService.findAll();
        mv.addObject("itemList", itemList);
        mv.addObject("list", roomList);

        return mv;
    }

    //채팅방 개설
    @GetMapping(value = "/roomMake")
    public String create(String owner, @AuthenticationPrincipal PrincipalDetails user, Long itemId){
        log.info("회원 정보:" + user.getUsername());

        User loginUser = userService.findById(user.getUsername());//채팅 버튼을 누른 사람
        Item item = itemService.findOne(itemId);
        log.info("아이템 이름:" + item.getName());

        User seller = userService.findById(owner);//아이템 등록한 사람

        //해당 아이템 정보의 판매자, 구매자가 일치할 때 방을 생성하지 않고 해당 방으로 입장
        if(roomService.findByUserAndItem(loginUser.getUserId(), seller.getUserId(), itemId) == null) {

            ChatRoom chatRoom = roomRepository.createChatRoom();//채팅방 생성

            Room room = new Room();//방 생성
            room.setRoomId(chatRoom.getRoomId());

            room.setSellerId(seller.getUserId()); //판매자
            room.setUserId(loginUser.getUserId()); //채팅방 클릭한 유저
            room.setItemIdx(itemId);
            roomService.save(room);

            return "redirect:/chat/room?roomId=" + chatRoom.getRoomId();
        } else {
            return "redirect:/chat/room?roomId=" + roomService.findByUserAndItem(loginUser.getUserId(), seller.getUserId(), itemId).getRoomId();
        }
    }

    //채팅방 조회
    @GetMapping("/room")
    public void getRoom(@AuthenticationPrincipal PrincipalDetails user, String roomId, Model model){

        User loginUser = userService.findById(user.getUsername());

        log.info("# get Chat Room, roomID : " + roomId);

        Room room = roomService.findOne(roomId);//생성된 방 조회
        /*//room.setUserIdx(loginUser.getUserIdx());

        log.info(room.getId());

        List<Room> roomList = loginUser.getRoomList(); //로그인한 유저의 방목록 조회
        if(roomList == null)
        {
            log.info("비어있음");
            roomList = new ArrayList<>(); //로그인한 유저의 방이 비어있을 경우 Room 목록을 만듬
            loginUser.setRoomList(roomList); //로그인한 유저의 방 목록에 위에서 만든 방목록 추가
        }
        boolean check = true;
        for(Room i : roomList){
            if(i.getRoomId().equals(room.getRoomId())) { //유저가 가진 방 목록중에 생성한 방의 ID와 같은 방이 있으면 방목록 추가 x
                check = false;
                break;
            }
        }

        if(check)
            //userService.addRoom(loginUser.getUserIdx(), room.getId(), room.getSellerIdx());//roomList.add(room);
        log.info("생성한 방 개수" + roomList.size());
        //log.info("안비어있음" + loginUser.getRoomList().size());*/

        model.addAttribute("user", loginUser);
        model.addAttribute("room", room);
        /*
         상대 정보를 바탕으로 채팅방에 초대(알림 기능 걱정)
         */
    }

    @GetMapping("/roomout")
    public String outRoom(@AuthenticationPrincipal PrincipalDetails user, String roomId){
        // 채팅방 나간 후 삭제 로직
        User loginUser = userService.findById(user.getUsername());
        //Room room = roomService.findOne(roomId);
        roomService.checkoutRoom(loginUser.getUserId(), roomId);//로그인한 유저의 정보가 담긴 채팅방 나가기

        return "redirect:/";
        /*userService.rmRoom(loginUser.getUserIdx(), room.getId());

        return "redirect:/";*/
    }
}
