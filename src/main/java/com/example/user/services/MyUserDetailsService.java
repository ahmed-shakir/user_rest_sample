package com.example.user.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * <description>
 *
 * @author Ahmed Shakir
 * @version 1.0
 * @since 2020-10-23
 */
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userService.findByUsername(username);
        if(user == null) {
            throw new UsernameNotFoundException("Username " + username + " not found");
        }
        return new User();
    }
}
