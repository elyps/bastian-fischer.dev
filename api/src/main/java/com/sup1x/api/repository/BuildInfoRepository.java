package com.sup1x.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sup1x.api.model.BuildInfo;

public interface BuildInfoRepository extends JpaRepository<BuildInfo, Long> {

}
