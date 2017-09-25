package mx.gob.bansefi.controllers;

import mx.gob.bansefi.clients.DocumentosClient;
import mx.gob.bansefi.dto.Modelos.*;
import mx.gob.bansefi.dto.BsfOperadorDTO;
import mx.gob.bansefi.dto.BusquedaDTO;
import mx.gob.bansefi.dto.ConsultaPrincipalDTO;
import mx.gob.bansefi.dto.DetalleConsultaDTO;
import mx.gob.bansefi.dto.GralApunteDTO;
import mx.gob.bansefi.dto.ReqEncryptDTO;
import mx.gob.bansefi.dto.ReqEncryptORDecryptDTO;
import mx.gob.bansefi.dto.Request.*;
import mx.gob.bansefi.dto.Request.ReqConsultaDTO;
import mx.gob.bansefi.dto.ResEncryptORDecryptDTO;
import mx.gob.bansefi.dto.Response.*;
import mx.gob.bansefi.process.SetConsultaDetallesProccess;
import mx.gob.bansefi.process.SetConsultaPrincipalProccess;
import mx.gob.bansefi.services.SecurityWS;
import mx.gob.bansefi.services.WsServicios;
import mx.gob.bansefi.utils.Util;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

import org.json.JSONObject;
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
            //BsfOperadorDTO bsfOperadorDecrypt = securityWs.decriptBsfOperador(new ReqEncryptORDecryptDTO(bsfOperador));
            try {
            	System.out.print("Setear algun valor");
            } catch (Exception ex) {
                System.out.print(ex.getMessage());
            }
            return new ModelAndView(packageTemplates + "/Buscador").addObject("Model", busquedaDatos);
        } else {
            return new ModelAndView("error/500").addObject("msgError", "ERROR AL RECIBIR LOS DATOS");

        }
    }
    
  //PANTALLA PRINCIPAL DE BUSQUEDA
    @RequestMapping(value = "/busquedaApuntes")
    public ModelAndView BusquedaApunte() {
    	
    	return new ModelAndView(packageTemplates + "/Buscador");
    }
    
    //BUSQUEDA DEL NOMBRE
    @RequestMapping("/getNombre")
    public String nombre(@RequestParam("bsfoperador") String bsfOperador, @RequestParam("acuerdo") String acuerdo){
    	if (!bsfOperador.equals("")) {
    		BsfOperadorDTO bsfOperadorDecrypt = securityWs.decriptBsfOperador(new ReqEncryptORDecryptDTO(bsfOperador));
	        try {
	        	GetConsultaCuentaNombreReqDTO req = new GetConsultaCuentaNombreReqDTO();
	        	req.setEntidad(bsfOperadorDecrypt.getBSFOPERADOR().getENTIDAD());
	        	req.setPassword(bsfOperadorDecrypt.getBSFOPERADOR().getPASSTCB());
	        	req.setTerminal(bsfOperadorDecrypt.getBSFOPERADOR().getTERMINAL());
	        	req.setUsuario(bsfOperadorDecrypt.getBSFOPERADOR().getUSERTCB());
	        	req.setAcuerdo(acuerdo);
	        	ResConsultaNombreDTO respuesta = wsServicios.consultaNombre(req);
	        	if(respuesta.getCabecera().getErrores()==null) {
	        		return respuesta.getNombres().get(0).getNombre();
	        	}
	        	else {
	        		return "ERROR";
	        	}
	        } catch (Exception ex) {
	            System.out.print(ex.getMessage());
	            return "ERROR";
	        }
    	}
    	else {
            return "ERROR AL RECIBIR LOS DATOS";
        }
    }
    
    //PATALLA PRINCIPAL DE ANOTACIONES
    @RequestMapping(value = "/anotaciones")
    public ModelAndView Consulta() {
    	ArrayList<AnotacionesDTO> anotaciones = new ArrayList<AnotacionesDTO>();
    	AnotacionesDTO datosAnotacion= new AnotacionesDTO();
    	datosAnotacion.setDescr("bla");
    	datosAnotacion.setFechactivacion("10/12/2012");
    	datosAnotacion.setNoanotacion("1");
    	datosAnotacion.setPrioridad("ALTA");
    	datosAnotacion.setTipoanotacion("NOTA");
    	anotaciones.add(datosAnotacion);
    	datosAnotacion= new AnotacionesDTO();
    	datosAnotacion.setDescr("kud");
    	datosAnotacion.setFechactivacion("21/04/2015");
    	datosAnotacion.setNoanotacion("5");
    	datosAnotacion.setPrioridad("ALTA");
    	datosAnotacion.setTipoanotacion("NOTA");
    	anotaciones.add(datosAnotacion);
    	datosAnotacion= new AnotacionesDTO();
    	datosAnotacion.setDescr("bla");
    	datosAnotacion.setFechactivacion("10/12/2012");
    	datosAnotacion.setNoanotacion("1");
    	datosAnotacion.setPrioridad("ALTA");
    	datosAnotacion.setTipoanotacion("NOTA");
    	anotaciones.add(datosAnotacion);
    	
    	return new ModelAndView(packageTemplates + "/ConsultaAnotaciones").addObject("Model", anotaciones);
    }
    //PANTALLA PARA VERIFICAR LOS DETALLES DE ANOTACIONES
    @RequestMapping(value = "/detalleAnotacion")
    public ModelAndView ConsultaDetalleAnotaciones(@ModelAttribute("Model") final BusquedaDTO DatosGenerales, @RequestParam("tipo") String tipo) {
    	DetalleConsultaDTO detalles = new DetalleConsultaDTO();
    	detalles.setTitulo("Detalle de Anotaci�n");
    	detalles.setTipo(tipo);
    	return new ModelAndView(packageTemplates + "/Detalles").addObject("Model", detalles);
    }
    
    //PANTALLA PARA REALIZAR LA CONSULTA GENERAL DE MOVIMIENTOS
    @RequestMapping(value = "/consultaGeneral")
    public ModelAndView ConsultaPrincipal(@ModelAttribute("Model") final BusquedaDTO DatosGenerales, @RequestParam("tipo") String tipo) {
    	boolean conAnotaciones=true;
    	//Si tiene anotaciones
    	if(conAnotaciones) {
    		ResConsultaAnotacionesDTO res = new ResConsultaAnotacionesDTO();
    		res.setTitular(DatosGenerales.getTitCuenta());
    		res.setTxtIdPe("("+DatosGenerales.getTxtTipoIdentificacion()+":"+ DatosGenerales.getNumId()+")");
    		res.setAnotaciones(setConsultaPrincipal.SetConsultaAnotaciones());
    		
        	
        	String datosString= util.objectToJson(DatosGenerales);
        	
        	return new ModelAndView(packageTemplates + "/ConsultaAnotaciones").addObject("Model", res).addObject("cadenaDatos", datosString);
    	}
    	//No tiene anotaciones
    	else {
    		BsfOperadorDTO bsfOperadorDecrypt = securityWs.decriptBsfOperador(new ReqEncryptORDecryptDTO(DatosGenerales.getBsfoperador()));
	        try {
	        	GetConsultaMovimientosGeneralReqDTO req = new GetConsultaMovimientosGeneralReqDTO();
	        	req.setEntidad(bsfOperadorDecrypt.getBSFOPERADOR().getENTIDAD());
	        	req.setPassword(bsfOperadorDecrypt.getBSFOPERADOR().getPASSTCB());
	        	req.setTerminal(bsfOperadorDecrypt.getBSFOPERADOR().getTERMINAL());
	        	req.setUsuario(bsfOperadorDecrypt.getBSFOPERADOR().getUSERTCB());
	        	req.setNumsec("0");
	        	req.setAcuerdo(DatosGenerales.getNumAcuerdo());
	        	req.setFechadesde(DatosGenerales.getFechaDesde());
	        	req.setFechahasta(DatosGenerales.getFechaHasta());
	        	req.setAcceso(DatosGenerales.getAcceso());
	        	req.setImpsdo(DatosGenerales.getImpsdo());
	        	req.setFormato(DatosGenerales.getFormato());
	        	
	        	ResConsultaApuntesDTO respuesta = wsServicios.consultaApuntes(req);
	        	if(respuesta.getCabecera().getStatus().equals("0")) {
	        		ConsultaPrincipalDTO detalles = new ConsultaPrincipalDTO();
	            	//detalles = setConsultaPrincipal.SetConsultaPrincipal();
	            	return new ModelAndView(packageTemplates + "/PrincipalConsultas").addObject("Model", detalles);	        		
	        	}
	        	else {
	        		return new ModelAndView("error/500").addObject("msgError", "ERROR AL RECIBIR LOS DATOS");
	        	}
	        } catch (Exception ex) {
	            System.out.print(ex.getMessage());
	            return new ModelAndView("error/500").addObject("msgError", "ERROR AL RECIBIR LOS DATOS");
	        }
    		
    	}
    }
    
    //PANTALLA PRINCIPAL DE LOS MOVIMIENTOS DE LA CUENTA
    @RequestMapping(value = "/principalMovimientos")
    public ModelAndView PrincipalMovimientos(@RequestParam("bsfoperador") String tibsfoperador, @RequestParam("datos") String strObj) {
    	
    	ConsultaPrincipalDTO detalles = new ConsultaPrincipalDTO();
    	
    	try {
    		BusquedaDTO datosGenerales= new BusquedaDTO();
			
    		datosGenerales=(BusquedaDTO) util.jsonToObject(new BusquedaDTO(), strObj,new ArrayList<String>());
			
    		BsfOperadorDTO bsfOperadorDecrypt = securityWs.decriptBsfOperador(new ReqEncryptORDecryptDTO(datosGenerales.getBsfoperador()));
			
    		//Se realiza la consulta de bloqueos
    		GetConsultaBloqueosReqDTO reqBloqueos = new GetConsultaBloqueosReqDTO();
    		reqBloqueos.setEntidad(bsfOperadorDecrypt.getBSFOPERADOR().getENTIDAD());
    		reqBloqueos.setPassword(bsfOperadorDecrypt.getBSFOPERADOR().getPASSTCB());
    		reqBloqueos.setTerminal(bsfOperadorDecrypt.getBSFOPERADOR().getTERMINAL());
    		reqBloqueos.setUsuario(bsfOperadorDecrypt.getBSFOPERADOR().getUSERTCB());
    		reqBloqueos.setAcuerdo(datosGenerales.getNumAcuerdo());
    		reqBloqueos.setEstado("A");
    		
    		ResConsultaBloqueosDTO resBloqueos = wsServicios.consultaBloqueos(reqBloqueos);
    		
    		if(resBloqueos.getCabecera().getErrores()==null){
    			detalles.setBloqueos(setConsultaPrincipal.SetConsultaBloqueos(resBloqueos.getBloqueos()));
    		}
        	
    		//Se realiza consulta de movimientos
    		GetConsultaMovimientosGeneralReqDTO reqApuntes = new GetConsultaMovimientosGeneralReqDTO();
    		reqApuntes.setEntidad(bsfOperadorDecrypt.getBSFOPERADOR().getENTIDAD());
    		reqApuntes.setPassword(bsfOperadorDecrypt.getBSFOPERADOR().getPASSTCB());
    		reqApuntes.setTerminal(bsfOperadorDecrypt.getBSFOPERADOR().getTERMINAL());
    		reqApuntes.setUsuario(bsfOperadorDecrypt.getBSFOPERADOR().getUSERTCB());
    		reqApuntes.setNumsec("0");
    		reqApuntes.setAcuerdo(datosGenerales.getNumAcuerdo());
    		reqApuntes.setFechadesde(datosGenerales.getFechaDesde()==null||datosGenerales.getFechaDesde().isEmpty()?"00/00/0000":"");
    		reqApuntes.setFechahasta(datosGenerales.getFechaHasta()==null||datosGenerales.getFechaHasta().isEmpty()?"00/00/0000":"");
    		reqApuntes.setAcceso("S");
    		reqApuntes.setImpsdo("0");
    		reqApuntes.setFormato(datosGenerales.getFormato());
        	
        	//detalles = setConsultaPrincipal.SetConsultaPrincipal();
        	//return new ModelAndView(packageTemplates + "/PrincipalConsultas").addObject("Model", detalles);
        	
        	ResConsultaApuntesDTO resApuntes = wsServicios.consultaApuntes(reqApuntes);
        	if(resApuntes.getCabecera().getErrores()==null) {
            	detalles.setApuntes(resApuntes.getApuntes());
        	}
        	return new ModelAndView(packageTemplates + "/PrincipalConsultas").addObject("Model", detalles);
        	/*
        	else {
        		return new ModelAndView("error/500").addObject("msgError", "ERROR AL RECIBIR LOS DATOS");
        	}*/
		} catch (ParseException e) {
			e.printStackTrace();
			return new ModelAndView("error/500").addObject("msgError", "ERROR AL RECIBIR LOS DATOS");
		}
    }
    
    @RequestMapping("/users")
    public String list(ModelMap model, @SortDefault("username") Pageable pageable){
    	//model.addAttribute("page", userService.find(pageable));
    	
    	return "users/list";
    }
    
  //PANTALLA PARA VERIFICAR LOS DETALLES DE MOVIMIENTOS
    @RequestMapping(value = "/detalles")//@RequestParam("tipo") String tipo, @RequestParam("row") String row
    public ModelAndView ConsultaDetalleMovimientos() {
    	DetalleConsultaDTO detalles = new DetalleConsultaDTO();
    	String tipo="ap";
    	switch(tipo) {
    		case "b":{
    			detalles.setTitulo("de Bloqueo");
    		}break;
    		case "r":{
    			detalles = setDetalles.SetConsultaDetallesRetencion();
    			
    		}break;
    		case "ap":{
    			detalles = setDetalles.SetConsultaDetallesApunte();
    		}break;
    	}
    	
    	detalles.setTipo(tipo);
    	return new ModelAndView(packageTemplates + "/Detalles").addObject("Model", detalles);
    }
    
    //PANTALLA PARA VERIFICAR LOS DATOS DE AUDITORIA
    @RequestMapping(value = "/auditoria")//@RequestParam("tipo") String tipo, @RequestParam("row") String row
    public ModelAndView ConsultaAuditoria() {
    	DetalleConsultaDTO detalles = new DetalleConsultaDTO();
    	detalles.setTitulo("B�sica de auditor�a");
    	detalles.setTipo("au");
    	return new ModelAndView(packageTemplates + "/Detalles").addObject("Model", detalles);
    }
    
  //PANTALLA PARA VERIFICAR LOS DETALLES DE AUDITORIA
    @RequestMapping(value = "/detalleAuditoria")//@RequestParam("tipo") String tipo, @RequestParam("row") String row
    public ModelAndView ConsultaDetallesAuditoria() {
    	DetalleConsultaDTO detalles = new DetalleConsultaDTO();
    	detalles.setTitulo("de Auditor�a");
    	detalles.setTipo("dAu");
    	return new ModelAndView(packageTemplates + "/Detalles").addObject("Model", detalles);
    }
    
  //PANTALLA PARA VERIFICAR EL ORIGEN 
    @RequestMapping(value = "/origen")//@RequestParam("tipo") String tipo, @RequestParam("row") String row
    public ModelAndView ConsultaOrigen() {
    	DetalleConsultaDTO detalles = new DetalleConsultaDTO();
    	detalles.setTipo("em");
    	if(detalles.getTipo().equals("li")) {
    		detalles.setTitulo("de liquidaci�n");
    	}
    	else {
    		detalles.setTitulo("Emisi�n de cheque");
    	}
    	return new ModelAndView(packageTemplates + "/Detalles").addObject("Model", detalles);
    }

}