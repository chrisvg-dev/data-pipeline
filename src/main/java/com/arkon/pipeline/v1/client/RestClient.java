package com.arkon.pipeline.v1.client;

import com.arkon.pipeline.v1.dto.cdmx.Template;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
/**
 * Se usa la dependencia Feign para facilitar el consumo de una api rest y recepción de los datos
 * en un formato definido por clases
 */
@FeignClient(
        url = "https://datos.cdmx.gob.mx",
        name = "CDMX-CLIENT"
)
public interface RestClient {
    @GetMapping("/api/3/action/datastore_search?resource_id=ad360a0e-b42f-482c-af12-1fd72140032e&limit=50")
    Template get();
}
