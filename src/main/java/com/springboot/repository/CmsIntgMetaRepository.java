package com.springboot.repository;

import com.springboot.domain.CmsIntgMap;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by ibong-gi on 2016. 8. 25..
 */
public interface CmsIntgMetaRepository extends JpaRepository<CmsIntgMap, String> {

}
