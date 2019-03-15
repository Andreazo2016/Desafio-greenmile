package com.greenmile.desafio.service.Implementation;

import com.greenmile.desafio.domain.RegisterTime;
import com.greenmile.desafio.domain.User;
import com.greenmile.desafio.exception.UserNotFoundException;
import com.greenmile.desafio.repository.UserRepository;
import com.greenmile.desafio.service.RegisterTimeService;
import com.greenmile.desafio.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private RegisterTimeService registerTimeService;

    @Autowired
    public UserServiceImpl( RegisterTimeService registerTimeService, UserRepository userRepository ){
        this.userRepository = userRepository;
        this.registerTimeService = registerTimeService;
    }

    @Transactional
    @Override
    public User save( User user ) {
        user.setPassword( new BCryptPasswordEncoder().encode( user.getPassword() ) );
        return userRepository.save( user );
    }

    @Override
    public void updateUser(User user) {
        userRepository.saveAndFlush( user );
    }


    @Override
    @Cacheable("Users")
    public Page<User> getUsers( Pageable pageable ){
        return userRepository.findAll( pageable );
    }

    @Override
    public User getUserByUsername( String  email ){
        return userRepository.findByEmail( email );
    }

    @Override
    public Page<RegisterTime> getMyAllRegister( Pageable pageable, Long idUser ){


        User user =  userRepository.getOne( idUser ) ;

        if( user != null ){

            return registerTimeService.getAllRegisterByUser( pageable, user.getId() );
        }
        throw new UserNotFoundException("User with id = "+ idUser + " do not exist");
    }

    @Transactional
    @Override
    public RegisterTime saveRegister( String emailUserLogged , RegisterTime registerTime ) {
        User user =  getUserByUsername( emailUserLogged );
        registerTime.setUser( user );
        RegisterTime newRegisterTime = registerTimeService.save( registerTime );
        user.getRegisterTimes().add( newRegisterTime );
        updateUser( user );

        return newRegisterTime;
    }


}
