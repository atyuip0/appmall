package com.zhh.repository;

import com.zhh.repository.entity.Category;
import com.zhh.repository.entity.Goods;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * @author zhang.haihe
 */
public interface GoodsRepository extends PagingAndSortingRepository<Goods, Long> {

    List<Goods> findByCategoryidAndStateAndGoodsnameLike(Long categoryid, Integer state, String goodsname, Sort sort);

    List<Goods> findByStateAndGoodsnameLike(Integer state, String goodsname, Sort sort);

}
