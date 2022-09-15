package com.demo.NS3.repository;

import com.demo.NS3.entity.SiteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SiteRepository extends JpaRepository<SiteEntity,Long> {
    Optional<SiteEntity> findByDeviceid(String deviceid);
}
