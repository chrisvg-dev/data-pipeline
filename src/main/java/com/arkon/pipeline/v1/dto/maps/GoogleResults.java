package com.arkon.pipeline.v1.dto.maps;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Google retorna una lista de direcciones correspondientes a la latitud y longitud, los cuales se mapean
 * y posteriormente se analizan para extraer la información correspondiente.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoogleResults {
    /**
     * Almacena la lista de direcciones
     */
    private List<GoogleAddressComponent> address_components;
    /**
     * Almacena la dirección completa y en formato legible
     */
    private String formatted_address;
}
