package com.arkon.pipeline.v1.graphql;

import com.arkon.pipeline.v1.model.Alcaldia;
import com.arkon.pipeline.v1.model.Informacion;
import com.arkon.pipeline.v1.services.AlcaldiaService;
import com.arkon.pipeline.v1.services.InformacionService;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * En esta clase se definen todas las funcionalidades de acceso a datos para consumir los datos almacenados dentro
 * de la base de datos, solamente se accede a consultas donde se devuelven datos; para registros, actualizaciones y
 * eliminaciones se debe usar otra clase.
 */
@Component
public class QueryResolver implements GraphQLQueryResolver {
    public static final Logger log = LoggerFactory.getLogger(QueryResolver.class);
    /**
     * Inyección de repositorios para poder hacer uso de ellos dentro de QueryResolver
     */
    private final InformacionService informacionService;
    private final AlcaldiaService alcaldiaService;

    public QueryResolver(InformacionService informacionService, AlcaldiaService alcaldiaService) {
        this.informacionService = informacionService;
        this.alcaldiaService = alcaldiaService;
    }
    /**
     * Permite listar la información utilizando la interface JPA
     * @return
     */
    public List<Informacion> records(){
        return this.informacionService.buscarTodos();
    }

    /**
     * Permite listar las alcaldias disponibles en la base de datos
     * @return
     */
    public List<Alcaldia> alcaldiasDisponibles(){
        return this.alcaldiaService.buscarTodas();
    }
    /**
     * Permite listar las unidades registradas en la base de datos siempre y cuando tengan el status en disponible
     * @return
     */
    public List<Informacion> unidadesDisponibles() {
        return this.informacionService.unidadesDisponibles();
    }

    /**
     * Permite obtener un registro de la base de datos que coincida con el ID proporcionado por el usuario mediante
     * la api de Graphql
     * @param idVehiculo
     * @return
     */
    public Informacion buscarPorId(Integer idVehiculo) {
        return this.informacionService.buscarPorId(idVehiculo);
    }
    /**
     * Permite obtener un todos los registros de la base de datos que coincidan con
     * el nombre de la alcaldia proporcionado por el usuario mediante la api de graphql
     * @param alcaldia
     * @return
     */
    public List<Informacion> buscarPorAlcaldia(String alcaldia){
        return this.informacionService.buscarPorAlcaldia(alcaldia.toUpperCase());
    }
}
