package com.zhh.repository;

import com.zhh.repository.entity.Banner;
import com.zhh.repository.entity.Category;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * @author zhang.haihe
 */
public interface CategoryRepository extends PagingAndSortingRepository<Category, Long> {

    List<Category> findByParentIdAndStatus(Long parentId, Integer status, Sort sort);

}
