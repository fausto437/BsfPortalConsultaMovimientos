package mx.gob.bansefi.clients;

import mx.gob.bansefi.dto.Request.Documentos.ReqAltaRelacionDocumento;
import mx.gob.bansefi.dto.Request.Documentos.ReqConsultaDocumento;
import mx.gob.bansefi.dto.Request.Documentos.ReqConsultaDocumentosTCB;
import mx.gob.bansefi.dto.Request.Documentos.ReqConsultaTipoDocumento;
import mx.gob.bansefi.dto.Response.Documentos.ResAltaDocumentoTCB;
import mx.gob.bansefi.dto.Response.Documentos.ResAltaRelacionDocumento;
import mx.gob.bansefi.dto.Response.Documentos.ResConsultaDocumento;
import mx.gob.bansefi.dto.Response.Documentos.ResConsultaTipoDocumento;
import mx.gob.bansefi.dto.Response.DocumentosPM.ResConsultaDocumentosTCB;
import mx.gob.bansefi.utils.Util;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

/**
 * Created by app on 01/08/2017.
 */
@Component
public class DocumentosClient {

    @Value("${mensajes.servicioInaccesible}")
    private String msjServicioInaccesible;
    @Value("${domain.services}")
    private String domainSerivices;
    @Value("${path.AltaRelacionDocumento}")
    private String altaRelacionDocumento;
    @Value("${path.ConsultaTipoId}")
    private String consultaTipoId;
    
    private static final Logger log = LogManager.getLogger(CatalogoClient.class);
    private Util util = Util.getInstance();

    /*
    * Metodo para consumir servicio de Consulta Documentos de TCB.
    
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
    }*/
    
    /*
     * Metodo para consumir servicio de Consulta Documentos enviando el idInternoPe y el tipo de documento.
     */
    public ResConsultaDocumento consultaBase64(ReqConsultaDocumento datos) {
    	ResConsultaDocumento res = new ResConsultaDocumento();
    	try {
            String jsonRes = this.util.callRestPost(new ReqConsultaTipoDocumento(datos.getTipo_documento()), domainSerivices + consultaTipoId);
            ResConsultaTipoDocumento resTipoId = new ResConsultaTipoDocumento();
            if (!jsonRes.equals("")) {
                ArrayList<String> nodos = new ArrayList<String>();
                nodos.add("getIdIdentificacionResp");
                resTipoId = (ResConsultaTipoDocumento) this.util.jsonToObject(resTipoId, jsonRes, nodos);
                if (resTipoId == null) {
                    log.error("Ocurrio un inesperado con el servicio : " + consultaTipoId);
                    resTipoId = new ResConsultaTipoDocumento();
                }
                else {
                	datos.setTipo_documento(resTipoId.getIdIdentificacion().getIdDocumento());
                	jsonRes= this.util.callRestPost(datos, domainSerivices + consultaTipoId);
                	if (!jsonRes.equals("")) {
                		res = (ResConsultaDocumento) this.util.jsonToObject(res, jsonRes);
                		if (res == null) {
                            log.error("Ocurrio un inesperado con el servicio : " + consultaTipoId);
                            res = new ResConsultaDocumento();
                        }
                	}
                }
                res.setCodTipoDocumento(resTipoId.getIdIdentificacion().getIdDocumento());
            } 
            else {
                log.error("\nServicio incaccesible: " + consultaTipoId);
                resTipoId = new ResConsultaTipoDocumento();
            }
        } 
    	catch (ParseException ex) {
            ex.printStackTrace();
        }
        return res;
    }
    
    /*
     * Metodo para consumir servicio de relación de documento con la persona.
     */
    public ResAltaRelacionDocumento relacionarDocumento(ReqAltaRelacionDocumento datos) {
    	ResAltaRelacionDocumento res = new ResAltaRelacionDocumento();
    	try {
            String jsonRes = this.util.callRestPost(datos, domainSerivices + altaRelacionDocumento);
            if (!jsonRes.equals("")) {
                ArrayList<String> nodos = new ArrayList<String>();
                nodos.add("AltaDocumentosPersonaResponse");
                nodos.add("return");
                nodos.add("RESPONSE");
                ResAltaDocumentoTCB resId = (ResAltaDocumentoTCB) this.util.jsonToObject(res, jsonRes, nodos);
                if(resId!=null){
                	//Hacer la relación de los documentos
                	jsonRes = this.util.callRestPost(datos, domainSerivices + altaRelacionDocumento);
                }
            } 
            else {
                log.error("\nServicio incaccesible: " + altaRelacionDocumento);
            }
        } 
    	catch (ParseException ex) {
            ex.printStackTrace();
        }
        return res;
    }
}
