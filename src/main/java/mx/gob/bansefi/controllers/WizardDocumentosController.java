package mx.gob.bansefi.controllers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import mx.gob.bansefi.clients.DocumentosClient;
import mx.gob.bansefi.dto.bsfOperador.BsfOperadorPadreDTO;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import mx.gob.bansefi.dto.Modelos.BsfOperador;
import mx.gob.bansefi.clients.CatalogoClient;
import mx.gob.bansefi.dto.ClsSeguridad;
import mx.gob.bansefi.dto.DatosEncryptDigitaliza;
import mx.gob.bansefi.dto.ResponceSecurity;
import mx.gob.bansefi.dto.DocumentoDigitalizadoDTO;
import mx.gob.bansefi.dto.encriptar;
import mx.gob.bansefi.dto.Modelos.TransportDTO;
import mx.gob.bansefi.dto.Modelos.DocumentosMODEL;
import mx.gob.bansefi.dto.Request.ReqAltaRelacionDocumentoDTO;
import mx.gob.bansefi.dto.Request.ReqEncryptDecryptDTO;
import mx.gob.bansefi.dto.Request.Documentos.ReqConsultaDocumento;
import mx.gob.bansefi.dto.Request.Documentos.ReqConsultaDocumentosTCB;
import mx.gob.bansefi.dto.Request.Documentos.ReqEncryptORDecryptDTO;
import mx.gob.bansefi.dto.Response.ResEncryptORDecryptDTO;
import mx.gob.bansefi.dto.Response.ResGralDTO;
import mx.gob.bansefi.dto.Response.Documentos.ResConsultaDocumento;
import mx.gob.bansefi.dto.clientServices.request.ReqCatalogoVariosPMDTO;
import mx.gob.bansefi.dto.clientServices.response.resCatalogosVariosPM.CatalogoDTO;
import mx.gob.bansefi.dto.clientServices.response.resCatalogosVariosPM.ResCatalogosVariosPMDTO;
import mx.gob.bansefi.services.SecurityService;
import mx.gob.bansefi.services.SecurityWS;
import mx.gob.bansefi.services.WsServicios;
import mx.gob.bansefi.utils.Util;

@RestController
public class WizardDocumentosController {

    private static final Logger log = LogManager.getLogger(WizardDocumentosController.class);
    @Value("${url.Digitalizacion}")
    private String urlDigitalizacion;
    @Value("${url.LocalHost}")
    private String urlLocalHost;
    @Value("${url.context}")
    private String urlServidor;
    @Value("${url.decrypt}")
    private String urlDecrypt;
    @Value("${url.encrypt}")
    private String urlEncrypt;
    @Value("${path.RelacionDocumentoOperacion}")
    private String pathRelacionDocumentoOperacion;
    @Value("${path.ConsultaRelacion}")
    private String pathConsultaRelacion;
    @Value("${path.ConsultaDocumento}")
    private String pathConsultaDocumento;
    @Value("${path.returnDigitalizacion}")
    private String pathReturnDigitalizacion;
    @Value("${domain.servicesU}")
    private String urlServicesU;
    @Value("${url.setRelacionDocTCB}")
    private String pathRelacionDocTCB;
    @Value("${server.context-path}")
    private String context;
    
    private Util util = Util.getInstance();
    @Autowired
    SecurityWS securityWs;
    @Autowired
    CatalogoClient catalogoClient;
    @Autowired
    DocumentosClient documentosClient;

    /*METODO PARA LA CONSULTA DEL BASE 64 DE UN DOCUMENTO*/
    @RequestMapping("/getDocumento")
    public ResConsultaDocumento consultaDocumento(@ModelAttribute("data") ReqConsultaDocumento datos) {
    	ResConsultaDocumento res = new ResConsultaDocumento();
        try {
        	res = documentosClient.consultaBase64(datos);
            
        } catch (Exception ex) {
            System.out.println("Error en consultaDocumento: " + ex.getMessage());
            return null;
        }

        return res;
    }
    
    /*METODO PARA GENERAR EL BSFOPERADOR QUE SE ENVIARÁ AL MÓDULO DE DIGITALIZACIÓN*/
    @RequestMapping(value = "/encriptar", method = RequestMethod.POST)
    public ResEncryptORDecryptDTO encripcion(@RequestParam("objStr") String datos) {
    	String url = urlLocalHost + context + "/busquedaDig";
    	DatosEncryptDigitaliza datosGenerales = new DatosEncryptDigitaliza();
    	ResEncryptORDecryptDTO encryptDatos = securityWs.encrypt(new ReqEncryptORDecryptDTO(datos));
		try {
			datosGenerales = (DatosEncryptDigitaliza) util.jsonToObject(new DatosEncryptDigitaliza(), datos);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
    	ResEncryptORDecryptDTO bsfOperadorDecrypt = securityWs.decrypt(new ReqEncryptORDecryptDTO(datosGenerales.getBSFOPERADOR()));
		if(bsfOperadorDecrypt.getRespuesta()!=null) {
			BsfOperadorPadreDTO bsfOp;
			try {
				bsfOp = (BsfOperadorPadreDTO) util.jsonToObject(new BsfOperadorPadreDTO(), bsfOperadorDecrypt.getRespuesta());
				String aencriptar = "{\"BSFOPERADOR\": {\"ENTIDAD\": \"" + bsfOp.getBSFOPERADOR().getENTIDAD() + "\", \"CENTRO\": \"" 
						+ bsfOp.getBSFOPERADOR().getCENTRO() + "\", \"TERMINAL\": \""+ bsfOp.getBSFOPERADOR().getTERMINAL() 
						+ "\", \"USERTCB\": \"" + bsfOp.getBSFOPERADOR().getUSERTCB()+ "\", \"SESSIONID\": \"\","
						+ "\"TRANSPORT\": {\"IDINTERNOPE\": \""+encryptDatos.getRespuesta().toString()+"\", \"ACTIONBACK\": \""+url
						+ "\", \"TITULO\": \"Digitalizar documento " + datosGenerales.getDescDoc()
	                    + "\", \"TIPODOCUMENTO\": \"" + datosGenerales.getCodDoc() + "\",\"TARGET\": \"_top\"}}}";
				ResEncryptORDecryptDTO encrypt = securityWs.encrypt(new ReqEncryptORDecryptDTO(aencriptar));
				System.out.println("############Texto a encriptado###########\n " + encrypt.getRespuesta().toString());
				if (encrypt.getError() != null) {
	            	encrypt.setError(null);
	                System.out.println(encrypt.getError());
	            }
				return encrypt;
			} catch (ParseException e) {
				e.printStackTrace();
				return null;
			}
		}
		else {
			return null;
		}
    }
    
}
