package com.greenmile.desafio.service.Implementation;

import com.greenmile.desafio.domain.User;
import com.greenmile.desafio.repository.UserRepository;
import com.greenmile.desafio.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl( UserRepository userRepository ){
        this.userRepository = userRepository;
    }

    @Override
    public User save( User user ) {
        user.setPassword( new BCryptPasswordEncoder().encode( user.getPassword() ) );
        return userRepository.save( user );
    }

    @Override
    public Page<User> getUsers( Pageable pageable ){
        return userRepository.findAll( pageable );
    }

    @Override
    public User getUserByUsername( String  email ){
        return userRepository.findByEmail( email );
    }


}
