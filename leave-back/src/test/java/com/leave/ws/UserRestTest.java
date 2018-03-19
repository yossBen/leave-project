package com.leave.ws;

import com.leave.config.AppConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.util.Random;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

//import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
//import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
@WebAppConfiguration
public class UserRestTest {
    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;

    @Before
    public void before() {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void getUsersTest() throws Exception {
        /*ResultActions r = mvc.perform(get("/api/user/getAll")).andExpect(status().isOk());
        r.andDo(MockMvcResultHandlers.print());*/
    }

    @Test
    public void createUserTest() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("firstname", "mohamed");
        params.add("lastname", "AMAZIGH");
        params.add("password", "mohamed");
        params.add("email", params.getFirst("firstname") + new Random().nextInt(100) + "@gmail.com");

        ResultActions r = mvc.perform(post("/api/user/create").contentType(MediaType.APPLICATION_JSON_UTF8).params(params));
        r.andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void validateAccountTest() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("token", "token");
        ResultActions r = mvc.perform(post("/api/user/account/validate").contentType(MediaType.APPLICATION_JSON_UTF8).params(params));
        r.andDo(MockMvcResultHandlers.print());
    }
}