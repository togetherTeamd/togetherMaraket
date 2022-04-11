package com.project.together.repository;

import com.project.together.VO.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {
    int joinUser(UserVO user);

    List<UserVO> selectUser(UserVO user);

    int updateUser(UserVO user);
}
