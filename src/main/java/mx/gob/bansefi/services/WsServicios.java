package mx.gob.bansefi.services;

import mx.gob.bansefi.dto.Response.*;
import mx.gob.bansefi.dto.Request.*;
import mx.gob.bansefi.utils.Util;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Created by AppWhere on 26/06/2017.
 */
@Service
public class WsServicios {

	/*
	 * Definicion de variables urls para servicios
	 */
	@Value("${domain.servicesU}")
	private String urlRootContext;
	@Value("${url.consultaCatalogosPM}")
	private String urlConsultaCatalogosPM;
	@Value("${url.ConsultaNombre}")
	private String urlConsultaNombre;
	@Value("${url.ConsultaPrincipal}")
	private String urlConsultaPrincipal;
	@Value("${url.ConsultaBloqueos}")
	private String urlConsultaBloqueos;
	@Value("${url.ConsultaRetenciones}")
	private String urlConsultaRetenciones;
	@Value("${url.ConsultaAnotaciones}")
	private String urlConsultaAnotaciones;
	@Value("${url.ConsultaDetalleAnotacion}")
	private String urlConsultaDetalleAnotacion;
	@Value("${url.ConsultaDetalleApunte}")
	private String urlConsultaDetalleApunte;
	@Value("${url.ConsultaAuditoria}")
	private String urlConsultaAuditoria;
	@Value("${url.ConsultaAuditoriaDetalles}")
	private String urlConsultaAuditoriaDetalles;
	@Value("${url.ConsultaLiquidacion}")
	private String urlConsultaLiquidacion;
	@Value("${url.ConsultaEmision}")
	private String urlConsultaEmision;

	/*
	 * Definicion de variables mensajes de servicios
	 */
	@Value("${mensaje.errorServicioCliente}")
	private static String mensajeErrorServicioCliente;
	/*
	 * Definicion de loog4g
	 */

    private static final Logger log = LogManager.getLogger(WsServicios.class);
    private static Util util = Util.getInstance();

    /*CONSULTA DE NOMBRE*/
    public ResConsultaNombreDTO consultaNombre(ReqConsultaCuentaNombreDTO Request)
	{
		ResConsultaNombreDTO Datos=new ResConsultaNombreDTO();
		try
		{
			String jsonRepuesta= util.callRestPost(Request, urlRootContext+ urlConsultaNombre);
			Datos=(ResConsultaNombreDTO)util.jsonToObject(Datos,jsonRepuesta);
		}
		catch(Exception ex)
		{
			log.error("\nError en el metodo consultaNombre(GetConsultaCuentaNombreReqDTO Request)"
					+ "\nException Message: " + ex.getMessage());

		}
		return Datos;
	}
    
    /*CONSULTA DE MOVIMIENTOS*/
    public ResConsultaApuntesDTO consultaApuntes(ReqConsultaMovimientosGeneralDTO Request)
	{
    	ResConsultaApuntesDTO Datos=new ResConsultaApuntesDTO();
		try
		{
			String jsonRepuesta= util.callRestPost(Request, urlRootContext+ urlConsultaPrincipal);
			Datos=(ResConsultaApuntesDTO)util.jsonToObject(Datos,jsonRepuesta);
		}
		catch(Exception ex)
		{
			log.error("\nError en el metodo consultaApuntes(GetConsultaMovimientosGeneralReqDTO Request)"
					+ "\nException Message: " + ex.getMessage());

		}
		return Datos;
	}
    
    /*CONSULTA DE RETENCIONES*/
	public ResConsultaRetencionDTO consultaRetencion(ReqConsultaRetencionDTO Request)
	{
		Util util = Util.getInstance();
		ResConsultaRetencionDTO Datos=new ResConsultaRetencionDTO();
		try
		{
			String jsonRepuesta= util.callRestPost(Request,urlRootContext+ urlConsultaRetenciones);
			Datos=(ResConsultaRetencionDTO)util.jsonToObject(Datos,jsonRepuesta);
		}
		catch(Exception ex)
		{
			log.error("\nError en el metodo consultaRetencion(GetConsultaRetencionReqDTO Request)"
					+ "\nException Message: " + ex.getMessage());

		}
		return Datos;
	}
	
	/*CONSULTA DE BLOQUEOS*/
	public ResConsultaBloqueosDTO consultaBloqueos(ReqConsultaBloqueosDTO Request)
	{
		Util util = Util.getInstance();
		ResConsultaBloqueosDTO Datos=new ResConsultaBloqueosDTO();
		try
		{
			String jsonRepuesta= util.callRestPost(Request,urlRootContext + urlConsultaBloqueos);
			Datos=(ResConsultaBloqueosDTO)util.jsonToObject(Datos,jsonRepuesta);
		}
		catch(Exception ex)
		{
			log.error("\nError en el metodo consultaBloqueos(GetConsultaBloqueosReqDTO Request)"
					+ "\nException Message: " + ex.getMessage());

		}
		return Datos;
	}
	
	/*CONSULTA DE DETALLE DE APUNTE*/
	public ResConsultaApunteDetalleDTO consultaDetalleApunte(ReqConsultaApunteDetallesDTO Request)
	{
		Util util = Util.getInstance();
		ResConsultaApunteDetalleDTO Datos=new ResConsultaApunteDetalleDTO();
		try
		{
			String jsonRepuesta= util.callRestPost(Request,urlRootContext + urlConsultaDetalleApunte);
			Datos=(ResConsultaApunteDetalleDTO)util.jsonToObject(Datos,jsonRepuesta);
		}
		catch(Exception ex)
		{
			log.error("\nError en el metodo consultaBloqueos(GetConsultaBloqueosReqDTO Request)"
					+ "\nException Message: " + ex.getMessage());

		}
		return Datos;
	}
	
