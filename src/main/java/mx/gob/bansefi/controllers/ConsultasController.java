package mx.gob.bansefi.controllers;

import mx.gob.bansefi.clients.DocumentosClient;
import mx.gob.bansefi.dto.AltaDocumentoTCBDTO;
import mx.gob.bansefi.dto.AltaRelacionDocumentoDTO;
import mx.gob.bansefi.dto.AnotacionesDTO;
import mx.gob.bansefi.dto.BusquedaDTO;
import mx.gob.bansefi.dto.ConsultaPrincipalDTO;
import mx.gob.bansefi.dto.DetalleConsultaDTO;
import mx.gob.bansefi.dto.GralApunteDTO;
import mx.gob.bansefi.dto.GralBloqueoDTO;
import mx.gob.bansefi.dto.GralRetencionDTO;
import mx.gob.bansefi.dto.TitularDTO;
import mx.gob.bansefi.dto.Request.*;
import mx.gob.bansefi.dto.Request.Documentos.ReqAltaRelacionDocumento;
import mx.gob.bansefi.dto.Request.Documentos.ReqEncryptORDecryptDTO;
import mx.gob.bansefi.dto.Response.*;
import mx.gob.bansefi.dto.Response.Documentos.ResAltaRelacionDocumento;
import mx.gob.bansefi.dto.bsfOperador.BsfOperadorPadreDTO;
import mx.gob.bansefi.process.SetConsultaDetallesProccess;
import mx.gob.bansefi.process.SetConsultaPrincipalProccess;
import mx.gob.bansefi.services.SecurityWS;
import mx.gob.bansefi.services.WsServicios;
import mx.gob.bansefi.utils.Util;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.SortDefault;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.*;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.ArrayList;
import java.util.List;

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
            //BsfOperadorDTO bsfOperadorDecrypt = securityWs.decriptBsfOperador(new ReqEncryptORDecryptDTO(bsfOperador));
            try {
            	System.out.print("Setear algun valor");
            } catch (Exception ex) {
                System.out.print(ex.getMessage());
            }
            return new ModelAndView(packageTemplates + "/Buscador").addObject("Model", busquedaDatos).addObject("urlActionBack", urlDigitalizacion);
        } else {
            return new ModelAndView("error/500").addObject("msgError", "ERROR AL RECIBIR LOS DATOS");

        }
    }
    
    
    //PANTALLA PRINCIPAL DE BUSQUEDA DESPUÉS DE LA DIGITALIZACIÓN
    @RequestMapping(value = "/busquedaDig&{idDoc}")
    public ModelAndView BusquedaDespuesDeDigitalizar(@RequestParam("TRANSPORT") String TRANSPORT) {
    	BusquedaDTO busquedaDatos = new BusquedaDTO();
    	ResEncryptORDecryptDTO transportDecrypt = securityWs.decrypt(new ReqEncryptORDecryptDTO(TRANSPORT));
    	transportDecrypt.setRespuesta(transportDecrypt.getRespuesta().replace("'", "\""));
    	transportDecrypt.setRespuesta(transportDecrypt.getRespuesta().replace("\"{", "{"));
    	transportDecrypt.setRespuesta(transportDecrypt.getRespuesta().replace("}\"", "}"));
    	try {
			BsfOperadorPadreDTO bsfOp = (BsfOperadorPadreDTO) util.jsonToObject(new BsfOperadorPadreDTO(), transportDecrypt.getRespuesta());
			
			AltaDocumentoTCBDTO datosRelacion = new AltaDocumentoTCBDTO();
			datosRelacion.setCentro(bsfOp.getBSFOPERADOR().getCENTRO());
			datosRelacion.setCodTipoDoc(""+bsfOp.getBSFOPERADOR().getTRANSPORT().getIDINTERNOPE().getCODDOC());
			datosRelacion.setDescDoc(bsfOp.getBSFOPERADOR().getTRANSPORT().getIDINTERNOPE().getDESCDOC());
			datosRelacion.setEntidad(bsfOp.getBSFOPERADOR().getENTIDAD());
			datosRelacion.setFechaCaducidadDoc("9999/99/12");
			datosRelacion.setTerminal(bsfOp.getBSFOPERADOR().getTERMINAL());
			datosRelacion.setIdInternoPe(bsfOp.getBSFOPERADOR().getTRANSPORT().getIDINTERNOPE().getIDINTERNOPE());
			
			ResAltaRelacionDocumento resAltaRelacion = documentosClient.relacionarDocumento(new ReqAltaDocumentoTCBDTO(datosRelacion), bsfOp.getBSFOPERADOR().getTRANSPORT().getIDDOCUMENTO());
			
			if(resAltaRelacion.getDescripcion()==null) {
				busquedaDatos.setBsfoperador(bsfOp.getBSFOPERADOR().getTRANSPORT().getIDINTERNOPE().getBSFOPERADORINICIO());
				busquedaDatos.setFormato("OFICINA");
	            busquedaDatos.setVerificaDigitalizacion("2");//Variable para especificar si ya se ha digitalizado el documento.
	            busquedaDatos.setIdInternoPe(datosRelacion.getIdInternoPe());
	            busquedaDatos.setTitCuenta(bsfOp.getBSFOPERADOR().getTRANSPORT().getIDINTERNOPE().getTITULAR());
	            busquedaDatos.setNumAcuerdo(bsfOp.getBSFOPERADOR().getTRANSPORT().getIDINTERNOPE().getACUERDO());
	            }
			else {
				busquedaDatos.setBsfoperador(bsfOp.getBSFOPERADOR().getTRANSPORT().getIDINTERNOPE().getBSFOPERADORINICIO());
				busquedaDatos.setFormato("OFICINA");
	            busquedaDatos.setVerificaDigitalizacion("1");//Variable para especificar si ya se ha digitalizado el documento.
	            busquedaDatos.setIdInternoPe(datosRelacion.getIdInternoPe());
	            busquedaDatos.setTitCuenta(bsfOp.getBSFOPERADOR().getTRANSPORT().getIDINTERNOPE().getTITULAR());
	            busquedaDatos.setNumAcuerdo(bsfOp.getBSFOPERADOR().getTRANSPORT().getIDINTERNOPE().getACUERDO());
			}
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	
    	return new ModelAndView(packageTemplates + "/Buscador").addObject("Model", busquedaDatos).addObject("urlActionBack", urlDigitalizacion);
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
    public ModelAndView ConsultaPrincipal(@ModelAttribute("Model") final BusquedaDTO DatosGenerales, @RequestParam("tipo") String tipo) {
    	ResEncryptORDecryptDTO bsfOperadorDecrypt = securityWs.decrypt(new ReqEncryptORDecryptDTO(DatosGenerales.getBsfoperador()));
    	if(bsfOperadorDecrypt.getRespuesta()!=null) {
    		try {
    			BsfOperadorPadreDTO bsfOp = (BsfOperadorPadreDTO) util.jsonToObject(new BsfOperadorPadreDTO(), bsfOperadorDecrypt.getRespuesta());
    			ReqConsultaAnotacionesDTO req = new ReqConsultaAnotacionesDTO();
    			req.setUsuario(bsfOp.getBSFOPERADOR().getUSERTCB());
    			req.setPassword(bsfOp.getBSFOPERADOR().getPASSTCB());
    			req.setEntidad(bsfOp.getBSFOPERADOR().getENTIDAD());
    			req.setTerminal(bsfOp.getBSFOPERADOR().getTERMINAL());
    			req.setAcuerdo(DatosGenerales.getNumAcuerdo());
    			req.setFechaDesde("00/00/0000");
    			req.setFechaHasta("00/00/0000");
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
        	        	return new ModelAndView(packageTemplates + "/ConsultaAnotaciones").addObject("Model", res).addObject("cadenaDatos", DatosGenerales).addObject("titular", titular).addObject("txtIdPe", txtIdPe);
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
    		    	            		detalles.setApuntes(resApuntes.getLista()==null?null:setConsultaPrincipal.setConsultaApuntes(resApuntes.getLista()));
    		    	            	}
    		    	            	
    		    	            	return new ModelAndView(packageTemplates + "/PrincipalConsultas").addObject("Model", detalles).addObject("datos", DatosGenerales).addObject("anotaciones","n");
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
    		@RequestParam("codAnt") String codAnt, @RequestParam("codSubAnt") String codSubAnt, @RequestParam("desc") String descripcion) {
    	
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
    		}
        	return new ModelAndView(packageTemplates + "/Detalles").addObject("Model", detalles);
    	}
    	catch(Exception ex) {
    		ex.printStackTrace();
    		return new ModelAndView("error/500").addObject("msgError", "ERROR AL RECIBIR LOS DATOS");
    	}
    	
    	
    }
    
    //PANTALLA PRINCIPAL DE LOS MOVIMIENTOS DE LA CUENTA
    @RequestMapping(value = "/principalMovimientos")
    public ModelAndView PrincipalMovimientos(@ModelAttribute("cadenaDatos") BusquedaDTO DatosGenerales) {
    	
    	ConsultaPrincipalDTO detalles = new ConsultaPrincipalDTO();
    	
    	try {
    		//BusquedaDTO datosGenerales= new BusquedaDTO();
			
    		//datosGenerales=(BusquedaDTO) util.jsonToObject(new BusquedaDTO(), strObj,new ArrayList<String>());
			
    		ResEncryptORDecryptDTO bsfOperadorDecrypt = securityWs.decrypt(new ReqEncryptORDecryptDTO(DatosGenerales.getBsfoperador()));

    		if(bsfOperadorDecrypt.getRespuesta()!=null) {
				BsfOperadorPadreDTO bsfOp = (BsfOperadorPadreDTO) util.jsonToObject(new BsfOperadorPadreDTO(), bsfOperadorDecrypt.getRespuesta());
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
            		detalles.setApuntes(resApuntes.getLista()==null?null:setConsultaPrincipal.setConsultaApuntes(resApuntes.getLista()));
            	}
	        	
	        	return new ModelAndView(packageTemplates + "/PrincipalConsultas").addObject("Model", detalles).addObject("datos", DatosGenerales);
    		}
    		else {
        		return new ModelAndView("error/500").addObject("msgError", "ERROR AL RECIBIR LOS DATOS");
        	}
    		
		} catch (ParseException e) {
			e.printStackTrace();
			return new ModelAndView("error/500").addObject("msgError", "ERROR AL RECIBIR LOS DATOS");
		}
    }
    
    //PANTALLA PARA VERIFICAR LOS DETALLES DE BLOQUEOS, RETENECIONES Y APUNTES 
    @RequestMapping(value = "/detalles")//
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
					GralApunteDTO renglonApunte = (GralApunteDTO) util.jsonToObject(new GralApunteDTO(), row,new ArrayList<String>());
					ReqConsultaApunteDetallesDTO reqApunteDetalle = new ReqConsultaApunteDetallesDTO();
					
					ResEncryptORDecryptDTO bsfOperadorDecrypt = securityWs.decrypt(new ReqEncryptORDecryptDTO(bsfoperador));
					if(bsfOperadorDecrypt.getRespuesta()!=null) {
						BsfOperadorPadreDTO bsfOp = (BsfOperadorPadreDTO) util.jsonToObject(new BsfOperadorPadreDTO(), bsfOperadorDecrypt.getRespuesta());

						reqApunteDetalle.setUsuario(bsfOp.getBSFOPERADOR().getUSERTCB());
						reqApunteDetalle.setPassword(bsfOp.getBSFOPERADOR().getPASSTCB());
						reqApunteDetalle.setEntidad(bsfOp.getBSFOPERADOR().getENTIDAD());
						reqApunteDetalle.setTerminal(bsfOp.getBSFOPERADOR().getTERMINAL());
						reqApunteDetalle.setAcuerdo(acuerdo);
						reqApunteDetalle.setFecha(renglonApunte.getFechaOperacion());
						reqApunteDetalle.setDetalle(renglonApunte.getDetalle());
						reqApunteDetalle.setImporte(renglonApunte.getImporte());
						reqApunteDetalle.setCodcuenta(renglonApunte.getCodcuenta());
						reqApunteDetalle.setSigno(renglonApunte.getSigno());
						reqApunteDetalle.setCodorigen(renglonApunte.getCodorigen());
						reqApunteDetalle.setCodapunte(renglonApunte.getCodapunte());
						
						ResConsultaApunteDetalleDTO resAputeDetalle = wsServicios.consultaDetalleApunte(reqApunteDetalle);
						
						//detalles = setDetalles.SetConsultaDetallesApunte(renglonApunte);
						detalles.setTitular(titular);
	    				detalles.setNumAcuerdo(acuerdo);
					}
					
				} catch (ParseException e) {
					e.printStackTrace();
				}
    		}break;
    	}
    	
    	detalles.setTipoDetalle(tipo);
    	return new ModelAndView(packageTemplates + "/Detalles").addObject("Model", detalles);
    }
    
    //PANTALLA PARA VERIFICAR LOS DATOS DE AUDITORIA
    @RequestMapping(value = "/auditoria")//@RequestParam("tipo") String tipo, @RequestParam("row") String row
    public ModelAndView ConsultaAuditoria() {
    	DetalleConsultaDTO detalles = new DetalleConsultaDTO();
    	detalles.setTitulo("Básica de auditoría");
    	detalles.setTipoDetalle("au");
    	return new ModelAndView(packageTemplates + "/Detalles").addObject("Model", detalles);
    }
    
  //PANTALLA PARA VERIFICAR LOS DETALLES DE AUDITORIA
    @RequestMapping(value = "/detalleAuditoria")//@RequestParam("tipo") String tipo, @RequestParam("row") String row
    public ModelAndView ConsultaDetallesAuditoria() {
    	DetalleConsultaDTO detalles = new DetalleConsultaDTO();
    	detalles.setTitulo("de Auditoría");
    	detalles.setTipoDetalle("dAu");
    	return new ModelAndView(packageTemplates + "/Detalles").addObject("Model", detalles);
    }
    
  //PANTALLA PARA VERIFICAR EL ORIGEN 
    @RequestMapping(value = "/origen")//@RequestParam("tipo") String tipo, @RequestParam("row") String row
    public ModelAndView ConsultaOrigen() {
    	DetalleConsultaDTO detalles = new DetalleConsultaDTO();
    	detalles.setTipoDetalle("em");
    	if(detalles.getTipoDetalle().equals("li")) {
    		detalles.setTitulo("de liquidación");
    	}
    	else {
    		detalles.setTitulo("Emisión de cheque");
    	}
    	return new ModelAndView(packageTemplates + "/Detalles").addObject("Model", detalles);
    }

}