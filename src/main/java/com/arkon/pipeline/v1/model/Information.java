package com.arkon.pipeline.v1.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Information {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String alcaldia;
    private String ubicacion;
    private Double latitud;
    private Double longitud;
    private Integer idVehiculo;
    private Boolean statusVehiculo;
}
