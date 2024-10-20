package com.mightyjava.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mightyjava.model.Users;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = { SpringSecurityWebAuxTestConfig.class }
)
@AutoConfigureMockMvc
public class WebApplicationSecurityTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    @WithUserDetails("teco") 
    public void givenManagerUser_whenPostUserAdd_thenOk() throws Exception {
        Users newUser = new Users();
        newUser.setUserName("newUser");
        newUser.setPassword("password123");
        newUser.setEmail("newuser@example.com");

            
        String userJson = new ObjectMapper().writeValueAsString(newUser);

        mockMvc.perform(MockMvcRequestBuilders.post("/user/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("User added successfully")));
    }


    @Test
    @WithUserDetails("teco") 
    public void givenManagerUser_whenGetUserEdit_thenOk() throws Exception {
        Long userId = 1L; 

        mockMvc.perform(MockMvcRequestBuilders.get("/user/edit/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("user/form")));
    }

    @Test
    @WithUserDetails("tal") 
    public void givenBasicUser_whenGetUserList_thenOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/list"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("List of Users")));
    }


    @Test
    @WithUserDetails("teco") 
    public void givenManagerUser_whenDeleteUser_thenOk() throws Exception {
        Long userId = 1L; 

        mockMvc.perform(MockMvcRequestBuilders.get("/user/delete/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("User deleted successfully")));
    }

 
    @Test
    @WithUserDetails("tal") 
    public void givenBasicUser_whenPostUserAdd_thenForbidden() throws Exception {
        Users newUser = new Users();
        newUser.setUserName("testuser");
        newUser.setPassword("password123");
        newUser.setEmail("testuser@example.com");

        String userJson = new ObjectMapper().writeValueAsString(newUser);

        mockMvc.perform(MockMvcRequestBuilders.post("/user/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }
}
