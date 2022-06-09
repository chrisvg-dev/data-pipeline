package com.arkon.pipeline.v1.services;

import com.arkon.pipeline.v1.client.RestClient;
import com.arkon.pipeline.v1.dto.maps.GoogleMaps;
import com.arkon.pipeline.v1.dto.cdmx.Template;
import com.arkon.pipeline.v1.model.Alcaldia;
import com.arkon.pipeline.v1.model.Informacion;
import com.arkon.pipeline.v1.repository.AlcaldiaRepository;
import com.arkon.pipeline.v1.repository.InformationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;
/**
 * Servicio que agrupa todas las funcionalidades que tienen relación con la obtención de la información que proviene
 * de origenes remotos. Este servicio solo se invoca una vez, cuando se necesita llenar la base de datos.
 */
@Service
@Transactional
public class RecursosService {
    public static final Logger log = LoggerFactory.getLogger(RecursosService.class);
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
    public RecursosService(InformationRepository recordRepository, AlcaldiaRepository alcaldiaRepository, RestTemplate templateService, RestClient restClient) {
        this.recordRepository = recordRepository;
        this.alcaldiaRepository = alcaldiaRepository;
        this.templateService = templateService;
        this.restClient = restClient;
    }

    public Integer countAll() {
        return this.recordRepository.countAll();
    }

    /** ----------------------------------------------------------------------------------------------------------
     * Método que se encarga de almacenar la información en la base de datos, este método lleva un proceso en el
     * que mediante manejo de flujos se manda a llamar de forma asíncrona la información de Google Maps para
     * acceder a la alcaldia.
     *
     */

    public void persist() {
        this.reset();
        try {
            Set<Alcaldia> alcaldias = new HashSet<>();
            /**
             * En este punto de la aplicación se realiza una iteración en los datos recibidos de la API de la ciudad de México para limpiarlos
             */
            log.info("START: COMIENZA LA DESCARGA DE LOS DATOS...");
            List<Informacion> data = this.obtenerDatosIniciales().getResult().getRecords().parallelStream()
                    .filter( r ->
                            r.getPosition_latitude() > 0.0 || r.getPosition_longitude() > 0.0
                    )
                    .map(item -> {
                        Informacion info = new Informacion();
                        info.setIdVehiculo( item.getVehicle_id() );
                        info.setLatitud( item.getPosition_latitude() );
                        info.setLongitud( item.getPosition_longitude() );
                        info.setStatusVehiculo( item.getVehicle_current_status() == 1 );
                        /**
                         * Cuando se llega a este punto, se manda una petición a Google para obtener la alcaldia correspondiente a las coordenadas
                         */
                        GoogleMaps gm = this.buscarUbicacionMaps(item.getPosition_latitude(), item.getPosition_longitude());
                        Alcaldia alcaldia = this.extraerAlcaldiaMaps(gm);
                        info.setAlcaldia(alcaldia);
                        alcaldias.add(alcaldia);
                        return info; // Retorna el objeto con la alcaldia
                    }).collect(Collectors.toList());

            this.alcaldiaRepository.saveAll(alcaldias);
            this.recordRepository.saveAll(data);

            log.info("END: TERMINA LA DESCARGA DE LOS DATOS...");
        } catch (Exception e){
            log.error( e.getMessage() );
        }
    }
    /**
     * ------------------------------------------------------------------------------------------------------------------
     */

    /**
     * Este método permite conectarse a la API de Google y descargar el JSON con la información de la ubicación con
     * base en la latitud y longitud proporcionada.
     * Retorna un DTO de GoogleMaps, que se puede encontrar en la carpeta DTO.
     * @param lat
     * @param lng
     * @return
     */
    @Async("threadPoolTaskExecutor")
    public GoogleMaps buscarUbicacionMaps(Double lat, Double lng){
        String url = this.urlGoogle+"latlng="+lat+","+lng+"&key=" + this.apiKey;
        return templateService.getForObject(url, GoogleMaps.class);
    }
    /**
     * Recibe las coordenadas (latitud y longitud), con ellas invoca al método de obtenerAlcaldia que viene en
     * formato DTO, lo descompone y extrae mediante un simple algoritmo la información requerida de la alcaldia.
     *
     * La alcaldia encontrada la consulta en la base de datos, si no existe la guarda, caso contrario, la consulta y
     * al final retorna la información necesaria.
     * @param gm
     * @return
     */
    public Alcaldia extraerAlcaldiaMaps(GoogleMaps gm) {
        int len = gm.getResults().size();
        try {
            String name = gm.getResults().get(len-4).getAddress_components().get(0).getLong_name().toUpperCase();
            String code = new StringBuilder().append( name.length() ).append( name.substring(0,4).toUpperCase() ).toString();
            return new Alcaldia(code, name);
        } catch (Exception e){
            log.error(String.format("ERROR: %s", e.getMessage()));
            return null;
        }
    }

    /**
     * ADVERTENCIA...
     * Este método se utiliza únicamente cuando se quiere volver a obtener la información de los orígenes remotos.
     * NO SE RECOMIENDA USARLO CONSTANTEMENTE.
     * @return
     */
    public Template obtenerDatosIniciales() {
        /**
         * WARNING.
         * CONSULTA NUEVAMENTE LA INFORMACIÓN DE LA API DE LA CIUDAD DE MÉXICO Y LA DESCARGA.
         */
        return this.restClient.get();
    }

    public void reset() {
        /**
         * WARNING
         * ELIMINA TODOS LOS REGISTROS DE LA BASE DE DATOS
         */
        this.recordRepository.deleteAll();
        this.alcaldiaRepository.deleteAll();
    }
}
