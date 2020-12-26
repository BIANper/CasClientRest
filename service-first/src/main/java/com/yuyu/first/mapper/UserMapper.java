package com.yuyu.first.mapper;

import com.yuyu.first.po.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {

    User findUserByUsername(String username);

}
