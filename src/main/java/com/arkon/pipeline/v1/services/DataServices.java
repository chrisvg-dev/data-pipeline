package com.arkon.pipeline.v1.services;

import com.arkon.pipeline.v1.client.RestClient;
import com.arkon.pipeline.v1.dto.maps.GoogleMaps;
import com.arkon.pipeline.v1.dto.cdmx.Template;
import com.arkon.pipeline.v1.model.Alcaldia;
import com.arkon.pipeline.v1.model.Informacion;
import com.arkon.pipeline.v1.repository.AlcaldiaRepository;
import com.arkon.pipeline.v1.repository.InformationRepository;
import lombok.Synchronized;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.xml.crypto.Data;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio que agrupa todas las funcionalidades que tienen relación con la obtención de la información que proviene
 * de origenes remotos. Este servicio solo se invoca una vez, cuando se necesita llenar la base de datos.
 */
@Service
@Transactional
public class DataServices {
    public static final Logger log = LoggerFactory.getLogger(Data.class);

    /**
     * URL de la API de Google, de aquí se obtienen los datos de las alcaldias
     */
    @Value("${api.url.apiGoogle}")
    private String urlGoogle;

    /**
     * API key del servicio de Google, necesario para realizar las peticiones
     */
    @Value("${api.url.apiKey}")
    private String apiKey;

    /**
     * Inyección de las dependencias necesarias para el funcionamiento de este servicio
     */
    private final InformationRepository recordRepository;
    private final AlcaldiaRepository alcaldiaRepository;
    private final RestTemplate templateService;
    private final RestClient restClient;
    /**
     * ---------------------------------------------------------
     */
    public DataServices(InformationRepository recordRepository, AlcaldiaRepository alcaldiaRepository, RestTemplate templateService, RestClient restClient) {
        this.recordRepository = recordRepository;
        this.alcaldiaRepository = alcaldiaRepository;
        this.templateService = templateService;
        this.restClient = restClient;
    }

    /**
     * Método que se encarga de almacenar la información en la base de datos, este método lleva un proceso en el
     * que mediante manejo de flujos se manda a llamar de forma asíncrona la información de Google Maps para
     * acceder a la alcaldia.
     * @param template
     */
    public void persist(Template template) {
        long time = System.currentTimeMillis();
        log.info("START: COMIENZA LA DESCARGA DE LOS DATOS...");
            List<Informacion> data = template.getResult().getRecords().stream()
                .filter( record ->
                        record.getPosition_latitude() > 0.0 || record.getPosition_longitude() > 0.0
                )
                .map(record -> {
                    Informacion info = new Informacion();
                    info.setIdVehiculo( record.getVehicle_id() );
                    info.setLatitud( record.getPosition_latitude() );
                    info.setLongitud( record.getPosition_longitude() );
                    info.setStatusVehiculo( record.getVehicle_current_status() == 1 );
                    Alcaldia alcaldia = this.buscarAlcaldiaPorCoordenadas(record.getPosition_latitude(), record.getPosition_longitude());
                    info.setAlcaldia(alcaldia);
                    return info;
                }).collect(Collectors.toList());
            this.recordRepository.saveAll(data);
        log.info("END: TERMINA LA DESCARGA DE LOS DATOS...");
        long endTime = System.currentTimeMillis();
        log.info("TIEMPO TRANSCURRIDO: " + ((endTime - time) / 1000));
    }

    /**
     * ADVERTENCIA...
     * Este método se utiliza únicamente cuando se quiere volver a obtener la información de los orígenes remotos.
     * NO SE RECOMIENDA USARLO CONSTANTEMENTE.
     * @return
     */
    public Template stream() {
        /**
         * WARNING
         * ELIMINA TODOS LOS REGISTROS DE LA BASE DE DATOS
         */
        this.recordRepository.deleteAll();
        this.alcaldiaRepository.deleteAll();
        /**
         * WARNING.
         * CONSULTA NUEVAMENTE LA INFORMACIÓN DE LA API DE LA CIUDAD DE MÉXICO Y LA DESCARGA.
         */
        return this.restClient.get();
    }

    /**
     * Se conecta a la API de Google y descarga el JSON con la información de la ubicación con base en la
     * latitud y longitud proporcionada.
     * Retorna un DTO de GoogleMaps, que se puede encontrar en la carpeta DTO.
     * @param lat
     * @param lng
     * @return
     */
    public GoogleMaps obtenerAlcaldia(Double lat, Double lng){
        String url = this.urlGoogle+"latlng="+lat+","+lng+"&key=" + this.apiKey;
        GoogleMaps gm = templateService.getForObject(url, GoogleMaps.class);
        return gm;
    }

    /**
     * Recibe las coordenadas (latitud y longitud), con ellas invoca al método de obtenerAlcaldia que viene en
     * formato DTO, lo descompone y extrae mediante un simple algoritmo la información requerida de la alcaldia.
     *
     * La alcaldia encontrada la consulta en la base de datos, si no existe la guarda, caso contrario, la consulta y
     * al final retorna la información necesaria.
     * @param lat
     * @param lng
     * @return
     */
    @Synchronized
    public Alcaldia buscarAlcaldiaPorCoordenadas(Double lat, Double lng) {
        GoogleMaps gm = this.obtenerAlcaldia(lat, lng);
        try {
            int len = gm.getResults().size();

            /** IMPORTANTE
             * Google retorna demasiada información con basándose en las coordenadas proporcionadas, la información de
             * las alcaldias puede ser fácilmente encontrada ubicando la siguiente secuencia:
             * JSON completo
             *      - results
             *          - address_components (aquí está toda la información necesaria)
             *
             * La alcaldia se obtiene accediendo a la posición n - 4, donde n es la cantidad total de registros
             * dentro de address_components
             *
             */
            String name = gm.getResults().get(len-4).getAddress_components().get(0).getLong_name();

            boolean exists = alcaldiaRepository.existsByName(name);
            if (exists) {
                return alcaldiaRepository.findByName(name).get();
            }
            return this.alcaldiaRepository.save(new Alcaldia(0, name.toUpperCase()));
        } catch (Exception e){
            log.error("ERROR: "+  e.getLocalizedMessage() );
            return null;
        }
    }
}
