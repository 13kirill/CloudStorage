package ru.netology.cloudstorage.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.netology.cloudstorage.model.entity.User.User;
import ru.netology.cloudstorage.security.jwt.JwtUser;
import ru.netology.cloudstorage.security.jwt.JwtUserFactory;
import ru.netology.cloudstorage.service.UserService;

@Service
@Slf4j
public class JwtUserDetailService implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public JwtUserDetailService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userService.findByUserName(userName);

        if(user == null){
            throw new UsernameNotFoundException("User with username: " + userName + " not found");
        }

        JwtUser jwtUser = JwtUserFactory.create(user);
        log.info("IN loadByUserName - user with username: {} successfully loaded", userName);
        return jwtUser;
    }
}
