package com.example.web;

import com.example.multipleEntryPoints.MultipleEntryPointsApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = MultipleEntryPointsApplication.class)
public class MultipleEntryPointsIntegrationTest {
    //تست سطح دسترسی کاربر های تعریف شده است

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).addFilter(springSecurityFilterChain).build();
    }

    @Test
    public void whenTestAdminCredentials_thenOk() throws Exception {
        mockMvc.perform(get("/admin/myAdminPage")).andExpect(status().isUnauthorized());

        mockMvc.perform(get("/admin/myAdminPage").with(httpBasic("admin", "adminPass"))).andExpect(status().isOk());

        mockMvc.perform(get("/user/myUserPage").with(user("admin").password("adminPass").roles("ADMIN"))).andExpect(status().isForbidden());

    }

    @Test
    public void whenTestUserCredentials_thenOk() throws Exception {
        mockMvc.perform(get("/user/general/myUserPage")).andExpect(status().isFound());

        mockMvc.perform(get("/user/general/myUserPage").with(user("user").password("userPass").roles("USER"))).andExpect(status().isOk());

        mockMvc.perform(get("/admin/myAdminPage").with(user("user").password("userPass").roles("USER"))).andExpect(status().isForbidden());
    }

    @Test
    public void givenAnyUser_whenGetGuestPage_thenOk() throws Exception {
        mockMvc.perform(get("/guest/myGuestPage")).andExpect(status().isOk());

        mockMvc.perform(get("/guest/myGuestPage").with(user("user").password("userPass").roles("USER"))).andExpect(status().isOk());

        mockMvc.perform(get("/guest/myGuestPage").with(httpBasic("admin", "adminPass"))).andExpect(status().isOk());
    }
}