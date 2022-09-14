package com.demo.NS3.repository;

import com.demo.NS3.entity.EventCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EventCodeRepository extends JpaRepository<EventCodeEntity,Long> {
    Optional<EventCodeEntity> findByEventtype(String events_type);
}
