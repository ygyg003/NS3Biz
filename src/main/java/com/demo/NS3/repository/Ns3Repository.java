package com.demo.NS3.repository;

import com.demo.NS3.entity.Ns3Entity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface Ns3Repository extends JpaRepository<Ns3Entity,Long> {
    Optional<Ns3Entity> findByDeviceidAndCameranameAndTrigger(String device_id, String camera_name, String trigger);
    List<Ns3Entity> findByFlag(String flag);
}
