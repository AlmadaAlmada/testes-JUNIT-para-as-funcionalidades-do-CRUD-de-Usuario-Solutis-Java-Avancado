package com.mightyjava.test;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Arrays;

@TestConfiguration
public class SpringSecurityWebAuxTestConfig {

    @Bean
    @Primary
    public UserDetailsService userDetailsService() {
        User basicUser = new User("tal", "password", Arrays.asList(
                new SimpleGrantedAuthority("ROLE_USER"),
                new SimpleGrantedAuthority("PERM_FOO_READ")));

        User basicActiveUser = new User("tico","password", Arrays.asList(
                new SimpleGrantedAuthority("ROLE_USER"),
                new SimpleGrantedAuthority("PERM_FOO_READ")
        ));

        User managerUser = new User("teco", "password", Arrays.asList(
                new SimpleGrantedAuthority("ROLE_MANAGER"),
                new SimpleGrantedAuthority("PERM_FOO_READ"),
                new SimpleGrantedAuthority("PERM_FOO_WRITE"),
                new SimpleGrantedAuthority("PERM_FOO_MANAGE")
        ));

        return new InMemoryUserDetailsManager(Arrays.asList(
                basicUser, managerUser
        ));
    }
}