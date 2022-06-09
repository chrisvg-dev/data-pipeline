package com.arkon.pipeline.v1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Esta clase representa a las alcaldias que serán recibidas desde Google Maps durante el proceso de recolección
 * de datos
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Alcaldia {
    @Id
    private String id;
    private String name;
}
