package com.arkon.pipeline.v1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer recordId;
    private Integer _id;
    private Integer id;
    private LocalDateTime date_updated;
    private Integer vehicle_id;
    private Integer vehicle_label;
    private Integer vehicle_current_status;
    private Double position_latitude;
    private Double position_longitude;
    private String geographic_point;
    private Integer position_speed;
    private Integer position_odometer;
    private Integer trip_schedule_relationship;
    private Integer trip_id;
    private Integer trip_start_date;
    private Integer trip_route_id;
    private String alcaldia;
}
