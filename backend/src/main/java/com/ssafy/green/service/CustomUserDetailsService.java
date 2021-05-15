package com.ssafy.green.service;

import com.ssafy.green.model.entity.User;
import com.ssafy.green.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User findUser = userRepository.findByUserIdAndFlag(userId, true).get();

        if(findUser == null) {
            throw new UsernameNotFoundException(userId);
        }
        return findUser;
    }
}
