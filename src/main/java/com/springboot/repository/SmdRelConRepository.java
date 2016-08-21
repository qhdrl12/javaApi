package com.springboot.repository;

import com.springboot.domain.SmdRelCon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by ibong-gi on 2016. 8. 17..
 */
//JpaRepository
public interface SmdRelConRepository extends PagingAndSortingRepository<SmdRelCon, Long> {
    Page<SmdRelCon> findAll(Pageable pageable);
//    Page<SmdRelCon> findAllByOrderByDesc(Pageable pageable);
}
