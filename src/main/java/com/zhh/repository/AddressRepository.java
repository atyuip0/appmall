package com.zhh.repository;

import com.zhh.repository.entity.Address;
import com.zhh.repository.entity.Notice;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * @author zhang.haihe
 */
public interface AddressRepository extends PagingAndSortingRepository<Address, Long> {

    Address getByUserIdAndIsDefaultIs(Long userId,Boolean isDefault);

    List<Address> findByUserId(Long userId, Sort sort);
}
