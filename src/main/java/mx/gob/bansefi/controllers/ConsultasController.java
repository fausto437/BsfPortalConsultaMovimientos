package mx.gob.bansefi.controllers;

import mx.gob.bansefi.clients.DocumentosClient;
import mx.gob.bansefi.dto.AltaDocumentoTCBDTO;
import mx.gob.bansefi.dto.AltaRelacionDocumentoDTO;
import mx.gob.bansefi.dto.AnotacionesDTO;
import mx.gob.bansefi.dto.ApunteDTO;
import mx.gob.bansefi.dto.BusquedaDTO;
import mx.gob.bansefi.dto.ConsultaPrincipalDTO;
import mx.gob.bansefi.dto.DatosEncryptDigitaliza;
import mx.gob.bansefi.dto.DetalleConsultaDTO;
import mx.gob.bansefi.dto.GralAnotacionDTO;
import mx.gob.bansefi.dto.GralApunteDTO;
import mx.gob.bansefi.dto.GralBloqueoDTO;
import mx.gob.bansefi.dto.GralRetencionDTO;
import mx.gob.bansefi.dto.ReportDTO;
import mx.gob.bansefi.dto.TitularDTO;
import mx.gob.bansefi.dto.Modelos.BsfOperadorDTO;
import mx.gob.bansefi.dto.Request.*;
import mx.gob.bansefi.dto.Request.Documentos.ReqAltaRelacionDocumento;
import mx.gob.bansefi.dto.Request.Documentos.ReqEncryptORDecryptDTO;
import mx.gob.bansefi.dto.Response.*;
import mx.gob.bansefi.dto.Response.Documentos.ResAltaRelacionDocumento;
import mx.gob.bansefi.dto.bsfOperador.BsfOperadorPadreDTO;
import mx.gob.bansefi.dto.bsfOperador.DatosTransportDTO;
import mx.gob.bansefi.process.SetConsultaDetallesProccess;
import mx.gob.bansefi.process.SetConsultaPrincipalProccess;
import mx.gob.bansefi.process.SetReporteProccess;
import mx.gob.bansefi.services.SecurityWS;
import mx.gob.bansefi.services.WsServicios;
import mx.gob.bansefi.utils.Util;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.SortDefault;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class ConsultasController {
    @Value("${domain.servicesU}")
    private String urlServU;
    @Value("${domain.services}")
    private String urlServ;
    @Value("${url.decrypt}")
    private String urldecrypt;
    @Value("${url.encrypt}")
    private String urlencrypt;
    @Value("${url.context}")
    private String urlcontext;
    @Value("${url.Digitalizacion}")
    private String urlDigitalizacion;
    @Value("${url.LocalHost}")
    private String urlLocalHost;
    @Value("${server.context-path}")
    private String context;
    @Value("${domain.servicesExternos}")
    private String urlRoot;
    @Value("${url.BusquedaAcuerdo}")
    private String urlBusquedaAcuerdo;

    @Autowired
    WsServicios wsServicios;
    private Util util = Util.getInstance();
    @Autowired
    SecurityWS securityWs;
    @Autowired
    private DocumentosClient documentosClient;
    @Autowired
    private SetConsultaDetallesProccess setDetalles;
    
    @Autowired
    private SetConsultaPrincipalProccess setConsultaPrincipal;
    
    private String packageTemplates = "ConsultasMovimientos";
    public String operador;
    
    @RequestMapping("/prueba")
    public ModelAndView pruaba() {
        return new ModelAndView(packageTemplates + "/pruebaPost");
    }

    // =================================Mapping desde la ventana de datos generales===========================//
    // Mapping de datos Generales a Los otros Modulos
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ModelAndView Index(@RequestParam("BSFOPERADOR") String bsfOperador) // Inicio del flujo
    {
		if (!bsfOperador.equals("")) {
			BusquedaDTO busquedaDatos = new BusquedaDTO();
            operador = bsfOperador;
            busquedaDatos.setBsfoperador(bsfOperador);
            busquedaDatos.setFormato("OFICINA");
            busquedaDatos.setVerificaDigitalizacion("0");
            ResEncryptORDecryptDTO transportDecrypt = securityWs.decrypt(new ReqEncryptORDecryptDTO(bsfOperador));
            try {
            	BsfOperadorPadreDTO bsfOp = (BsfOperadorPadreDTO) util.jsonToObject(new BsfOperadorPadreDTO(), transportDecrypt.getRespuesta());
            	if(bsfOp.getBSFOPERADOR().getTRANSPORT().getIDINTERNOPE()!="") {
            		busquedaDatos.setIdInternoPe(Integer.parseInt(bsfOp.getBSFOPERADOR().getTRANSPORT().getIDINTERNOPE()));
            		busquedaDatos.setNumAcuerdo(bsfOp.getBSFOPERADOR().getTRANSPORT().getACUERDO());
            	}
            	
            } catch (Exception ex) {
                System.out.print(ex.getMessage());
            }
            return new ModelAndView(packageTemplates + "/Buscador").addObject("Model", busquedaDatos).addObject("urlActionBack", urlRoot+urlDigitalizacion).addObject("urlActionFind",urlRoot+urlBusquedaAcuerdo);
        } else {
            return new ModelAndView("error/500").addObject("msgError", "ERROR AL RECIBIR LOS DATOS");

        }
    }
    
    //PANTALLA PRINCIPAL DE BUSQUEDA DESPUÉS DE LA DIGITALIZACIÓN
    @RequestMapping(value = "/busquedaDig{idDoc}")
    public ModelAndView BusquedaDespuesDeDigitalizar(@RequestParam("TRANSPORT") String TRANSPORT) {
    	BusquedaDTO busquedaDatos = new BusquedaDTO();
    	AltaDocumentoTCBDTO datosRelacion = new AltaDocumentoTCBDTO();
    	ResEncryptORDecryptDTO transportDecrypt = securityWs.decrypt(new ReqEncryptORDecryptDTO(TRANSPORT));
    	transportDecrypt.setRespuesta(transportDecrypt.getRespuesta().replace("'", "\""));
    	transportDecrypt.setRespuesta(transportDecrypt.getRespuesta().replace("\"{", "{"));
    	transportDecrypt.setRespuesta(transportDecrypt.getRespuesta().replace("}\"", "}"));
    	try {
			BsfOperadorPadreDTO bsfOp = (BsfOperadorPadreDTO) util.jsonToObject(new BsfOperadorPadreDTO(), transportDecrypt.getRespuesta());
			if(!bsfOp.getBSFOPERADOR().getTRANSPORT().getMESSAGE().contains("CANCELADO")) {
				ResEncryptORDecryptDTO datosDecrypt = securityWs.decrypt(new ReqEncryptORDecryptDTO(bsfOp.getBSFOPERADOR().getTRANSPORT().getIDINTERNOPE()));
				
				DatosEncryptDigitaliza datosDeDigitalizacion = (DatosEncryptDigitaliza) util.jsonToObject(new DatosEncryptDigitaliza(), datosDecrypt.getRespuesta());
				//LOS DATOS PARA LA RELACION DEL DOCUMENTO SE GUARDARÁN HASTA REALIZAR LA CONSULTA DE LAS TRANSACCIONES SE REALIZARA
				//LA RELACIÓN
				datosRelacion.setCentro(bsfOp.getBSFOPERADOR().getCENTRO());
				datosRelacion.setCodTipoDoc(""+datosDeDigitalizacion.getCodDoc());
				datosRelacion.setDescDoc(datosDeDigitalizacion.getDescDoc());
				datosRelacion.setEntidad(bsfOp.getBSFOPERADOR().getENTIDAD());
				datosRelacion.setFechaCaducidadDoc("9999/99/12");
				datosRelacion.setTerminal(bsfOp.getBSFOPERADOR().getTERMINAL());
				datosRelacion.setIdInternoPe(Integer.parseInt(datosDeDigitalizacion.getIdInternoPe()));
				
				//ResAltaRelacionDocumento resAltaRelacion = documentosClient.relacionarDocumento(new ReqAltaDocumentoTCBDTO(datosRelacion), bsfOp.getBSFOPERADOR().getTRANSPORT().getIDDOCUMENTO());
				
				if(bsfOp.getBSFOPERADOR().getTRANSPORT().getIDDOCUMENTO()==null) {
					busquedaDatos.setBsfoperador(datosDeDigitalizacion.getBSFOPERADOR());
					busquedaDatos.setFormato("OFICINA");
		            busquedaDatos.setVerificaDigitalizacion("nA");//Error en la digitalizacion.
		            busquedaDatos.setIdInternoPe(datosRelacion.getIdInternoPe());
		            busquedaDatos.setTitCuenta(datosDeDigitalizacion.getTitular());
		            busquedaDatos.setNumAcuerdo(datosDeDigitalizacion.getCuenta());
		            }
				else {
					busquedaDatos.setBsfoperador(datosDeDigitalizacion.getBSFOPERADOR());
					busquedaDatos.setFormato("OFICINA");
		            busquedaDatos.setVerificaDigitalizacion(bsfOp.getBSFOPERADOR().getTRANSPORT().getIDDOCUMENTO());//Se guarda el id del documento digitalizado.
		            busquedaDatos.setIdInternoPe(datosRelacion.getIdInternoPe());
		            busquedaDatos.setTitCuenta(datosDeDigitalizacion.getTitular());
		            busquedaDatos.setNumAcuerdo(datosDeDigitalizacion.getCuenta());
		            busquedaDatos.setRelacionDoc(datosRelacion);
				}
			}
			
			else {
				busquedaDatos.setIdInternoPe(datosRelacion.getIdInternoPe());
				busquedaDatos.setFormato("OFICINA");
	            busquedaDatos.setVerificaDigitalizacion("0");
				if(bsfOp.getBSFOPERADOR().getTRANSPORT().getIDINTERNOPE()!="") {
					ResEncryptORDecryptDTO datosDecrypt = securityWs.decrypt(new ReqEncryptORDecryptDTO(bsfOp.getBSFOPERADOR().getTRANSPORT().getIDINTERNOPE()));
					DatosEncryptDigitaliza datosDeDigitalizacion = (DatosEncryptDigitaliza) util.jsonToObject(new DatosEncryptDigitaliza(), datosDecrypt.getRespuesta());
					busquedaDatos.setBsfoperador(datosDeDigitalizacion.getBSFOPERADOR());
		            busquedaDatos.setTitCuenta(datosDeDigitalizacion.getTitular());
		            busquedaDatos.setNumAcuerdo(datosDeDigitalizacion.getCuenta());
				}
				else{
					busquedaDatos.setBsfoperador(TRANSPORT);
				}
			}
			
			
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	
    	return new ModelAndView(packageTemplates + "/Buscador").addObject("Model", busquedaDatos).addObject("urlActionBack", urlRoot+urlDigitalizacion);
    }
    
    //BUSQUEDA DEL NOMBRE
    @RequestMapping("/getNombre")
    public TitularDTO nombre(@RequestParam("bsfoperador") String bsfOperador, @RequestParam("acuerdo") String acuerdo){
    	TitularDTO datosTitular = new TitularDTO();
    	if (!bsfOperador.equals("")) {
    		ResEncryptORDecryptDTO bsfOperadorDecrypt = securityWs.decrypt(new ReqEncryptORDecryptDTO(bsfOperador));
	        try {
	        		if(bsfOperadorDecrypt.getRespuesta()!=null) {
	        			BsfOperadorPadreDTO bsfOp = (BsfOperadorPadreDTO) util.jsonToObject(new BsfOperadorPadreDTO(), bsfOperadorDecrypt.getRespuesta());
	        			ReqConsultaCuentaNombreDTO req = new ReqConsultaCuentaNombreDTO();
	    	        	req.setEntidad(bsfOp.getBSFOPERADOR().getENTIDAD());
	    	        	req.setPassword(bsfOp.getBSFOPERADOR().getPASSTCB());
	    	        	req.setTerminal(bsfOp.getBSFOPERADOR().getTERMINAL());
	    	        	req.setUsuario(bsfOp.getBSFOPERADOR().getUSERTCB());
	    	        	req.setAcuerdo(acuerdo);
	    	        	ResConsultaNombreDTO respuesta = wsServicios.consultaNombre(req);
	    	        	if(respuesta.getCabecera().getErrores()==null) {
	    	        		datosTitular.setNombre(respuesta.getNombres().get(0).getNombre());
	    	        		datosTitular.setIdInternoPe(respuesta.getNombres().get(0).getIdInternoPe());
	    	        		datosTitular.setError(null);
	    	        		return datosTitular;
	    	        	}
	    	        	else {
	    	        		datosTitular.setError(respuesta.getCabecera().getErrores().get(0).getMensaje());
	    	        		return datosTitular;
	    	        	}
	        		}
	        		else {
    	        		return datosTitular;
    	        	}
	        	
	        } catch (Exception ex) {
	            System.out.print(ex.getMessage());
	            return datosTitular;
	        }
    	}
    	else {
            return datosTitular;
        }
    }
    
    //PANTALLA PARA REALIZAR LA CONSULTA GENERAL DE MOVIMIENTOS
    @RequestMapping(value = "/consultaGeneral")
    public ModelAndView ConsultaPrincipal(@ModelAttribute("Model") final BusquedaDTO DatosGenerales, @RequestParam("tipo") String tipo, @RequestParam("relacion") String relacionDoc) {
    	ResEncryptORDecryptDTO bsfOperadorDecrypt = securityWs.decrypt(new ReqEncryptORDecryptDTO(DatosGenerales.getBsfoperador()));
    	if(bsfOperadorDecrypt.getRespuesta()!=null) {
    		try {
    			BsfOperadorPadreDTO bsfOp = (BsfOperadorPadreDTO) util.jsonToObject(new BsfOperadorPadreDTO(), bsfOperadorDecrypt.getRespuesta());
    			ReqConsultaAnotacionesDTO req = new ReqConsultaAnotacionesDTO();
    			req.setUsuario(bsfOp.getBSFOPERADOR().getUSERTCB());
    			req.setPassword(bsfOp.getBSFOPERADOR().getPASSTCB());
    			req.setEntidad(bsfOp.getBSFOPERADOR().getENTIDAD());
    			req.setTerminal(bsfOp.getBSFOPERADOR().getTERMINAL());
    			req.setSucursal(bsfOp.getBSFOPERADOR().getCENTRO());
    			req.setAcuerdo(DatosGenerales.getNumAcuerdo());
    			req.setFechaDesde(DatosGenerales.getFechaDesde()==null||DatosGenerales.getFechaDesde().isEmpty()?"00/00/0000":"");
	    		req.setFechaHasta(DatosGenerales.getFechaHasta()==null||DatosGenerales.getFechaHasta().isEmpty()?"00/00/0000":"");
    			req.setAntInformativa("1");
    			req.setAntAlerta("2");
    			req.setAntImperativa("3");
    			req.setAntAvisos("4");
    			ResConsultaAnotacionesDTO resAnotaciones = wsServicios.consultaAnotaciones(req);
    			//Se verifica si tiene anotaciones
    			if(resAnotaciones.getErrores()==null) {
    				if(resAnotaciones.getAnotaciones().size()>1) {
    					ResConsultaAnotacionesDTO res = new ResConsultaAnotacionesDTO();
    					String titular=DatosGenerales.getTitCuenta();
        	    		String txtIdPe="("+DatosGenerales.getTxtTipoIdentificacion()+":"+ DatosGenerales.getNumId()+")";
        	    		res.setAnotaciones(setConsultaPrincipal.SetConsultaAnotaciones(resAnotaciones.getAnotaciones()));
        	        	String datosString= util.objectToJson(DatosGenerales);
        	        	return new ModelAndView(packageTemplates + "/ConsultaAnotaciones").addObject("Model", res).addObject("cadenaDatos", DatosGenerales).addObject("titular", titular).addObject("txtIdPe", txtIdPe).addObject("relacion", relacionDoc);
    				}
    				else {
    		    		ConsultaPrincipalDTO detalles = new ConsultaPrincipalDTO();
    		    		
    		        	try {
    		        			if(bsfOperadorDecrypt.getRespuesta()!=null) {
    		    	        		//Se realiza la consulta de bloqueos
    		        				ReqConsultaBloqueosDTO reqBloqueos = new ReqConsultaBloqueosDTO();
    		    	        		reqBloqueos.setEntidad(bsfOp.getBSFOPERADOR().getENTIDAD());
    		    	        		reqBloqueos.setPassword(bsfOp.getBSFOPERADOR().getPASSTCB());
    		    	        		reqBloqueos.setTerminal(bsfOp.getBSFOPERADOR().getTERMINAL());
    		    	        		reqBloqueos.setUsuario(bsfOp.getBSFOPERADOR().getUSERTCB());
    		    	        		reqBloqueos.setAcuerdo(DatosGenerales.getNumAcuerdo());
    		    	        		reqBloqueos.setEstado("A");
    		            		
    		    	        		ResConsultaBloqueosDTO resBloqueos = wsServicios.consultaBloqueos(reqBloqueos);
    		            		
    		    	        		if(resBloqueos.getCabecera().getErrores()==null){
    		    	        			detalles.setBloqueos(resBloqueos.getBloqueos()==null?null:setConsultaPrincipal.SetConsultaBloqueos(resBloqueos.getBloqueos()));
    		    	        		}
    		    	        		
    		    	            	//Se realiza consulta de retenciones
    		    	        		ReqConsultaRetencionDTO reqRetenciones = new ReqConsultaRetencionDTO();
    		    	        		reqRetenciones.setEntidad(bsfOp.getBSFOPERADOR().getENTIDAD());
    		    	        		reqRetenciones.setPassword(bsfOp.getBSFOPERADOR().getPASSTCB());
    		    	        		reqRetenciones.setTerminal(bsfOp.getBSFOPERADOR().getTERMINAL());
    		    	        		reqRetenciones.setUsuario(bsfOp.getBSFOPERADOR().getUSERTCB());
    		    	        		reqRetenciones.setAcuerdo(DatosGenerales.getNumAcuerdo());
    		    	        		reqRetenciones.setEstado("ACTIVO");
    		            		
    		    	        		ResConsultaRetencionDTO resRetenciones= wsServicios.consultaRetencion(reqRetenciones);
    		    	        		
    		    	        		if(resRetenciones.getCabecera().getErrores()==null){
    		    	        			detalles.setRetenciones(resRetenciones.getRetenciones()==null?null:setConsultaPrincipal.setConsultaRetenciones(resRetenciones.getRetenciones()));
    		    	        		}
    		    	        		
    		    	        		//Se realiza consulta de movimientos
    		    	        		ReqConsultaMovimientosGeneralDTO reqApuntes = new ReqConsultaMovimientosGeneralDTO();
    		    	        		reqApuntes.setEntidad(bsfOp.getBSFOPERADOR().getENTIDAD());
    		    	        		reqApuntes.setPassword(bsfOp.getBSFOPERADOR().getPASSTCB());
    		    	        		reqApuntes.setTerminal(bsfOp.getBSFOPERADOR().getTERMINAL());
    		    	        		reqApuntes.setUsuario(bsfOp.getBSFOPERADOR().getUSERTCB());
    		    	        		reqApuntes.setNumsec("0");
    		    	        		reqApuntes.setAcuerdo(DatosGenerales.getNumAcuerdo());
    		    	        		reqApuntes.setFechadesde(DatosGenerales.getFechaDesde()==null||DatosGenerales.getFechaDesde().isEmpty()?"00/00/0000":"");
    		    	        		reqApuntes.setFechahasta(DatosGenerales.getFechaHasta()==null||DatosGenerales.getFechaHasta().isEmpty()?"00/00/0000":"");
    		    	        		reqApuntes.setAcceso("S");
    		    	        		reqApuntes.setImpsdo("0");
    		    	        		reqApuntes.setFormato(DatosGenerales.getFormato());
    		                	
    		    	        		ResConsultaApuntesDTO resApuntes = wsServicios.consultaApuntes(reqApuntes);
    		    	            	if(resApuntes.getCabecera().getErrores()==null) {
    		    	            		//SI EXISTE UN DOCUMENTO POR RELACIONAR SE REALIZA
    		    		        		if(relacionDoc!=null&&relacionDoc!=""){
    		    		        			AltaDocumentoTCBDTO reqRelacionDoc  =  new AltaDocumentoTCBDTO();
    		    		        			reqRelacionDoc=(AltaDocumentoTCBDTO) util.jsonToObject(new AltaDocumentoTCBDTO(), relacionDoc);
    		    		        			String fechaHora = bsfOp.getBSFOPERADOR().getTERMINAL().substring(bsfOp.getBSFOPERADOR().getTERMINAL().length()-7,bsfOp.getBSFOPERADOR().getTERMINAL().length());
    		    		        			fechaHora += resApuntes.getFechaOperacion()+resApuntes.getHoraOperacion();
    		    			        		ResAltaRelacionDocumento resAltaRelacion = documentosClient.relacionarDocumento(DatosGenerales.getVerificaDigitalizacion(), fechaHora);	        			
    		    		        		}

    		    	            		detalles.setApuntes(resApuntes.getLista()==null?null:setConsultaPrincipal.setConsultaApuntes(resApuntes.getLista()));
    		    	            	}
    		    	            	
    		    	            	return new ModelAndView(packageTemplates + "/PrincipalConsultas").addObject("Model", detalles).addObject("datos", DatosGenerales).addObject("lstAnotaciones",null);
    		        			}
    		        			else {
    		        				return new ModelAndView("error/500").addObject("msgError", "ERROR AL RECIBIR LOS DATOS");
    		        			}
    			    		} catch (Exception e) {
    			    			e.printStackTrace();
    			    			return new ModelAndView("error/500").addObject("msgError", "ERROR AL RECIBIR LOS DATOS");
    			    		}
    		    	}
    	    	}
    		}
    		catch(Exception ex) {
    			ex.printStackTrace();
    		}
    	}
    	return new ModelAndView("error/500").addObject("msgError", "ERROR AL RECIBIR LOS DATOS");
    }
    
    //PANTALLA PARA VERIFICAR LOS DETALLES DE ANOTACIONES
    @RequestMapping(value = "/detalleAnotacion")
    public ModelAndView ConsultaDetalleAnotaciones(@RequestParam("bsfoperador") String bsfoperador,@RequestParam("titular") String titular, 
    		@RequestParam("numAnotacion") String numAnotacion, @RequestParam("tipo") String tipo, @RequestParam("numAcuerdo") String numAcuerdo,
    		@RequestParam("codAnt") String codAnt, @RequestParam("codSubAnt") String codSubAnt, @RequestParam("desc") String descripcion,
    		@RequestParam("subcodigo") String subcodigo, @RequestParam("area") String area) {
    	
    	try {
    		DetalleConsultaDTO detalles = new DetalleConsultaDTO();
    		ResEncryptORDecryptDTO bsfOperadorDecrypt = securityWs.decrypt(new ReqEncryptORDecryptDTO(bsfoperador));
        	if(bsfOperadorDecrypt.getRespuesta()!=null) {
    			BsfOperadorPadreDTO bsfOp = (BsfOperadorPadreDTO) util.jsonToObject(new BsfOperadorPadreDTO(), bsfOperadorDecrypt.getRespuesta());
    			ReqConsultaAnotacionDetallesDTO req = new ReqConsultaAnotacionDetallesDTO();
    			req.setUsuario(bsfOp.getBSFOPERADOR().getUSERTCB());
    			req.setPassword(bsfOp.getBSFOPERADOR().getPASSTCB());
    			req.setEntidad(bsfOp.getBSFOPERADOR().getENTIDAD());
    			req.setTerminal(bsfOp.getBSFOPERADOR().getTERMINAL());
    			req.setAnotacion(numAnotacion);
    			req.setCodAnt(codAnt);
    			req.setCodSubAnt(codSubAnt);
    			
    			ResConsultaAnotacionDetalleDTO res = wsServicios.consultaDetalleAnotacion(req); 
    			detalles = setDetalles.SetAnotacionDetalles(res, codAnt);
    			detalles.setTipoDetalle(tipo);
            	detalles.setNumAcuerdo(numAcuerdo);
            	detalles.setDescripcion(descripcion);
            	detalles.setArea(area);
            	detalles.setSubcodigo(subcodigo);
    		}
        	return new ModelAndView(packageTemplates + "/Detalles").addObject("Model", detalles);
    	}
    	catch(Exception ex) {
    		ex.printStackTrace();
    		return new ModelAndView("error/500").addObject("msgError", "ERROR AL RECIBIR LOS DATOS");
    	}
    	
    	
    }
    
    //PANTALLA PRINCIPAL DE LOS MOVIMIENTOS DE LA CUENTA DESDE LA TABLA DE ANOTACIONES
    @RequestMapping(value = "/principalMovimientos")
    public ModelAndView PrincipalMovimientos(@ModelAttribute("cadenaDatos") BusquedaDTO DatosGenerales, @RequestParam("lstAnotaciones") String lstAnotaciones, @RequestParam("relacion") String relacionDoc) {
    	
    	ConsultaPrincipalDTO detalles = new ConsultaPrincipalDTO();
    	
    	try {
    		ResEncryptORDecryptDTO bsfOperadorDecrypt = securityWs.decrypt(new ReqEncryptORDecryptDTO(DatosGenerales.getBsfoperador()));

    		if(bsfOperadorDecrypt.getRespuesta()!=null) {
				BsfOperadorPadreDTO bsfOp = (BsfOperadorPadreDTO) util.jsonToObject(new BsfOperadorPadreDTO(), bsfOperadorDecrypt.getRespuesta());
				JSONArray nuevalstAnotaciones=null;
				if(lstAnotaciones.length()>1) {
					Object object=null;
					JSONParser jsonParser=new JSONParser();
					object=jsonParser.parse(lstAnotaciones);
					nuevalstAnotaciones=(JSONArray) object;
				}
				
				//Se realiza la consulta de bloqueos
	    		ReqConsultaBloqueosDTO reqBloqueos = new ReqConsultaBloqueosDTO();
	    		reqBloqueos.setEntidad(bsfOp.getBSFOPERADOR().getENTIDAD());
	    		reqBloqueos.setPassword(bsfOp.getBSFOPERADOR().getPASSTCB());
	    		reqBloqueos.setTerminal(bsfOp.getBSFOPERADOR().getTERMINAL());
	    		reqBloqueos.setUsuario(bsfOp.getBSFOPERADOR().getUSERTCB());
	    		reqBloqueos.setAcuerdo(DatosGenerales.getNumAcuerdo());
	    		reqBloqueos.setEstado("A");
	    		
	    		ResConsultaBloqueosDTO resBloqueos = wsServicios.consultaBloqueos(reqBloqueos);
	    		
	    		if(resBloqueos.getCabecera().getErrores()==null){
        			detalles.setBloqueos(resBloqueos.getBloqueos()==null?null:setConsultaPrincipal.SetConsultaBloqueos(resBloqueos.getBloqueos()));
        		}
	    		
	        	//Se realiza consulta de retenciones
	    		ReqConsultaRetencionDTO reqRetenciones = new ReqConsultaRetencionDTO();
	    		reqRetenciones.setEntidad(bsfOp.getBSFOPERADOR().getENTIDAD());
	    		reqRetenciones.setPassword(bsfOp.getBSFOPERADOR().getPASSTCB());
	    		reqRetenciones.setTerminal(bsfOp.getBSFOPERADOR().getTERMINAL());
	    		reqRetenciones.setUsuario(bsfOp.getBSFOPERADOR().getUSERTCB());
	    		reqRetenciones.setAcuerdo(DatosGenerales.getNumAcuerdo());
	    		reqRetenciones.setEstado("ACTIVO");
	    		
	    		ResConsultaRetencionDTO resRetenciones= wsServicios.consultaRetencion(reqRetenciones);
	    		
	    		if(resRetenciones.getCabecera().getErrores()==null){
        			detalles.setRetenciones(resRetenciones.getRetenciones()==null?null:setConsultaPrincipal.setConsultaRetenciones(resRetenciones.getRetenciones()));
        		}
	    		
	    		//Se realiza consulta de movimientos
	    		ReqConsultaMovimientosGeneralDTO reqApuntes = new ReqConsultaMovimientosGeneralDTO();
	    		reqApuntes.setEntidad(bsfOp.getBSFOPERADOR().getENTIDAD());
	    		reqApuntes.setPassword(bsfOp.getBSFOPERADOR().getPASSTCB());
	    		reqApuntes.setTerminal(bsfOp.getBSFOPERADOR().getTERMINAL());
	    		reqApuntes.setUsuario(bsfOp.getBSFOPERADOR().getUSERTCB());
	    		reqApuntes.setNumsec("0");
	    		reqApuntes.setAcuerdo(DatosGenerales.getNumAcuerdo());
	    		reqApuntes.setFechadesde(DatosGenerales.getFechaDesde()==null||DatosGenerales.getFechaDesde().isEmpty()?"00/00/0000":DatosGenerales.getFechaDesde());
	    		reqApuntes.setFechahasta(DatosGenerales.getFechaHasta()==null||DatosGenerales.getFechaHasta().isEmpty()?"00/00/0000":DatosGenerales.getFechaHasta());
	    		reqApuntes.setAcceso("S");
	    		reqApuntes.setImpsdo("0");
	    		reqApuntes.setFormato(DatosGenerales.getFormato());
	        	
	        	ResConsultaApuntesDTO resApuntes = wsServicios.consultaApuntes(reqApuntes);
	        	if(resApuntes.getCabecera()!=null) {
	        		if(resApuntes.getCabecera().getErrores()==null) {
	        			//SI EXISTE UN DOCUMENTO POR RELACIONAR SE REALIZA
		        		if(relacionDoc!=null&&relacionDoc!=""&&!relacionDoc.equals("null")){
		        			AltaDocumentoTCBDTO reqRelacionDoc  =  new AltaDocumentoTCBDTO();
		        			reqRelacionDoc=(AltaDocumentoTCBDTO) util.jsonToObject(new AltaDocumentoTCBDTO(), relacionDoc);
		        			String fechaHora = bsfOp.getBSFOPERADOR().getTERMINAL().substring(bsfOp.getBSFOPERADOR().getTERMINAL().length()-7,bsfOp.getBSFOPERADOR().getTERMINAL().length());
		        			fechaHora += resApuntes.getFechaOperacion()+resApuntes.getHoraOperacion();
			        		ResAltaRelacionDocumento resAltaRelacion = documentosClient.relacionarDocumento(DatosGenerales.getVerificaDigitalizacion(), fechaHora);	        			
		        		}

	            		detalles.setApuntes(resApuntes.getLista()==null?null:setConsultaPrincipal.setConsultaApuntes(resApuntes.getLista()));
	            	}
	        	}
	        	
	        	
	        	return new ModelAndView(packageTemplates + "/PrincipalConsultas").addObject("Model", detalles).addObject("datos", DatosGenerales).addObject("lstAnotaciones", nuevalstAnotaciones);
    		}
    		else {
        		return new ModelAndView("error/500").addObject("msgError", "ERROR AL RECIBIR LOS DATOS");
        	}
    		
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("error/500").addObject("msgError", "ERROR AL RECIBIR LOS DATOS");
		}
    }
    
    //CARGAR MÁS REGISTROS EN LAS TABLAS PRINCIPALES
    @RequestMapping("/cargarRegistros")
    public ConsultaPrincipalDTO CargarRegistros(@RequestParam("cadenaDatos") String cadenaDatosGenerales,
    		@RequestParam("tipo") String tipo, @RequestParam("numsec") String numsec, @RequestParam("imp") String imp){
    	ConsultaPrincipalDTO detalles = new ConsultaPrincipalDTO();
    	try {
    		BusquedaDTO DatosGenerales = (BusquedaDTO) util.jsonToObject(new BusquedaDTO(), cadenaDatosGenerales);
    		ResEncryptORDecryptDTO bsfOperadorDecrypt = securityWs.decrypt(new ReqEncryptORDecryptDTO(DatosGenerales.getBsfoperador()));
    		if(bsfOperadorDecrypt.getRespuesta()!=null) {
				BsfOperadorPadreDTO bsfOp = (BsfOperadorPadreDTO) util.jsonToObject(new BsfOperadorPadreDTO(), bsfOperadorDecrypt.getRespuesta());
				switch(tipo) {
		    		case "b":{
		    			try {
		    				return null;
						} catch (Exception e) {
							e.printStackTrace();
						}
		    		}break;
		    		case "r":{
		    			try {
							return null;
						} catch (Exception e) {
							e.printStackTrace();
						}
		    		}break;
		    		case "ap":{
		    			try {
		    				ReqConsultaMovimientosGeneralDTO reqApuntes = new ReqConsultaMovimientosGeneralDTO();
		    	    		reqApuntes.setEntidad(bsfOp.getBSFOPERADOR().getENTIDAD());
		    	    		reqApuntes.setPassword(bsfOp.getBSFOPERADOR().getPASSTCB());
		    	    		reqApuntes.setTerminal(bsfOp.getBSFOPERADOR().getTERMINAL());
		    	    		reqApuntes.setUsuario(bsfOp.getBSFOPERADOR().getUSERTCB());
		    	    		reqApuntes.setNumsec(numsec);
		    	    		reqApuntes.setAcuerdo(DatosGenerales.getNumAcuerdo());
		    	    		reqApuntes.setFechadesde(DatosGenerales.getFechaDesde()==null||DatosGenerales.getFechaDesde().isEmpty()?"00/00/0000":"");
		    	    		reqApuntes.setFechahasta(DatosGenerales.getFechaHasta()==null||DatosGenerales.getFechaHasta().isEmpty()?"00/00/0000":"");
		    	    		reqApuntes.setAcceso("N");
		    	    		reqApuntes.setImpsdo(imp);
		    	    		reqApuntes.setFormato(DatosGenerales.getFormato());
		    	        	
		    	        	ResConsultaApuntesDTO resApuntes = wsServicios.consultaApuntes(reqApuntes);
		    	        	if(resApuntes.getCabecera().getErrores()==null) {
			                	detalles.setApuntes(resApuntes.getLista()==null?null:setConsultaPrincipal.setConsultaApuntes(resApuntes.getLista()));
			                }
							
						} catch (Exception e) {
							e.printStackTrace();
						}
		    		}break;
				}
    		}
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    	}
    	
    	return detalles;
    }
    
    //IMPRESION DEL REPORTE
    @RequestMapping(value = "/imprimir")
    public String imprimirReporte(@RequestParam("lista") String lista, @RequestParam("numAcuerdo") String numAcuerdo, @RequestParam("titCuenta") String titCuenta,
    		@RequestParam("fechaDesde") String fechaDesde, @RequestParam("fechaHasta") String fechaHasta){
    	ArrayList<ApunteDTO> listdata = new ArrayList<ApunteDTO>();     
    	JsonParser parser = new JsonParser();
    	JsonElement element = parser.parse(lista);
    	JsonArray jasonArray = element.getAsJsonArray();
    	for(Object o : jasonArray) {
    		ApunteDTO nuevoObj=null;
			try {
				nuevoObj = (ApunteDTO) util.jsonToObject(new ApunteDTO(), o.toString());
				if(nuevoObj.getIdorigen()!="") {
					int i =nuevoObj.getIdorigen().lastIndexOf("-")+1;
					nuevoObj.setIdorigen(nuevoObj.getIdorigen().substring(i, nuevoObj.getIdorigen().length()));
				}
				listdata.add(nuevoObj);
			} catch (ParseException e) {
				e.printStackTrace();
			}
    	}
    	List<ReportDTO> report = new ArrayList<ReportDTO>();
    	ReportDTO dat= new ReportDTO();
    	dat.setNumacuerdo(numAcuerdo);
    	dat.setTitular(titCuenta);
    	dat.setDesde(fechaDesde);
    	dat.setHasta(fechaHasta);
    	report.add(dat);
        String XMLbase64="";
        JasperReport Reporte; // Variable donde se cargara la plantilla del reporte
        JasperPrint jp; // Variable donde se genera el reporte
        String path ="";
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("listamovimientos", listdata);
        try {
            path = Util.class.getResource("/Cedulas/ReporteMovimientos.jasper").getPath();

            path = new String(path.replaceAll("%20", " "));

            String pathImgae = Util.class.getResource("/Cedulas/Imagenes/bansefi-logo.png").getPath();
            pathImgae = new String(pathImgae.replaceAll("%20", " "));

            parameters.put("urlimagen", pathImgae);

            Reporte = (JasperReport) JRLoader.loadObjectFromFile(path);

            jp = JasperFillManager.fillReport(Reporte, parameters, new JRBeanCollectionDataSource(report, false) );

            File pdf = File.createTempFile("output.", ".pdf");
            FileOutputStream os = new FileOutputStream(pdf);
            JasperExportManager.exportReportToPdfStream(jp, os);
            Base64.Encoder e = Base64.getEncoder();
            XMLbase64 = e.encodeToString(Files.readAllBytes(pdf.toPath()));
            os.close();
            pdf.delete();


        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        }

        return XMLbase64;
    }
    
    //PANTALLA PARA VERIFICAR LOS DETALLES DE BLOQUEOS, RETENECIONES Y APUNTES 
    @RequestMapping(value = "/detalles")
    public ModelAndView ConsultaDetalleMovimientos(@RequestParam("tipo") String tipo, @RequestParam("row") String row, 
    		@RequestParam("acuerdo") String acuerdo, @RequestParam("titular") String titular, @RequestParam("BSFOPERADOR") String bsfoperador) {
    	DetalleConsultaDTO detalles = new DetalleConsultaDTO();
    	switch(tipo) {
    		case "b":{
    			try {
    				GralBloqueoDTO renglonBloqueo = (GralBloqueoDTO) util.jsonToObject(new GralBloqueoDTO(), row,new ArrayList<String>());
					detalles= setDetalles.SetConsultaDetallesBloqueo(renglonBloqueo);
					detalles.setTitular(titular);
    				detalles.setNumAcuerdo(acuerdo);
    				detalles.setMotivo(renglonBloqueo.getMotivo());
				} catch (ParseException e) {
					e.printStackTrace();
				}
    		}break;
    		case "r":{
    			try {
					GralRetencionDTO renglonRetencion = (GralRetencionDTO) util.jsonToObject(new GralRetencionDTO(), row,new ArrayList<String>());
					detalles = setDetalles.SetConsultaDetallesRetencion(renglonRetencion);
					detalles.setTitular(titular);
    				detalles.setNumAcuerdo(acuerdo);
				} catch (ParseException e) {
					e.printStackTrace();
				}
    		}break;
    		case "ap":{
    			try {
					ApunteDTO renglonApunte = (ApunteDTO) util.jsonToObject(new ApunteDTO(), row,new ArrayList<String>());
					ReqConsultaApunteDetallesDTO reqApunteDetalle = new ReqConsultaApunteDetallesDTO();
					
					ResEncryptORDecryptDTO bsfOperadorDecrypt = securityWs.decrypt(new ReqEncryptORDecryptDTO(bsfoperador));
					if(bsfOperadorDecrypt.getRespuesta()!=null) {
						BsfOperadorPadreDTO bsfOp = (BsfOperadorPadreDTO) util.jsonToObject(new BsfOperadorPadreDTO(), bsfOperadorDecrypt.getRespuesta());

						reqApunteDetalle.setUsuario(bsfOp.getBSFOPERADOR().getUSERTCB());
						reqApunteDetalle.setPassword(bsfOp.getBSFOPERADOR().getPASSTCB());
						reqApunteDetalle.setEntidad(bsfOp.getBSFOPERADOR().getENTIDAD());
						reqApunteDetalle.setTerminal(bsfOp.getBSFOPERADOR().getTERMINAL());
						reqApunteDetalle.setAcuerdo(acuerdo);
						reqApunteDetalle.setFecha(renglonApunte.getFechaoperacion());
						reqApunteDetalle.setDetalle(renglonApunte.getDetalle());
						reqApunteDetalle.setImporte(renglonApunte.getImporte());
						reqApunteDetalle.setCodcuenta(renglonApunte.getCodcuenta());
						reqApunteDetalle.setSigno(renglonApunte.getSigno());
						reqApunteDetalle.setCodorigen(renglonApunte.getCodorigen());
						reqApunteDetalle.setCodapunte(renglonApunte.getCodapunte());
						
						ResConsultaApunteDetalleDTO resAputeDetalle = wsServicios.consultaDetalleApunte(reqApunteDetalle);
						
						detalles = setDetalles.SetConsultaDetallesApunte(resAputeDetalle);
						detalles.setEstatus(renglonApunte.getSigno());
						detalles.setIdTipoCuenta(renglonApunte.getCodcuenta());
	    				detalles.setNumAcuerdo(acuerdo);
	    				detalles.setIdOrigen(renglonApunte.getIdorigen());
					}
					
				} catch (ParseException e) {
					e.printStackTrace();
				}
    		}break;
    	}
    	
    	detalles.setTipoDetalle(tipo);
    	return new ModelAndView(packageTemplates + "/Detalles").addObject("Model", detalles).addObject("bsfOperador",  bsfoperador);
    }
    
    //PANTALLA PARA VERIFICAR LOS DATOS DE AUDITORIA
    @RequestMapping(value = "/auditoria")//@RequestParam("tipo") String tipo, @RequestParam("row") String row
    public ModelAndView ConsultaAuditoria(@ModelAttribute("Model") DetalleConsultaDTO DatosConsulta, @RequestParam("bsfOperadorAuditoria") String bsfOperador) {
    	ResEncryptORDecryptDTO bsfOperadorDecrypt = securityWs.decrypt(new ReqEncryptORDecryptDTO(bsfOperador));
    	DetalleConsultaDTO detalles = new DetalleConsultaDTO();
    	
    	if(bsfOperadorDecrypt.getRespuesta()!=null) {
			try {
				BsfOperadorPadreDTO bsfOp = (BsfOperadorPadreDTO) util.jsonToObject(new BsfOperadorPadreDTO(), bsfOperadorDecrypt.getRespuesta());
				ReqConsultaAuditoriaDTO req= new ReqConsultaAuditoriaDTO();
				req.setUsuario(bsfOp.getBSFOPERADOR().getUSERTCB());
				req.setPassword(bsfOp.getBSFOPERADOR().getPASSTCB());
				req.setEntidad(bsfOp.getBSFOPERADOR().getENTIDAD());
				req.setTerminal(bsfOp.getBSFOPERADOR().getTERMINAL());
				req.setAcuerdo(DatosConsulta.getNumAcuerdo());
				req.setCodcuenta(DatosConsulta.getIdTipoCuenta());
				req.setDetalle(DatosConsulta.getNumero());
				
				ResConsultaAuditoriaDTO res= wsServicios.consultaAuditoria(req);
				
				detalles= setDetalles.SetConsultaAuditoria(res);
				detalles.setNumAcuerdo(DatosConsulta.getNumAcuerdo());
			} catch (ParseException e) {
				e.printStackTrace();
			}	
		}
    	return new ModelAndView(packageTemplates + "/Detalles").addObject("Model", detalles).addObject("bsfOperador", bsfOperador);
    }
    
    //PANTALLA PARA VERIFICAR LOS DETALLES DE AUDITORIA
    @RequestMapping(value = "/detalleAuditoria")//@RequestParam("tipo") String tipo, @RequestParam("row") String row
    public ModelAndView ConsultaDetallesAuditoria(@ModelAttribute("Model") DetalleConsultaDTO DatosConsulta, @RequestParam("bsfOperadorAuditoriaDetalles") String bsfOperador) {
    	ResEncryptORDecryptDTO bsfOperadorDecrypt = securityWs.decrypt(new ReqEncryptORDecryptDTO(bsfOperador));
    	DetalleConsultaDTO detalles = new DetalleConsultaDTO();
    	
    	if(bsfOperadorDecrypt.getRespuesta()!=null) {
			try {
				BsfOperadorPadreDTO bsfOp = (BsfOperadorPadreDTO) util.jsonToObject(new BsfOperadorPadreDTO(), bsfOperadorDecrypt.getRespuesta());
				ReqConsultaAuditoriaDetallesDTO req= new ReqConsultaAuditoriaDetallesDTO();
				req.setUsuario(bsfOp.getBSFOPERADOR().getUSERTCB());
				req.setPassword(bsfOp.getBSFOPERADOR().getPASSTCB());
				req.setEntidad(bsfOp.getBSFOPERADOR().getENTIDAD());
				req.setTerminal(bsfOp.getBSFOPERADOR().getTERMINAL());
				req.setAcuerdo(DatosConsulta.getNumAcuerdo());
				req.setEmpleado(DatosConsulta.getCodEmpleado());
				req.setAutorizador(DatosConsulta.getAutorizador());
				
				ResConsultaAuditoriaDetalleDTO res= wsServicios.consultaAuditoriaDetalles(req);
				
				detalles= setDetalles.SetConsultaAuditoriaDetalles(res);
				detalles.setNumAcuerdo(DatosConsulta.getNumAcuerdo());
				detalles.setEntidad(bsfOp.getBSFOPERADOR().getENTIDAD());
				detalles.setCodCentroActual(bsfOp.getBSFOPERADOR().getCENTRO());
				detalles.setCodEmpleado(DatosConsulta.getCodEmpleado());
				detalles.setCodEmpleadoAutorizador(DatosConsulta.getAutorizador());
				detalles.setTerminal(DatosConsulta.getTerminal());
				detalles.setFechaOperacion(DatosConsulta.getFechaOperacion());
				detalles.setHoraOperacion(DatosConsulta.getHoraOperacion());
				detalles.setCodTransaccion(DatosConsulta.getCodAuditoria());
			} catch (ParseException e) {
				e.printStackTrace();
			}	
		}
    	return new ModelAndView(packageTemplates + "/Detalles").addObject("Model", detalles);
    }
    
    //PANTALLA PARA VERIFICAR MÁS DATOS DEL APUNTE 
    @RequestMapping(value = "/masApunte")
    public ModelAndView ConsultaMasOrigen(@ModelAttribute("Model") DetalleConsultaDTO DatosConsulta) {
    	if(DatosConsulta.getConceptoCorto().contains("LIQ")) {
    		DatosConsulta.setOriginadoPor("LIQUIDACION");
    	}
    	if(DatosConsulta.getConceptoCorto().contains("CHQ")) {
    		DatosConsulta.setOriginadoPor("EMISION");
    	}
    	DatosConsulta.setTitulo(" ampliada de apunte");
    	DatosConsulta.setTipoDetalle("mAp");
    	return new ModelAndView(packageTemplates + "/Detalles").addObject("Model", DatosConsulta);
    }
    
    //PANTALLA PARA VERIFICAR EL ORIGEN 
    @RequestMapping(value = "/origen")
    public ModelAndView ConsultaOrigen(@ModelAttribute("Model") DetalleConsultaDTO DatosConsulta, @RequestParam("bsfOperadorOrigen") String bsfOperador) {
    	ResEncryptORDecryptDTO bsfOperadorDecrypt = securityWs.decrypt(new ReqEncryptORDecryptDTO(bsfOperador));
    	DetalleConsultaDTO detalles = new DetalleConsultaDTO();
    	if(bsfOperadorDecrypt.getRespuesta()!=null) {
    		try {
    			BsfOperadorPadreDTO bsfOp = (BsfOperadorPadreDTO) util.jsonToObject(new BsfOperadorPadreDTO(), bsfOperadorDecrypt.getRespuesta());
    			if(DatosConsulta.getConcepto().contains("LIQ")) {
            		ReqConsultaLiquidacionDTO req = new ReqConsultaLiquidacionDTO();
            		req.setUsuario(bsfOp.getBSFOPERADOR().getUSERTCB());
            		req.setPassword(bsfOp.getBSFOPERADOR().getPASSTCB());
            		req.setTerminal(bsfOp.getBSFOPERADOR().getTERMINAL());
            		req.setEntidad(bsfOp.getBSFOPERADOR().getENTIDAD());
            		req.setAcuerdo(DatosConsulta.getNumAcuerdo());
            		String idOrigen = DatosConsulta.getIdOrigen();
            		idOrigen= idOrigen.substring(idOrigen.indexOf("-")+1, idOrigen.length());
            		String fechaLiquidacion=idOrigen.substring(idOrigen.indexOf("-")+1,idOrigen.lastIndexOf("-"));
            		String detalle = idOrigen.substring(idOrigen.indexOf(fechaLiquidacion)+12, idOrigen.length());
            		req.setDetalle(detalle);
            		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            		DateFormat newFormatter = new SimpleDateFormat("yyyy-MM-dd");
            		try {
						Date date = (Date)formatter.parse(fechaLiquidacion);
						fechaLiquidacion=newFormatter.format(date).toString().replaceAll("-", "");
						req.setFechaLiquidacion(fechaLiquidacion);
					} catch (java.text.ParseException e) {
						e.printStackTrace();
					}
            		
            		req.setLiqOpcion("1");
            		
            		ResConsultaLiquidacionDTO res = wsServicios.consultaLiquidacion(req);
            		
            		detalles=setDetalles.SetConsultaLiquidacion(res);
            		
            	}
            	if(DatosConsulta.getConcepto().contains("EMI")) {
            		ReqConsultaEmisionDTO req = new ReqConsultaEmisionDTO();
            		req.setUsuario(bsfOp.getBSFOPERADOR().getUSERTCB());
            		req.setPassword(bsfOp.getBSFOPERADOR().getPASSTCB());
            		req.setTerminal(bsfOp.getBSFOPERADOR().getTERMINAL());
            		req.setEntidad(bsfOp.getBSFOPERADOR().getENTIDAD());
            		String acuerdo= DatosConsulta.getIdOrigen();
            		acuerdo=acuerdo.substring(acuerdo.lastIndexOf("    ")+1, acuerdo.length());
            		req.setAcuerdo(acuerdo.trim());
            		String concepto = DatosConsulta.getConcepto();
            		String codCaja = concepto.substring(concepto.indexOf(":")+1, concepto.indexOf("-"));
            		req.setCodigoCaja(codCaja);
            		String numChq = concepto.substring(concepto.indexOf("-")+1, concepto.length());
            		req.setNumCheque(numChq);
            		req.setCodSeguridad("0");
            		
            		ResConsultaEmisionDTO res = wsServicios.consultaEmision(req);
            		
            		detalles=setDetalles.SetConsultaEmision(res);
            		detalles.setReqNumeroCheque(""+Integer.parseInt(numChq.trim()));
            		
            	}
    		}
    		catch (ParseException e) {
				e.printStackTrace();
    		}
        	
    	}
    	
    	return new ModelAndView(packageTemplates + "/Detalles").addObject("Model", detalles).addObject("datos", DatosConsulta);
    }
    
    //MÉTODO PARA GENERAR EL BSFOPERADOR QUE SE ENVIARÁ AL MÓDULO DE BUSQUEDA DE ACUERDO
    @RequestMapping(value = "/busquedaPersonaEncriptar", method = RequestMethod.POST)
    public ResEncryptORDecryptDTO encripcion(@ModelAttribute("obj") DatosEncryptDigitaliza datos) {
    	String url = urlLocalHost + context + "/";
    	
    	ResEncryptORDecryptDTO bsfOperadorDecrypt = securityWs.decrypt(new ReqEncryptORDecryptDTO(datos.getBSFOPERADOR()));
		if(bsfOperadorDecrypt.getRespuesta()!=null) {
			BsfOperadorPadreDTO bsfOp;
			try {
				bsfOp = (BsfOperadorPadreDTO) util.jsonToObject(new BsfOperadorPadreDTO(), bsfOperadorDecrypt.getRespuesta());
				String aencriptar = "{\"BSFOPERADOR\": {\"ENTIDAD\": \"" + bsfOp.getBSFOPERADOR().getENTIDAD() + "\", \"CENTRO\": \"" 
						+ bsfOp.getBSFOPERADOR().getCENTRO() + "\", \"TERMINAL\": \""+ bsfOp.getBSFOPERADOR().getTERMINAL() 
						+ "\", \"USERTCB\": \"" + bsfOp.getBSFOPERADOR().getUSERTCB() + "\", \"SESSIONID\": \"\", \"PASSTCB\": \""
						+ bsfOp.getBSFOPERADOR().getPASSTCB() + "\", \"NOMBREEMPLEADO\": \""+ bsfOp.getBSFOPERADOR().getNOMBREEMPLEADO() 
	                    + "\", \"NOMBRECENTRO\": \"" + bsfOp.getBSFOPERADOR().getNOMBRECENTRO() + "\", \"TRANSPORT\": {\"URLACTION\": \""+url
	                    + "\", \"TITULO\": \"Busca Persona\", \"MenuDinamico\":\"1\",\"TARGET\": \"_top\", \"IDINTERNOPE\":\"\", "+
	                    " \"DATOSANTERIORES\": \"\"}}}";
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