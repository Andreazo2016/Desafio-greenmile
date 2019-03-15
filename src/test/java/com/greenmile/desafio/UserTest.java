package com.greenmile.desafio;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.greenmile.desafio.Util.Util;
import com.greenmile.desafio.controller.UserController;
import com.greenmile.desafio.domain.User;
import com.greenmile.desafio.jwt.service.TokenAuthenticationService;
import com.greenmile.desafio.repository.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Locale;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserRepository userRepository;

    private static String token;

    @Autowired
    private UserController userController;

    @Before
    public void shouldRetornTokenAcess(){

        token = Util.createToken( "usernameTest" );
        Assert.assertNotNull(token);
    }


    @Test
    public void shouldNotAllowAccessToUnauthenticatedUsers() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders.post("/api/users").header("Origin","*")
                        .content(asJsonString(new User("Andreazo Silva Souza","andreazo2012@gmail.com","123321a")))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect( MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    public void registeUser() throws Exception {
        userRepository.deleteAll();
        mvc.perform( MockMvcRequestBuilders
                .post("/api/users")
                .header("Origin","*")
                .header("Authorization",token)
                .content(asJsonString(new User("Andreazo Silva Souza","Anonimo3@gmail.com","123321a")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Andreazo Silva Souza"));
    }


    @Test
    public void getAllUsers() throws Exception
    {
        MockMvcBuilders.standaloneSetup(UserController.class)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers(new ViewResolver() {
                    @Override
                    public View resolveViewName(String viewName, Locale locale) throws Exception {
                        return new MappingJackson2JsonView();
                    }
                })
                .build().perform( MockMvcRequestBuilders
                .get("/api/users")
                .header("Origin","*")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*]").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].name").isNotEmpty());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
