package com.zhh.repository;

import com.zhh.repository.entity.Notice;
import com.zhh.repository.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author zhang.haihe
 */
public interface NoticeRepository extends JpaRepository<Notice, Long> {

    Notice getByIdIs(Integer id);

    Page<Notice> findByStatus(Integer status, Pageable pageable);

}
