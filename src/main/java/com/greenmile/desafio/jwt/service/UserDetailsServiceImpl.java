package com.greenmile.desafio.jwt.service;

import com.greenmile.desafio.domain.User;
import com.greenmile.desafio.repository.UserRepository;
import com.greenmile.desafio.service.Implementation.UserServiceImpl;
import com.greenmile.desafio.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service(value = "myUserServiceDetails")
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserService userService;

    @Autowired
    public UserDetailsServiceImpl( UserService userService ){
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user =  userService.getUserByUsername( username );

        return org.springframework.security.core.userdetails.User.withUsername( user.getEmail() )
                .password( user.getPassword() )
                .authorities("admin")
                .build();

    }

}
