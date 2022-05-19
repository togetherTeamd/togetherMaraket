package com.project.together.service;

import com.project.together.entity.*;
import com.project.together.repository.RoomRepository;
import com.project.together.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    /**
     * 회원가입
     */
    @Transactional
    public Long join(User user) {
        validateDuplicateUser(user);
        userRepository.save(user);
        return user.getUserIdx();
    }

    @Transactional
    public Long update(User user){
        userRepository.merge(user);
        return user.getUserIdx();
    }

    private void validateDuplicateUser(User user) {
        List<User> findUsers = userRepository.findById(user.getUserId());
        if(!findUsers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 아이디 입니다.");
        }
    }

    public int idCheck(String id) {
        if(!userRepository.findById(id).isEmpty())
            return 1;
        return 0;
    }

    public User findById(String userId) {
        if(userRepository.findById(userId).isEmpty())
            throw new IllegalStateException("회원 정보 없음");
        return userRepository.findById(userId).get(0);
    }

    public User findByIdx(Long userIdx) {
        return userRepository.findByIdx(userIdx);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional
    public void setKakaoQR(Long userIdx, String QR) {
        User user = userRepository.findByIdx(userIdx);
        user.setKakaoQr(QR);
    }

    @Transactional
    public void setTossQR(Long userIdx, String QR) {
        User user = userRepository.findByIdx(userIdx);
        user.setTossQr(QR);
    }

    @Transactional
    public void addRoom(Long userIdx, Long roomIdx) {
        User user = userRepository.findByIdx(userIdx);
        Room room = roomRepository.findById(roomIdx);
        room.setUserIdx(userIdx);
        List<Room> roomList = user.getRoomList();
        if(roomList == null)
            roomList = new ArrayList<>();

        roomList.add(room);
    }

    public List<User> findByPhone(String phone) {
        return userRepository.findByPhone(phone);
    }

    public List<User> findByReport() {
        return userRepository.findByReport();
    }
}
