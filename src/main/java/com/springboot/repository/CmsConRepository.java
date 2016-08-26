package com.springboot.repository;

import com.springboot.domain.CmsCon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by ibong-gi on 2016. 8. 25..
 */
public interface CmsConRepository extends JpaRepository<CmsCon, String> {
    @Query("select c from CmsCon c where c.AT_CONTENTS in :ats")
    public List<CmsCon> findMappingId(@Param("ats") List<String> ats);


    @Query("select c from CmsCon c join c.intgMap im where c.AT_CONTENTS in :ats")
    public List<CmsCon> findMappingIntg(@Param("ats") List<String> ats);
}

