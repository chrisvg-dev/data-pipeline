package com.arkon.pipeline.v1.dto.maps;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Almacena unicamente los códigos de la ciudad, no son datos útiles para la aplicación
 * pero son necesarios para la recepción
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GooglePlusCode {
    private String compound_code;
    private String global_code;
}
