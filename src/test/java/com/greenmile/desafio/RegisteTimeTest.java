package com.greenmile.desafio;

import com.greenmile.desafio.Util.Util;
import com.greenmile.desafio.domain.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RegisteTimeTest {

    @Autowired
    private MockMvc mvc;


    private static String token;

    @Before
    public void shouldRetornTokenAcess(){
        token = Util.createToken( "usernameTest" );
        Assert.assertNotNull(token);
    }




    @Test
    public void getRegisterTimeOfUserDontExist() throws Exception {

        mvc.perform( MockMvcRequestBuilders
                .get("/api/users/1000/registerTimes?page=0")
                .header("Origin","*")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*]").isEmpty());
    }



}
