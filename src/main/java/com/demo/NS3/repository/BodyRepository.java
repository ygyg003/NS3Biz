package com.demo.NS3.repository;

import com.demo.NS3.entity.BodyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BodyRepository extends JpaRepository<BodyEntity,Long> {
    Optional<BodyEntity> findByDeviceidAndCameranameAndTrigger(String device_id, String camera_name, String trigger);
    Optional<BodyEntity> findBySnapid(String snap_id);
}
