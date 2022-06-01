package com.arkon.pipeline.v1.dto.cdmx;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * La clase Record representa toda la información importante de este análisis, en ella viene la información
 * de los microbuses, la ubicación geográfica, el id de la unidad, entre otras.
 *
 * Esta clase se usa como DTO para posteriormente limpiar los datos y almacenarlos en la base de datos.
 */
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
