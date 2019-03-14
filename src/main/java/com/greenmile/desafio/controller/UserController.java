package com.greenmile.desafio.controller;

import com.greenmile.desafio.domain.RegisterTime;
import com.greenmile.desafio.domain.User;
import com.greenmile.desafio.dto.RegisteTimeResponseDTO;
import com.greenmile.desafio.dto.RegisterTimeDTO;
import com.greenmile.desafio.dto.UserDTO;
import com.greenmile.desafio.dto.UserResponseDTO;
import com.greenmile.desafio.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api")
@EnableSpringDataWebSupport
public class UserController {

    private UserService userService;

    private static final int DEFAULT_QTD_ITENS_PER_PAGE = 10;
    @Autowired
    public UserController( UserService userService ){
        this.userService = userService;
    }

    @PostMapping("/users")
    public ResponseEntity<UserResponseDTO> save( @RequestBody UserDTO userDTO ){
        User user = userService.save( userDTO.toUser() );
        return new ResponseEntity<>( UserResponseDTO.toUserResponseDTO( user ), HttpStatus.CREATED );

    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers( Pageable pageable ){
        Page<User> users = userService.getUsers(PageRequest.of( pageable.getPageNumber(), DEFAULT_QTD_ITENS_PER_PAGE ) );
        List< UserResponseDTO> userResponseDTOS = users.getContent()
                .stream()
                .map( user -> UserResponseDTO.toUserResponseDTO(( user ) ) )
                .collect( Collectors.toList() );
        return new ResponseEntity<>( userResponseDTOS, HttpStatus.OK );

    }
    @PostMapping("/users/registerTimes")
    public ResponseEntity<RegisteTimeResponseDTO> saveRegister(Principal principal, @RequestBody RegisterTimeDTO registerTimeDTO ){

        RegisterTime registerTime = userService.saveRegister( principal.getName(), registerTimeDTO.toRegisteTime() );
        return new ResponseEntity<>(RegisteTimeResponseDTO.toRegisteTimeResponse( registerTime ), HttpStatus.CREATED );
    }

    @GetMapping("/users/{idUser}/registerTimes")
    public ResponseEntity<List<RegisteTimeResponseDTO>> getAllRegister( @PathVariable("idUser") Long idUser, Pageable pageable ){
        Page<RegisterTime> registerTimes = userService.getMyAllRegister( PageRequest.of( pageable.getPageNumber(), DEFAULT_QTD_ITENS_PER_PAGE ), idUser );
        List< RegisteTimeResponseDTO > registerResponseDTOS = registerTimes.getContent()
                .stream()
                .map( registerTime -> RegisteTimeResponseDTO.toRegisteTimeResponse( registerTime ))
                .collect( Collectors.toList() );
        return new ResponseEntity<>( registerResponseDTOS , HttpStatus.OK );

    }
}
