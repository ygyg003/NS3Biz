package com.demo.NS3.entity;

import lombok.Data;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Table(name = "event_code")
public class EventCodeEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long Id;
    @Column(name = "event_type")
    private String eventtype;
    @Column(name = "event_value")
    private String eventvalue;
}
