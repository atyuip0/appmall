package com.zhh.repository;

import com.zhh.repository.entity.PayLog;
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
public interface PayLogRepository extends PagingAndSortingRepository<PayLog, Long> {

    List<PayLog> findByOrderIdAndPayStatus(Long orderId,Integer payStatus,Sort sort);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update t_pay_log o set o.pay_status =?1,o.up_by =?3,o.up_time =?4,o.notify_body =?5 where o.id = ?2",nativeQuery = true)
    int updatePayStatusById(Integer payStatus, Long id, String upBy, LocalDateTime upTime, String notifyBody);

}
