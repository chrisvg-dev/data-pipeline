package com.arkon.pipeline.v1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Esta clase representa a las alcaldias que serán recibidas desde Google Maps durante el proceso de recolección
 * de datos
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Alcaldia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
}
