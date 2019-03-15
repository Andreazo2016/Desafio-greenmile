package com.greenmile.desafio.controller;

import com.greenmile.desafio.domain.RegisterTime;
import com.greenmile.desafio.domain.User;
import com.greenmile.desafio.dto.RegisteTimeResponseDTO;
import com.greenmile.desafio.dto.RegisterTimeDTO;
import com.greenmile.desafio.dto.UserDTO;
import com.greenmile.desafio.dto.UserResponseDTO;
import com.greenmile.desafio.service.UserService;
import com.wordnik.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
@Api(value = "UserController", description = "User operations")
public class UserController {

    private UserService userService;

    private static final int DEFAULT_QTD_ITENS_PER_PAGE = 10;

    public UserController(){}
    @Autowired
    public UserController( UserService userService ){
        this.userService = userService;
    }

    @ApiOperation(
            value="Cadastrar uma nova pessoa",
            response=UserResponseDTO.class,
            notes="Essa operação salva um novo registro com as informações de usuário.")
    @ApiResponses(value= {
            @ApiResponse(
                    code=200,
                    message="Retorna um UserResponseDTO com uma mensagem de sucesso",
                    response=UserResponseDTO.class
            ),
            @ApiResponse(
                    code=403,
                    message="Caso tente salvar um registro sem está autenticado"
            ),
            @ApiResponse(
                    code=401,
                    message="Não possui autenticação para realizar essa operação"
            )

    })
    @Authorization(scopes = {},value = "Bearer", type = "JWT")
    @PostMapping("/users")
    public ResponseEntity<UserResponseDTO> save( @RequestBody UserDTO userDTO ){
        User user = userService.save( userDTO.toUser() );
        return new ResponseEntity<>( UserResponseDTO.toUserResponseDTO( user ), HttpStatus.CREATED );

    }

    @ApiOperation(value = "Return a List of users", notes = "Pagination")
    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers( @PageableDefault(size = 5, value = 0) Pageable pageable ){
        Page<User> users = userService.getUsers(PageRequest.of( pageable.getPageNumber(), DEFAULT_QTD_ITENS_PER_PAGE ) );
        List< UserResponseDTO> userResponseDTOS = users.getContent()
                .stream()
                .map( user -> UserResponseDTO.toUserResponseDTO(( user ) ) )
                .collect( Collectors.toList() );
        return new ResponseEntity<>( userResponseDTOS, HttpStatus.OK );

    }

    @ApiOperation(value = "Create  users' s registerTime")
    @Authorization(scopes = {},value = "Bearer", type = "JWT")
    @PostMapping("/users/registerTimes")
    public ResponseEntity<RegisteTimeResponseDTO> saveRegister(Principal principal, @RequestBody RegisterTimeDTO registerTimeDTO ){

        RegisterTime registerTime = userService.saveRegister( principal.getName(), registerTimeDTO.toRegisteTime() );
        return new ResponseEntity<>(RegisteTimeResponseDTO.toRegisteTimeResponse( registerTime ), HttpStatus.CREATED );
    }

    @ApiOperation(value = "Return registerTime of user by idUser")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "Number of records per page."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "Sorting criteria in the format: property(,asc|desc). " +
                            "Default sort order is ascending. " +
                            "Multiple sort criteria are supported.")
    })
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
