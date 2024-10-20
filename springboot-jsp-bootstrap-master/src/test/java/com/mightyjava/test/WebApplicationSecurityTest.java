package com.mightyjava.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mightyjava.controller.UserController;
import com.mightyjava.model.Role;
import com.mightyjava.model.Users;
import com.mightyjava.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class WebApplicationSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    public WebApplicationSecurityTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @WithMockUser(username = "tal", roles = {"USER"})
    public void givenValidUser_whenPostUserAdd_thenReturnSuccess() throws Exception {
        Users user = new Users();
        user.setUserName("newUser");
        user.setPassword("password123");
        user.setEmail("newuser@gmail.com");

        when(userService.addUser(any())).thenReturn("User added successfully");

        mockMvc.perform(post("/user/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(user)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("User added successfully"));

        verify(userService, times(1)).addUser(any());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void givenUserId_whenDeleteUser_thenReturnSuccess() throws Exception {
        Long userId = 1L;
        when(userService.deleteUser(userId)).thenReturn("User deleted successfully");

        mockMvc.perform(get("/user/delete/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("User deleted successfully"));

        verify(userService, times(1)).deleteUser(userId);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void givenUserId_whenGetUserEdit_thenReturnUserForm() throws Exception {
        Long userId = 1L;
        Users user = new Users();
        user.setUserName("existingUser");

        // Mock do retorno do usuário
        when(userService.findOne(userId)).thenReturn(user);

        // Mock do retorno da lista de papéis (Roles)
        Role userRole = new Role();
        Role adminRole = new Role();
        when(userService.roleList()).thenReturn(Arrays.asList(userRole, adminRole));

        mockMvc.perform(get("/user/edit/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("userForm"))
                .andExpect(view().name("user/form"));

        verify(userService, times(1)).findOne(userId);
    }

    @Test
    @WithMockUser(username = "tal", roles = {"USER"})
    public void whenGetUserList_thenReturnListView() throws Exception {
        when(userService.userList()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/user/list"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("users"))
                .andExpect(view().name("/user/list"));

        verify(userService, times(1)).userList();
    }

    @Test
    @WithMockUser(username = "tal", roles = {"USER"})
    public void givenInvalidUser_whenPostUserAdd_thenReturnValidationError() throws Exception {
        Users invalidUser = new Users();  // Usuário sem campos obrigatórios preenchidos

        mockMvc.perform(post("/user/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(invalidUser)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").exists());

        verify(userService, times(0)).addUser(any());
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
