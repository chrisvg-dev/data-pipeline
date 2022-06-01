package com.arkon.pipeline.v1.dto.maps;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Clase modelo que representa la información que será recibida desde la API de Google a través del
 * envío de la ubicación en formato de latitud y longitud
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class GoogleMaps {
    private GooglePlusCode plus_code;
    private List<GoogleResults> results;
    private String status;
}
