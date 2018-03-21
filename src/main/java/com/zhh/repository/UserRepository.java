package com.zhh.repository;

import com.zhh.repository.entity.Parameter;
import com.zhh.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author zhang.haihe
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 根据openId查找
     * @param openId
     * @return
     */
    User getByOpenIdIs(String openId);

}
