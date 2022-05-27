package com.arkon.pipeline.v1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Record {
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
