package com.demo.NS3.entity;

import lombok.Cleanup;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
@RequiredArgsConstructor
@Table(name = "test_site")
public class SiteEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String sitename;
    @Column(name = "device_id")
    private String deviceid;
    @Column(name = "camera_name")
    private String cameraname;
}
