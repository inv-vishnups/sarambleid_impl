package com.inv.scrambleid.service.impl;

import com.inv.scrambleid.entity.User;
import com.inv.scrambleid.forms.UserForm;
import com.inv.scrambleid.mapping.UserMapper;
import com.inv.scrambleid.mapping.UserViewMapper;
import com.inv.scrambleid.repository.UserRepository;
import com.inv.scrambleid.service.UserService;
import com.inv.scrambleid.view.UserView;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserViewMapper userViewMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, UserViewMapper userViewMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.userViewMapper = userViewMapper;
    }

    @Override
    public UserView createUser(UserForm userForm) {


//        OkHttpClient client = new OkHttpClient().newBuilder().build();
//        MediaType mediaType = MediaType.parse("application/json");
////        RequestBody body = RequestBody.create()


        User user = userMapper.toEntity(userForm);

        User savedUser = userRepository.save(user);

        return userViewMapper.toView(savedUser);
    }
}
