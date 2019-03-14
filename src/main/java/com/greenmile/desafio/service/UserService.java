package com.greenmile.desafio.service;

import com.greenmile.desafio.domain.RegisterTime;
import com.greenmile.desafio.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
     User save( User user );
     void updateUser( User user );
     User getUserByUsername( String  email );
     Page<User> getUsers(Pageable pageable);
     Page<RegisterTime> getMyAllRegister( Pageable pageable,Long idUser );
     RegisterTime saveRegister( String emailUserLogged, RegisterTime registerTime );
}
