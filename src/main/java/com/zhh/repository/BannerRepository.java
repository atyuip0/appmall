package com.zhh.repository;

import com.zhh.repository.entity.Banner;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * @author zhang.haihe
 */
public interface BannerRepository extends PagingAndSortingRepository<Banner, Long> {

    List<Banner> findByTypeAndStatus(Integer type, Integer status, Sort sort);

}
