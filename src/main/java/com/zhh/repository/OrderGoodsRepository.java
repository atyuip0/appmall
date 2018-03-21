package com.zhh.repository;

import com.zhh.repository.entity.OrderGoods;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author zhang.haihe
 */
public interface OrderGoodsRepository extends PagingAndSortingRepository<OrderGoods, Long> {


}
