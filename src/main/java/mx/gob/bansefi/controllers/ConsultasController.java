package mx.gob.bansefi.controllers;

import mx.gob.bansefi.clients.DocumentosClient;
import mx.gob.bansefi.dto.Modelos.*;
import mx.gob.bansefi.dto.BsfOperadorDTO;
import mx.gob.bansefi.dto.BusquedaDTO;
import mx.gob.bansefi.dto.ConsultaPrincipalDTO;
import mx.gob.bansefi.dto.DetalleConsultaDTO;
import mx.gob.bansefi.dto.GralMovimientoDTO;
import mx.gob.bansefi.dto.ReqEncryptDTO;
import mx.gob.bansefi.dto.ReqEncryptORDecryptDTO;
import mx.gob.bansefi.dto.Request.*;
import mx.gob.bansefi.dto.Request.ReqConsultaDTO;
import mx.gob.bansefi.dto.ResEncryptORDecryptDTO;
import mx.gob.bansefi.dto.Response.*;
import mx.gob.bansefi.dto.Response.ResDatosPersonaFisicaDTO;
import mx.gob.bansefi.process.SetConsultaDetallesProccess;
import mx.gob.bansefi.process.SetConsultaPrincipalProccess;
import mx.gob.bansefi.services.SecurityWS;
import mx.gob.bansefi.services.WsServicios;
import mx.gob.bansefi.utils.Util;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

import org.json.JSONObject;
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
    @Value("${url.getAltPersMoral}")
    private String urlAltPersMoral;
    @Value("${url.decrypt}")
    private String urldecrypt;
    @Value("${url.encrypt}")
    private String urlencrypt;
    @Value("${url.context}")
    private String urlcontext;
    @Value("${url.getLocalidad}")
    private String urlgetLocalidad;
    @Value("${url.EditPF}")
    private String urlEditPersonaFisica;

    @Autowired
    WsServicios wsServicios;
    private Util util = Util.getInstance();
    @Autowired
    SecurityWS securityWs;
    @Autowired
    private DocumentosClient documentosClient;
    @Autowired
    private SetConsultaDetallesProccess setDetalles;
    
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
            busquedaDatos.setFormato("oficina");
            //BsfOperadorDTO bsfOperadorDecrypt = securityWs.decriptBsfOperador(new ReqEncryptORDecryptDTO(bsfOperador));
            try {
            	System.out.print("Setear algun valor");
            } catch (Exception ex) {
                System.out.print(ex.getMessage());
            }
            return new ModelAndView(packageTemplates + "/Buscador").addObject("Model", busquedaDatos);
        } else {
            return new ModelAndView("error/500").addObject("msgError", "ERROR A RECIBIR LOS DATOS");

        }
    }
    //PANTALLA PRINCIPAL DE BUSQUEDA
    @RequestMapping(value = "/busquedaApuntes")
    public ModelAndView BusquedaApunte() {
    	
    	return new ModelAndView(packageTemplates + "/Buscador");
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
    public ModelAndView ConsultaDetalleAnotaciones(@RequestParam("tipo") String tipo) {
    	DetalleConsultaDTO detalles = new DetalleConsultaDTO();
    	detalles.setTitulo("Detalle de Anotación");
    	detalles.setTipo(tipo);
    	return new ModelAndView(packageTemplates + "/Detalles").addObject("Model", detalles);
    }
    //PANTALLA PRINCIPAL DE LOS MOVIMIENTOS DE LA CUENTA
    @RequestMapping(value = "/principalMovimientos")
    public ModelAndView PrincipalMovimientos() {
    	ConsultaPrincipalDTO detalles = new ConsultaPrincipalDTO();
    	SetConsultaPrincipalProccess construct= new SetConsultaPrincipalProccess();
    	detalles = construct.SetConsultaPrincipal();
    	return new ModelAndView(packageTemplates + "/PrincipalConsultas").addObject("Model", detalles);
    }
    
    @RequestMapping("/users")
    public String list(ModelMap model, @SortDefault("username") Pageable pageable){
    	//model.addAttribute("page", userService.find(pageable));
    	
    	return "users/list";
    }
    
  //PANTALLA PARA VERIFICAR LOS DETALLES DE MOVIMIENTOS
    @RequestMapping(value = "/detalles")
    public ModelAndView ConsultaDetalleMovimientos(@RequestParam("tipo") String tipo) {
    	DetalleConsultaDTO detalles = new DetalleConsultaDTO();
    	switch(tipo) {
    		case "b":{
    			detalles.setTitulo("de Bloqueo");
    		}break;
    		case "r":{
    			detalles.setTitulo("de Retención");
    		}break;
    		case "ap":{
    			detalles = setDetalles.SetConsultaDetallesApunte();
    		}break;
    	}
    	
    	detalles.setTipo(tipo);
    	return new ModelAndView(packageTemplates + "/Detalles").addObject("Model", detalles);
    }

}