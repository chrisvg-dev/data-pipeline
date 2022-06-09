package com.arkon.pipeline.v1.model;

import lombok.*;

import javax.persistence.*;

/**
 * La clase Información es la forma final de representación de los datos, los cuales necesitan ser limpiados
 * para obtener la información necesaria para este proyecto. Además es el modelo de la base de datos.
 *
 * La anotación @Entity permite mapear la información para almacenarla en una base de datos mediante JPA
 * La anotación @Data permite generar los métodos Get, Set, toString, Hash, etc. de forma automática
 * La anotación @NoArgsConstructor genera un constructor sin parámetros
 * La anotación @AllArgsConstructor genera un constructor con la inicialización de las variables
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Informacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Se relaciona el campo alcaldia de la tabla información con la llave primaria de la tabla alcaldia
     */
    @ManyToOne
    @JoinColumn(name = "alcaldiaCode", referencedColumnName = "id")
    private Alcaldia alcaldia;

    private Double latitud;
    private Double longitud;
    private Integer idVehiculo;
    private Boolean statusVehiculo;
}
