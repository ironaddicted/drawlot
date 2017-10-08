package com.avoinovan.drawlot.service;

import com.avoinovan.drawlot.model.repository.UserRepository;
import org.springframework.stereotype.Service;

/**
 * @author by avoinovan
 */
@Service
public class UserService {


    private final UserRepository userRepository;

    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
