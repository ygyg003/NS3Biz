package com.demo.NS3.entity;

import com.demo.NS3.vo.ApiBodyVo;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
@NoArgsConstructor
@Table(name = "ns3pushdata")
public class Ns3Entity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long Id;
    @Column(name = "camera_name")
    private String cameraname;
    @Column(name = "device_id")
    private String deviceid;
    private Long channel;
    private String events_type;
    private String trigger;
    private String snap_id;
    private String snap_path;
    private String flag;

    public Ns3Entity(ApiBodyVo vo) {
        this.cameraname = vo.getCamera_name();
        this.deviceid = vo.getDevice_id();
        this.channel = vo.getChannel();
        this.events_type = vo.getEvents_type();
        this.trigger = vo.getTrigger();
        this.snap_id = vo.getSnap_id();
        this.flag="N";
    }

}
