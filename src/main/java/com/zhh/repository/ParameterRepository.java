package com.zhh.repository;

import com.zhh.repository.entity.Address;
import com.zhh.repository.entity.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author zhang.haihe
 */
public interface ParameterRepository extends JpaRepository<Parameter, Long> {

    /**
     * 根据名称查找
     * @param paramName
     * @return
     */
    Parameter getByParamNameIs(String paramName);

}
