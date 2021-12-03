package com.leverx.dealerstat.security;

import com.leverx.dealerstat.model.User;
import com.leverx.dealerstat.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsersRepository repository;

    @Autowired
    public UserDetailsServiceImpl(UsersRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repository.findByEmail(email).orElseThrow(() -> {
            throw new UsernameNotFoundException("The username is not found");
        });
        return SecurityUser.fromUser(user);
    }
}
