package mx.gob.bansefi.clients;

import mx.gob.bansefi.dto.clientServices.request.ReqCatalogoVariosPMDTO;
import mx.gob.bansefi.dto.clientServices.response.resCatalogosVariosPM.ResCatalogosVariosPMDTO;

import mx.gob.bansefi.utils.Util;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * Created by AppWIns on 26/04/2017.
 */
@Component
public class CatalogoClient {

	/*
	 * Se definen variables de clase.
	 */
	private String uriCatalogosVariosPM;
	Util util = Util.getInstance();
	private static final Logger log = LogManager.getLogger(CatalogoClient.class);

	/*
	 * Constructor para inicializar uris de los servicios de catalogos.
	 */
	public CatalogoClient(@Value("${url.context}") String domainServidorServicios, @Value("${path.wsBfsPersonasMorales}") String pathPersonasMorales,
			@Value("${rest.uri.wsBfsPersonasMorales.ConsultaCatalogo}") String uriCatalogosVariosPM) {
		this.uriCatalogosVariosPM = domainServidorServicios + pathPersonasMorales + uriCatalogosVariosPM;
	}

	/*
	 * Metodo para consumir catalogos varios de personas morales.
	 */
	public ResCatalogosVariosPMDTO catalogosVariosPM(ReqCatalogoVariosPMDTO req) {
		ResCatalogosVariosPMDTO res = null;
		try {
			String jsonRes = this.util.callRestPost(req, uriCatalogosVariosPM);

			res = new ResCatalogosVariosPMDTO();
			ArrayList<String> nodos = new ArrayList<String>();
			nodos.add("RESPONSE");

			res = (ResCatalogosVariosPMDTO) this.util.jsonToObject(res, jsonRes, nodos);

		} catch (ParseException ex) {
			log.error(ex.getMessage());
		}
		return res;
	}

}