	/*CONSULTA DE ANOTACIONES*/
	public ResConsultaAnotacionesDTO consultaAnotaciones(ReqConsultaAnotacionesDTO Request) {
		Util util = Util.getInstance();
		ResConsultaAnotacionesDTO Datos=new ResConsultaAnotacionesDTO();
		try
		{
			String jsonRepuesta= util.callRestPost(Request,urlRootContext + urlConsultaAnotaciones);
			Datos=(ResConsultaAnotacionesDTO)util.jsonToObject(Datos,jsonRepuesta);
		}
		catch(Exception ex)
		{
			log.error("\nError en el metodo consultaAnotaciones(ReqConsultaAnotacionesDTO Request)"
					+ "\nException Message: " + ex.getMessage());

		}
		return Datos;
	}
	
	/*CONSULTA DE DETALLE DE ANOTACION*/
	public ResConsultaAnotacionDetalleDTO consultaDetalleAnotacion(ReqConsultaAnotacionDetallesDTO Request)
	{
		Util util = Util.getInstance();
		ResConsultaAnotacionDetalleDTO Datos = new ResConsultaAnotacionDetalleDTO();
		try
		{
			String jsonRepuesta= util.callRestPost(Request,urlRootContext + urlConsultaDetalleAnotacion);
			Datos=(ResConsultaAnotacionDetalleDTO)util.jsonToObject(Datos,jsonRepuesta);
		}
		catch(Exception ex)
		{
			log.error("\nError en el metodo consultaDetalleAnotacion(ReqConsultaAnotacionDetallesDTO Request)"
					+ "\nException Message: " + ex.getMessage());

		}
		return Datos;
	}
	
	/*CONSULTA DE AUDITORIA*/
	public ResConsultaAuditoriaDTO consultaAuditoria(ReqConsultaAuditoriaDTO Request) {
		Util util = Util.getInstance();
		ResConsultaAuditoriaDTO Datos = new ResConsultaAuditoriaDTO();
		try
		{
			String jsonRepuesta= util.callRestPost(Request,urlRootContext + urlConsultaAuditoria);
			Datos=(ResConsultaAuditoriaDTO)util.jsonToObject(Datos,jsonRepuesta);
		}
		catch(Exception ex)
		{
			log.error("\nError en el metodo consultaDetalleAnotacion(ReqConsultaAnotacionDetallesDTO Request)"
					+ "\nException Message: " + ex.getMessage());

		}
		return Datos;
	}

	/*CONSULTA DE DETALLE DE AUDITORIA*/
	public ResConsultaAuditoriaDetalleDTO consultaAuditoriaDetalles(ReqConsultaAuditoriaDetallesDTO Request) {
		Util util = Util.getInstance();
		ResConsultaAuditoriaDetalleDTO Datos = new ResConsultaAuditoriaDetalleDTO();
		try
		{
			String jsonRepuesta= util.callRestPost(Request,urlRootContext + urlConsultaAuditoriaDetalles);
			Datos=(ResConsultaAuditoriaDetalleDTO)util.jsonToObject(Datos,jsonRepuesta);
		}
		catch(Exception ex)
		{
			log.error("\nError en el metodo consultaAuditoriaDetalles(ReqConsultaAuditoriaDetallesDTO Request)"
					+ "\nException Message: " + ex.getMessage());

		}
		return Datos;
	}

	/*CONSULTA DE LIQUIDACION*/
	public ResConsultaLiquidacionDTO consultaLiquidacion(ReqConsultaLiquidacionDTO Request) {
		Util util = Util.getInstance();
		ResConsultaLiquidacionDTO Datos = new ResConsultaLiquidacionDTO();
		try
		{
			String jsonRepuesta= util.callRestPost(Request,urlRootContext + urlConsultaLiquidacion);
			Datos=(ResConsultaLiquidacionDTO)util.jsonToObject(Datos,jsonRepuesta);
		}
		catch(Exception ex)
		{
			log.error("\nError en el metodo consultaLiquidacion(ReqConsultaLiquidacionDTO Request)"
					+ "\nException Message: " + ex.getMessage());

		}
		return Datos;
	}

	/*CONSULTA DE EMISION*/
	public ResConsultaEmisionDTO consultaEmision(ReqConsultaEmisionDTO Request) {
		Util util = Util.getInstance();
		ResConsultaEmisionDTO Datos = new ResConsultaEmisionDTO();
		try
		{
			String jsonRepuesta= util.callRestPost(Request,urlRootContext + urlConsultaEmision);
			Datos=(ResConsultaEmisionDTO)util.jsonToObject(Datos,jsonRepuesta);
		}
		catch(Exception ex)
		{
			log.error("\nError en el metodo consultaEmision(ReqConsultaEmisionDTO req)"
					+ "\nException Message: " + ex.getMessage());

		}
		return Datos;
	}

	/*
	 * Metodo para consulta de datos de persona fisica
	 *
	public ResDatosPersonaFisicaDTO consultaDatosPersonaFisica(ReqConsultaDTO req) {

		ResDatosPersonaFisicaDTO personaFisica = new ResDatosPersonaFisicaDTO();
		try {
			String jsonRepuesta = util.callRestPost(req, urlRootContext + urlConsultaDatosPersonaFisica);
			personaFisica = (ResDatosPersonaFisicaDTO) util.jsonToObject(personaFisica, jsonRepuesta);
		} catch (Exception ex) {
			log.error("\nError en el metodo Consulta(ResDatosPersonaFisicaDTO Request, String Url)" + "\nException Message: " + ex.getMessage());
		}
		return personaFisica;
	}*/
}
