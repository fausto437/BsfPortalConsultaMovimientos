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
import mx.gob.bansefi.dto.documentos;
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

    @RequestMapping("/getDocumento")
    public ResConsultaDocumento consultaDocumento(@ModelAttribute("data") ReqConsultaDocumento datos) {
    	ResConsultaDocumento res = new ResConsultaDocumento();
        try {
        	res = documentosClient.consultaBase64(datos);
            
        } catch (Exception ex) {
            System.out.println("Error en consultaDocumento: " + ex.getMessage());
        }

        return res;
    }
    /*@RequestMapping(value = "/documentos")
    public ModelAndView documentos(Model model, @ModelAttribute("DocumentosMODEL") final DocumentosMODEL DatosGenerales) {

        // ////////////////////decrypt BSFOPERADOR //////////////////
        BsfOperador bsfOperadorDecrypt = securityWs.decriptBsfOperadorDoc(new ReqEncryptORDecryptDTO(DatosGenerales.getBsfOperador()));
        // si el servicio de decrypt falla retornar pagina de error
        // *******************
        if (bsfOperadorDecrypt.getBSFOPERADOR().getStatus() != 1) {
            return new ModelAndView("error/500").addObject("msgError", "Error en los servicios (decrypt), intente de nuevo, si el error persiste contacte a sistemas.");
        }
        String bsfOperadorDTOencryp = Util.getInstance().objectToJson(bsfOperadorDecrypt.getBSFOPERADOR());
        ResEncryptORDecryptDTO bsfOperadorDTOEncrypt = securityWs.encrypt(new ReqEncryptORDecryptDTO(bsfOperadorDTOencryp));
        if (bsfOperadorDTOEncrypt.getCodRet() != 1) {
            return new ModelAndView("error/500").addObject("msgError", "Error en los servicios (encrypt), intente de nuevo, si el error persiste contacte a sistemas.");
        }
        DatosGenerales.setBsfOperadorDTO(bsfOperadorDTOEncrypt.getRespuesta());
        //obtener la lista de los documentos relacionados en TCB
        ResConsultaDocumentosTCB resConsultaDocumentosTCB = documentosClient.consultaDocumentosTCB(new ReqConsultaDocumentosTCB(bsfOperadorDecrypt.getBSFOPERADOR().getUSERTCB(), bsfOperadorDecrypt.getBSFOPERADOR().getPASSTCB(), bsfOperadorDecrypt.getBSFOPERADOR().getENTIDAD(), DatosGenerales.getIdIntPe(), bsfOperadorDecrypt.getBSFOPERADOR().getTERMINAL()));
        if (!resConsultaDocumentosTCB.getEstatus().equals("1")) {
            return new ModelAndView("error/500").addObject("msgError", "Error en los servicios (consultaDocumentosTCB), intente de nuevo, si el error persiste contacte a sistemas.");
        }
        ArrayList<documentos> lista = new ArrayList<documentos>();
        //recorremos la lista de documento de TCB para setear a la lista de la vista
        for (DocumentosRelacionadosDTO element : resConsultaDocumentosTCB.getListaDocumentosRelacionados()) {
            documentos doc = new documentos();
            doc.setDescripcion(element.getTxt_DC());
            doc.setId_tipo_documento(element.getCod_DOC());
            doc.setFecha(element.getFecha_ENT_DC().substring(6, 8) + "/" + element.getFecha_ENT_DC().substring(4, 6) + "/" + element.getFecha_ENT_DC().substring(0, 4));
            doc.setId_documento(element.getId_INTERNO_DC());
            lista.add(doc);
        }
        model.addAttribute("list", lista);
        return new ModelAndView("documentos/Documentos").addObject("urlActionBack", urlDigitalizacion).addObject("model", DatosGenerales);
    }*/
    
    @RequestMapping(value = "/encriptar", method = RequestMethod.POST)
    public ResEncryptORDecryptDTO encripcion(@ModelAttribute("obj") DatosEncryptDigitaliza datos) {
    	String url = urlLocalHost + context + "/busquedaDig";
    	
    	ResEncryptORDecryptDTO bsfOperadorDecrypt = securityWs.decrypt(new ReqEncryptORDecryptDTO(datos.getBSFOPERADOR()));
		if(bsfOperadorDecrypt.getRespuesta()!=null) {
			BsfOperadorPadreDTO bsfOp;
			try {
				bsfOp = (BsfOperadorPadreDTO) util.jsonToObject(new BsfOperadorPadreDTO(), bsfOperadorDecrypt.getRespuesta());
				String aencriptar = "{\"BSFOPERADOR\": {\"ENTIDAD\": \"" + bsfOp.getBSFOPERADOR().getENTIDAD() + "\", \"CENTRO\": \"" + bsfOp.getBSFOPERADOR().getCENTRO() 
	        			+ "\", \"TERMINAL\": \""+ bsfOp.getBSFOPERADOR().getTERMINAL() + "\", \"USERTCB\": \"" + bsfOp.getBSFOPERADOR().getUSERTCB()
	                    + "\", \"SESSIONID\": \"\", \"TRANSPORT\": {\"ACTIONBACK\": \""+url+"\", \"TITULO\": \"Digitalizar documento " + datos.getDescDoc()
	                    + "\", \"TIPODOCUMENTO\": \"" + datos.getCodDoc() + "\",\"TARGET\": \"_top\", \"IDINTERNOPE\": {\"BSFOPERADORINICIO\": \""+datos.getBSFOPERADOR()
	                    + "\", \"IDINTERNOPE\": \"" + datos.getIdInternoPe() + "\", \"DESCDOC\": \"" + datos.getDescDocDB() + "\", \"ACUERDO\": \"" + datos.getCuenta() 
	                    + "\", \"CODDOC\": \"" + datos.getCodDoc() + "\", \"TITULAR\": \"" + datos.getTitular() + "\" }}}}";
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

    /*@RequestMapping(value = "/addDocumentos")
    public ModelAndView adddocumentos(Model model, @ModelAttribute("AddDocumentosMODEL") final AddDocumentosMODEL DatosGenerales) {

        // ////////////////////decrypt BSFOPERADOR //////////////////
        BsfOperador bsfOperadorDecrypt = securityWs.decriptBsfOperadorDoc(new ReqEncryptORDecryptDTO(DatosGenerales.getBsfOperador()));
        // si el servicio de decrypt falla retornar pagina de error
        // *******************
        if (bsfOperadorDecrypt.getBSFOPERADOR().getStatus() != 1) {
            return new ModelAndView("error/500").addObject("msgError", "Error en los servicios (decrypt), intente de nuevo, si el error persiste contacte a sistemas.");
        }
        String bsfOperadorDTOencryp = Util.getInstance().objectToJson(bsfOperadorDecrypt.getBSFOPERADOR());
        ResEncryptORDecryptDTO bsfOperadorDTOEncrypt = securityWs.encriptBsfOperador(new ReqEncryptORDecryptDTO(bsfOperadorDTOencryp));
        if (bsfOperadorDTOEncrypt.getCodRet() != 1) {
            return new ModelAndView("error/500").addObject("msgError", "Error en los servicios (encrypt), intente de nuevo, si el error persiste contacte a sistemas.");
        }
        DatosGenerales.setBsfOperadorDTO(bsfOperadorDTOEncrypt.getRespuesta());

        //obtener la lista de los documentos relacionados en TCB
        ResConsultaDocumentosTCB resConsultaDocumentosTCB = documentosClient.consultaDocumentosTCB(new ReqConsultaDocumentosTCB(bsfOperadorDecrypt.getBSFOPERADOR().getUSERTCB(), bsfOperadorDecrypt.getBSFOPERADOR().getPASSTCB(), bsfOperadorDecrypt.getBSFOPERADOR().getENTIDAD(), DatosGenerales.getIdIntPe(), bsfOperadorDecrypt.getBSFOPERADOR().getTERMINAL()));
        if (!resConsultaDocumentosTCB.getEstatus().equals("1")) {
            return new ModelAndView("error/500").addObject("msgError", "Error en los servicios (consultaDocumentosTCB), intente de nuevo, si el error persiste contacte a sistemas.");
        }
        ArrayList<documentos> lista = new ArrayList<documentos>();
        //recorremos la lista de documento de TCB para setear a la lista de la vista
        for (DocumentosRelacionadosDTO element : resConsultaDocumentosTCB.getListaDocumentosRelacionados()) {
            documentos doc = new documentos();
            doc.setDescripcion(element.getTxt_DC());
            doc.setId_tipo_documento(element.getCod_DOC());
            doc.setFecha(element.getFecha_ENT_DC().substring(6, 8) + "/" + element.getFecha_ENT_DC().substring(4, 6) + "/" + element.getFecha_ENT_DC().substring(0, 4));
            doc.setId_documento(element.getId_INTERNO_DC());
            lista.add(doc);
        }

        model.addAttribute("list", lista);
        return new ModelAndView("documentos/addDocumentos").addObject("urlActionBack", urlDigitalizacion).addObject("model", DatosGenerales);
    }

    @RequestMapping(value = "/docPrueba")
    public ModelAndView docPrueba() {
        return new ModelAndView("documentos/pruebaPost");
    }

    @RequestMapping(value = "/documentoDigitalizado", method = RequestMethod.POST, produces = "application/json")
    public
    @ResponseBody
    ModelAndView documentoDigitalizadoPost(Model model, @RequestParam("TRANSPORT") String BsfOperador) {

        JSONObject json = null;
        String ENTIDAD = "";
        String CENTRO = "";
        String TERMINAL = "";
        String USERTCB = "";
        String SESSIONID = "";
        String mensaje = "";
        String idinternope = "";
        String statusDigitalizacion = "";
        String msgError = "";
        String id_documento = "";
        String contrasena = "";
        String IdIntPe = "";
        ResponceSecurity responceSecurity = SecurityService.DesEncriptar(BsfOperador, urlServidor + urlDecrypt);
        if (responceSecurity.getStatus() == 1) {
            try {
                json = new JSONObject(responceSecurity.getResponce());

                ENTIDAD = json.getJSONObject("BSFOPERADOR").get("ENTIDAD").toString();
                CENTRO = json.getJSONObject("BSFOPERADOR").get("CENTRO").toString();
                TERMINAL = json.getJSONObject("BSFOPERADOR").get("TERMINAL").toString();
                USERTCB = json.getJSONObject("BSFOPERADOR").get("USERTCB").toString();
                SESSIONID = json.getJSONObject("BSFOPERADOR").get("SESSIONID").toString();
                id_documento = json.getJSONObject("BSFOPERADOR").getJSONObject("TRANSPORT").get("IDDOCUMENTO").toString();
                idinternope = json.getJSONObject("BSFOPERADOR").getJSONObject("TRANSPORT").get("IDINTERNOPE").toString();
                msgError = json.getJSONObject("BSFOPERADOR").getJSONObject("TRANSPORT").get("MESSAGE").toString();
                statusDigitalizacion = json.getJSONObject("BSFOPERADOR").getJSONObject("TRANSPORT").get("STATUS").toString();
                String[] cad = idinternope.split("\\|");
                IdIntPe = cad[0];
                contrasena = cad[1];
                String tipoDocDigitalizado = cad[4];
                String desDocDigitalizado = cad[5];
                ArrayList<documentos> lista = new ArrayList<documentos>();
                if (id_documento.isEmpty()) {
                    id_documento = "";
                }
                BsfOperadorDTO bsfOperadorDTO = new BsfOperadorDTO();
                bsfOperadorDTO.setENTIDAD(ENTIDAD);
                bsfOperadorDTO.setCENTRO(CENTRO);
                bsfOperadorDTO.setTERMINAL(TERMINAL);
                bsfOperadorDTO.setUSERTCB(USERTCB);
                bsfOperadorDTO.setSESSIONID(SESSIONID);
                bsfOperadorDTO.setPASSTCB(contrasena);
                TransportDTO t = new TransportDTO();
                t.setIDINTERNOPE(IdIntPe);
                bsfOperadorDTO.setTRANSPORT(t);
                BsfOperador bsf = new BsfOperador(bsfOperadorDTO);
                String text = Util.getInstance().objectToJson(bsf);
                ReqEncryptDTO req = new ReqEncryptDTO(text);
                Responce bsfOperadorEncr = AltaModificacionServices.encriptBsfOperador(req, urlServidor + urlEncrypt);
                String bsfOperadorDTOencryp = Util.getInstance().objectToJson(bsfOperadorDTO);
                ResEncryptORDecryptDTO bsfOperadorDTOEncrypt = securityWs.encriptBsfOperador(new ReqEncryptORDecryptDTO(bsfOperadorDTOencryp));
                if (bsfOperadorDTOEncrypt.getCodRet() != 1) {
                    return new ModelAndView("error/500").addObject("msgError", "Error en los servicios (encrypt), intente de nuevo, si el error persiste contacte a sistemas.");
                }
                if (bsfOperadorEncr.getStatus() == 1) {
                    text = Util.getInstance().objectToJson(bsfOperadorDTO);
                    req = new ReqEncryptDTO(text);
                    Responce bsfOperadorDTOEncr = AltaModificacionServices.encriptBsfOperador(req, urlServidor + urlEncrypt);
                    if (bsfOperadorDTOEncr.getStatus() != 1) {
                        System.out.println("\nbsfOperador Status: " + bsfOperadorEncr.getStatus() + "\nmsg: " + bsfOperadorEncr.getDescripcion());
                        return new ModelAndView("error/500").addObject("msgError", "Error en consulta de servicios (encrypt), intente de nuevo, si el error persiste contacte a sistemas.");
                    }
                    if (statusDigitalizacion.equals("1") && !id_documento.equals("")) {
                        String resRelacionSQL = "";
                        //alta de documento en TCB (relacion en TCB)
                        ReqAltaRelacionDocumentoDTO reqAltaReDocTCB = new ReqAltaRelacionDocumentoDTO(IdIntPe, tipoDocDigitalizado, CENTRO, desDocDigitalizado);
                        reqAltaReDocTCB.setEntidad(ENTIDAD);
                        reqAltaReDocTCB.setPassword(contrasena);
                        reqAltaReDocTCB.setTerminal(TERMINAL);
                        reqAltaReDocTCB.setUsuario(USERTCB);
                        ResAltaDocTCB resAltaDocTCB = WsServicios.altaRelacioDocumento(reqAltaReDocTCB, urlServicesU + pathRelacionDocTCB);//<--- Alta doc TCB

                        if (!resAltaDocTCB.getEstatus().equals("1"))
                            log.error("\nError en el servicio altaRelacioDocumentoTCB\nSTATUS: " + resAltaDocTCB.getEstatus() + "\nmsgError: " + resAltaDocTCB.getMensaje());
                        else{
                            int idDocTCB = Integer.valueOf(resAltaDocTCB.getId_INTERNO_DC());
                            resRelacionSQL = relacionDocumentoPersona(id_documento, IdIntPe, String.valueOf(idDocTCB));//<----relacion en SQL
                        }


                        //obtener la lista de los documentos relacionados en TCB
                        ResConsultaDocumentosTCB resConsultaDocumentosTCB = documentosClient.consultaDocumentosTCB(new ReqConsultaDocumentosTCB(USERTCB, contrasena, ENTIDAD, IdIntPe, TERMINAL));
                        if (!resConsultaDocumentosTCB.getEstatus().equals("1")) {
                            return new ModelAndView("error/500").addObject("msgError", "Error en los servicios (consultaDocumentosTCB), intente de nuevo, si el error persiste contacte a sistemas.");
                        }
                        //recorremos la lista de documento de TCB para setear a la lista de la vista
                        for (DocumentosRelacionadosDTO element : resConsultaDocumentosTCB.getListaDocumentosRelacionados()) {
                            documentos doc = new documentos();
                            doc.setDescripcion(element.getTxt_DC());
                            doc.setId_tipo_documento(element.getCod_DOC());
                            doc.setFecha(element.getFecha_ENT_DC().substring(6, 8) + "/" + element.getFecha_ENT_DC().substring(4, 6) + "/" + element.getFecha_ENT_DC().substring(0, 4));
                            doc.setId_documento(element.getId_INTERNO_DC());
                            lista.add(doc);
                        }
                    }

                    model.addAttribute("list", lista);
                    model.addAttribute("Tama�oListDocumentos", lista.size());

                    if (cad[2].equals("1")) {
                        DocumentosMODEL DatosGenerales = new DocumentosMODEL(bsfOperadorEncr.getDescripcion(),bsfOperadorDTOEncrypt.getRespuesta() ,IdIntPe,"","","");
                        return new ModelAndView("documentos/Documentos").addObject("urlActionBack", urlDigitalizacion).addObject("model", DatosGenerales);
                    } else {
                        AddDocumentosMODEL DatosGenerales = new AddDocumentosMODEL(bsfOperadorEncr.getDescripcion(), bsfOperadorDTOEncr.getDescripcion(), IdIntPe, cad[3]);
                        return new ModelAndView("documentos/addDocumentos").addObject("urlActionBack", urlDigitalizacion).addObject("model", DatosGenerales);
                    }
                } else {
                    System.out.println("\nbsfOperador Status: " + bsfOperadorEncr.getStatus() + "\nmsg: " + bsfOperadorEncr.getDescripcion());
                    return new ModelAndView("error/500").addObject("msgError", "Error en consulta de servicios(encrypt), intente de nuevo, si el error persiste contacte a sistemas.");
                }
            } catch (Exception e) {
                System.out.println("\nDatos incompletos");
                return new ModelAndView("error/500").addObject("msgError", "Error al recibir los datos, intente de nuevo, si el error persiste contacte a sistemas.");
            }
        } else {
            System.out.println("\nbsfOperador Status: " + responceSecurity.getStatus() + "\nmsg: " + responceSecurity.getMsgError());
            return new ModelAndView("error/500").addObject("msgError", "Error en consulta de servicios(Decrypt), intente de nuevo, si el error persiste contacte a sistemas.");
        }
    }

    //// encriptar
    @RequestMapping(value = "/encriptar", method = RequestMethod.POST)
    public
    @ResponseBody
    encriptar encriptado(Model model, @ModelAttribute("obj") DatosEncryptDigitaliza datos) {

        // System.out.println("\ntipoDoc: " + datos.getTipoDoc() + "\nidPersona: " + datos.getIdInternoPe() + "\nBSF: " + datos.getBSFOPERADOR());

        // String ruta = datosNuevos[0];
        String tipo_doc = datos.getTipoDoc();

        String url = urlLocalHost + pathReturnDigitalizacion;
        ReqEncryptDTO req = new ReqEncryptDTO(datos.getBSFOPERADOR());
        BsfOperador bsfOperador = AltaModificacionServices.decriptBsfOperador(req, urlServidor + urlDecrypt);
        if (bsfOperador.getBSFOPERADOR().getStatus() == 1) {
            String idInternoP = datos.getIdInternoPe() + "|" + bsfOperador.getBSFOPERADOR().getPASSTCB() + "|" + datos.getAlta() + "|" + datos.getNombre() + "|" + tipo_doc + "|" + datos.getDescDoc();
            String aencriptar = "{'BSFOPERADOR': {'ENTIDAD': '" + bsfOperador.getBSFOPERADOR().getENTIDAD() + "', 'CENTRO': '" + bsfOperador.getBSFOPERADOR().getCENTRO() + "', 'TERMINAL': '"
                    + bsfOperador.getBSFOPERADOR().getTERMINAL() + "', 'USERTCB': '" + bsfOperador.getBSFOPERADOR().getUSERTCB()// framePrincipal
                    + "', 'SESSIONID': '" + bsfOperador.getBSFOPERADOR().getSESSIONID() + "', 'TRANSPORT': {'ACTIONBACK':'" + url + "','TITULO':'Digitalizar documento " + datos.getDescDoc()
                    + "', 'TIPODOCUMENTO':'" + tipo_doc + "', 'TARGET':'framePrincipal', 'IDINTERNOPE':'" + idInternoP + "' }}}";
            // framePrincipal _top
            // System.out.println("entro a encriptar: " + aencriptar);

            ClsSeguridad respEncr = Encriptar(aencriptar);
            System.out.println("###################################################################texto a encriptado:" + respEncr.getRespuesta().toString());

            encriptar encriptada = new encriptar();
            encriptada.encriptado = respEncr.getRespuesta().toString();

            // System.out.println("msgError " + respEncr.getError());
            // System.out.println("Status " + respEncr.getEstatus());
            if (respEncr.getEstatus() != 1) {
                respEncr.setError(null);
                System.out.println(respEncr.getError());
            }
            return encriptada;
        } else {
            System.out.println("\nbsfOperador Status: " + bsfOperador.getBSFOPERADOR().getStatus() + "\nmsg: " + bsfOperador.getBSFOPERADOR().getDescripcion());
            encriptar encriptada = new encriptar();
            encriptada.setEncriptado(null);
            return encriptada;
        }
    }

    public ClsSeguridad Encriptar(String Cadena) {
        String Salida = "";
        Cadena = Cadena.replaceAll("É", "E");
        ClsSeguridad Resp = new ClsSeguridad();
        try {
            JSONObject jsonObject = new JSONObject().put("text", Cadena);
            String input = jsonObject.toString();
            try {
                URL restServiceURL = new URL(urlServidor + urlEncrypt);
                HttpURLConnection httpConnection = (HttpURLConnection) restServiceURL.openConnection();
                httpConnection.setDoOutput(true);
                httpConnection.setRequestMethod("POST");
                httpConnection.setRequestProperty("Content-Type", "application/json");
                OutputStream outputStream = httpConnection.getOutputStream();
                outputStream.write(input.getBytes());
                outputStream.flush();
                if (httpConnection.getResponseCode() != 200) {
                    throw new RuntimeException("Failed : HTTP error code : " + httpConnection.getResponseCode());
                }
                BufferedReader responseBuffer = new BufferedReader(new InputStreamReader((httpConnection.getInputStream()), "UTF8"));
                String output;
                while ((output = responseBuffer.readLine()) != null) {
                    Salida += output;
                }
                JSONObject json = new JSONObject(Salida);

                Resp.setRespuesta(json.getString("respuesta"));
                Resp.setError(json.getString("error"));
                Resp.setError(json.getString("error"));
                Resp.setEstatus(Integer.parseInt(json.getString("codRet")));
                httpConnection.disconnect();

            } catch (Exception ex) {
                Resp.setError(ex.getMessage());
                Resp.setEstatus(0);
            }
        } catch (Exception ex) {
            Resp.setError(ex.getMessage());
            Resp.setEstatus(0);
        }

        return Resp;
    }

    public ClsSeguridad DesEncriptar(String Cadena) {
        String Salida = "";
        ClsSeguridad Resp = new ClsSeguridad();
        try {
            JSONObject jsonObject = new JSONObject().put("text", Cadena);
            String input = jsonObject.toString();
            try {
                URL restServiceURL = new URL(urlServidor + urlDecrypt);
                HttpURLConnection httpConnection = (HttpURLConnection) restServiceURL.openConnection();
                httpConnection.setDoOutput(true);
                httpConnection.setRequestMethod("POST");
                httpConnection.setRequestProperty("Content-Type", "application/json");
                OutputStream outputStream = httpConnection.getOutputStream();
                outputStream.write(input.getBytes());
                outputStream.flush();
                if (httpConnection.getResponseCode() != 200) {
                    throw new RuntimeException("Failed : HTTP error code : " + httpConnection.getResponseCode());
                }
                BufferedReader responseBuffer = new BufferedReader(new InputStreamReader((httpConnection.getInputStream()), "UTF8"));
                String output;
                while ((output = responseBuffer.readLine()) != null) {
                    Salida += output;
                }
                JSONObject json = new JSONObject(Salida);

                Resp.setRespuesta(json.getString("respuesta"));
                Resp.setError(json.getString("error"));
                Resp.setError(json.getString("error"));
                Resp.setEstatus(Integer.parseInt(json.getString("codRet")));
                httpConnection.disconnect();

            } catch (Exception ex) {
                Resp.setError(ex.getMessage());
                Resp.setEstatus(0);
            }
        } catch (Exception ex) {
            Resp.setError(ex.getMessage());
            Resp.setEstatus(0);
        }

        return Resp;
    }

    public String relacionDocumentoPersona(String id_documento, String id_persona, String id_documentoTCB) {
        String Salida = "";
        String respuesta = "";
        try {
            // JSONObject jsonObject = new JSONObject().put("text",Cadena);
            String input = "{'IdDocumento':'" + id_documento + "','IdTipoOperacion':'5','IdOperacion':'" + id_persona + "','IdDocumentoTCB':'" + id_documentoTCB + "'}";
            try {
                URL restServiceURL = new URL(urlServidor + pathRelacionDocumentoOperacion);
                HttpURLConnection httpConnection = (HttpURLConnection) restServiceURL.openConnection();
                httpConnection.setDoOutput(true);
                httpConnection.setRequestMethod("POST");
                httpConnection.setRequestProperty("Content-Type", "application/json");
                OutputStream outputStream = httpConnection.getOutputStream();
                outputStream.write(input.getBytes());
                outputStream.flush();
                if (httpConnection.getResponseCode() != 200) {
                    throw new RuntimeException("Failed : HTTP error code : " + httpConnection.getResponseCode());
                }
                BufferedReader responseBuffer = new BufferedReader(new InputStreamReader((httpConnection.getInputStream()), "UTF8"));
                String output;
                while ((output = responseBuffer.readLine()) != null) {
                    Salida += output;
                }
                JSONObject json = new JSONObject(Salida);
                respuesta = json.getJSONObject("InsertaRelacionDocumentoTCBResp").getJSONObject("RespuestaRelacion").get("Respuesta").toString();
                httpConnection.disconnect();

            } catch (Exception ex) {
                System.out.println("error: " + ex.getMessage());

            }
        } catch (Exception ex) {
            System.out.println("error: " + ex.getMessage());
        }

        return respuesta;
    }

    public ArrayList<documentos> getDocumentosByUserId(String id) {
        ArrayList<documentos> lista = new ArrayList<documentos>();
        documentos documentoLista = new documentos();
        String Salida = "";
        try {
            JSONObject jsonObject = new JSONObject().put("ConsultaRelacionDocumento:{\"IdOperacion\"", id);
            String input = "{'ConsultaRelacionDocumento':{'IdOperacion':'" + id + "'}}";

            // try {
            URL restServiceURL = new URL(urlServidor + pathConsultaRelacion);
            HttpURLConnection httpConnection = (HttpURLConnection) restServiceURL.openConnection();
            httpConnection.setDoOutput(true);
            httpConnection.setRequestMethod("POST");
            httpConnection.setRequestProperty("Content-Type", "application/json");
            OutputStream outputStream = httpConnection.getOutputStream();
            outputStream.write(input.getBytes());
            outputStream.flush();
            if (httpConnection.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + httpConnection.getResponseCode());
            }
            BufferedReader responseBuffer = new BufferedReader(new InputStreamReader((httpConnection.getInputStream()), "UTF8"));
            String output;
            while ((output = responseBuffer.readLine()) != null) {
                Salida += output;
            }
            JSONObject json = new JSONObject(Salida);

            JSONObject songs = json.getJSONObject("ConsultaRelacionDocumentoResp");
            Iterator x = songs.keys();
            String datos = "";
            while (x.hasNext()) {
                String key = (String) x.next();
                datos = songs.get(key).toString();
            }
            if (datos.contains("[")) {
                JSONArray jsonarray = new JSONArray(datos);
                for (int i = 0; i < jsonarray.length(); i++) {
                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                    String id_op = jsonobject.get("IdOperacion").toString();
                    String id_tipoOp = jsonobject.get("IdTipoOperacion").toString();
                    String id_docu = jsonobject.get("IdDocumento").toString();
                    documentoLista = consultaDocumento(id_docu);
                    lista.add(documentoLista);
                }
            } else {
                JSONObject jsonobject = new JSONObject(datos);
                String id_op = jsonobject.get("IdOperacion").toString();
                String id_tipoOp = jsonobject.get("IdTipoOperacion").toString();
                String id_docu = jsonobject.get("IdDocumento").toString();

                documentoLista = consultaDocumento(id_docu);

                lista.add(documentoLista);
            }

            httpConnection.disconnect();

            // } catch (Exception ex) {
            // System.out.println("ERROR EN GET_LIST DOCUMENTOS: " + ex.getMessage().toString());
            // }
        } catch (Exception ex) {
            System.out.println("ERROR EN GET_LIST DOCUMENTOS: " + ex.getMessage().toString());
        }

        return lista;
  }/*/
    
}
