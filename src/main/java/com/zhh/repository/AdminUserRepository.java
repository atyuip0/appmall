package com.zhh.repository;

import com.zhh.repository.entity.Address;
import com.zhh.repository.entity.AdminUser;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

public interface AdminUserRepository extends PagingAndSortingRepository<AdminUser, Long> {

    AdminUser findByUserNameAndPwdIs(String userName,String pwd);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update t_admin_user a set a.last_login_ip =?1,a.last_login_time =?2 where a.id = ?3",nativeQuery = true)
    int updateLastLoginById(String lastLoginIp, LocalDateTime lastLoginTime, Long id);

}
