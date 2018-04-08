package com.zhh.repository;

import com.zhh.repository.entity.Banner;
import com.zhh.repository.entity.Category;
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
public interface CategoryRepository extends PagingAndSortingRepository<Category, Long> {

    List<Category> findByParentIdAndStatus(Long parentId, Integer status, Sort sort);

    List<Category> findByParentId(Long parentId, Sort sort);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update t_category o set o.status =?1,o.up_by =?3,o.up_time =?4 where o.id = ?2",nativeQuery = true)
    int updateStatusById(Integer status, Long id, String upBy, LocalDateTime upTime);

}
