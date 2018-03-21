package com.zhh.repository;

import com.zhh.repository.entity.Order;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author zhang.haihe
 */
public interface OrderRepository extends PagingAndSortingRepository<Order, Long> {

    List<Order> findByUserIdAndStatus(Long userId,Integer status, Sort sort);

    List<Order> findByUserId(Long userId, Sort sort);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update t_order o set o.status =?1,o.up_by =?3,o.up_time =?4 where o.id = ?2",nativeQuery = true)
    int updateStatusById(Integer status, Long id, String upBy, LocalDateTime upTime);

}
