package com.avoinovan.drawlot.model.repository;

import com.avoinovan.drawlot.model.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    User findByEmail(String email);
}
