package mx.gob.bansefi.clients;

import mx.gob.bansefi.dto.Request.DocumentosPM.ReqConsultaDocumentosTCB;
import mx.gob.bansefi.dto.Response.DocumentosPM.ResConsultaDocumentosTCB;
import mx.gob.bansefi.utils.Util;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * Created by app on 01/08/2017.
 */
@Component
public class DocumentosClient {

    @Value("${mensajes.servicioInaccesible}")
    private String msjServicioInaccesible;
    private String urlConsultaDocumentoTCB;
    private String urlConsultaDatosSurcursal;
    private static final Logger log = LogManager.getLogger(CatalogoClient.class);
    private Util util = Util.getInstance();

    public DocumentosClient(
            @Value("${domain.services}") String domainSerivices,
            @Value("${domain.servicesU}") String domainServicesU,
            @Value("${path.ConsultaDocumentosTCB}") String pathConsultaDocumentosTCB,
            @Value("${path.ConsultaDatosSucursal}") String pathConsultaDatosSucursal) {
        this.urlConsultaDocumentoTCB = domainServicesU + pathConsultaDocumentosTCB;
        this.urlConsultaDatosSurcursal = domainSerivices + pathConsultaDatosSucursal;
    }

    /*
    * Metodo para consumir servicio de Consulta Documentos de TCB.
    */
    public ResConsultaDocumentosTCB consultaDocumentosTCB(ReqConsultaDocumentosTCB req) {
        ResConsultaDocumentosTCB res = null;
        try {
            String jsonRes = this.util.callRestPost(req, urlConsultaDocumentoTCB);
            res = new ResConsultaDocumentosTCB();

            if (!jsonRes.equals("")) {
                ArrayList<String> nodos = new ArrayList<String>();
                res = (ResConsultaDocumentosTCB) this.util.jsonToObject(res, jsonRes, nodos);
                if (res == null) {
                    log.error("Ocurrio un inesperado con el servicio : " + urlConsultaDocumentoTCB);
                    res = new ResConsultaDocumentosTCB();
                    res.setEstatus("0");
                    res.setMensaje("Ocurri\u00f3 un error inesperado, intente de nuevo.");
                } else {
                    if (!res.getEstatus().equals("1")) {
                        log.error("\nError en el servicio consultaDocumentosTCB url: " + urlConsultaDocumentoTCB + "\nEntrada: " + util.objectToJson(req) + "Salida: " + util.objectToJson(res));
                    }
                }
            } else {
                log.error("\nServicio incaccesible: " + urlConsultaDocumentoTCB);
                res.setEstatus("-1");
                res.setMensaje(msjServicioInaccesible);
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return res;
    }
}
