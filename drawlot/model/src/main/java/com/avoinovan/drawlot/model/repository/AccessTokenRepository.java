package com.avoinovan.drawlot.model.repository;

import com.avoinovan.drawlot.model.entity.AccessToken;
import com.avoinovan.drawlot.model.entity.Platform;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author by avoinovan
 */
public interface AccessTokenRepository extends MongoRepository<AccessToken, String> {
    AccessToken findByUserIdAndPlatform(String userId, Platform platform);
}
