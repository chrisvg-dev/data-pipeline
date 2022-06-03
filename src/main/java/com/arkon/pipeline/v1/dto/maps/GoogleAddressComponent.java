package com.arkon.pipeline.v1.dto.maps;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase modelo que representa la información que será recibida desde la API de Google a través del
 * envío de la ubicación en formato de latitud y longitud.
 * Esta clase almacena la información de las direcciones. Permite identificar la alcaldia.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoogleAddressComponent {
    private String long_name;
    private String short_name;
}