package mx.gob.bansefi.services;

import mx.gob.bansefi.dto.Response.*;

import mx.gob.bansefi.dto.Response.DocumentosPM.ResAltaDocTCB;
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
    public ResConsultaNombreDTO consultaNombre(GetConsultaCuentaNombreReqDTO Request)
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
    public ResConsultaApuntesDTO consultaApuntes(GetConsultaMovimientosGeneralReqDTO Request)
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
	public ResConsultaRetencionDTO consultaRetencion(GetConsultaRetencionReqDTO Request)
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
	public ResConsultaBloqueosDTO consultaBloqueos(GetConsultaBloqueosReqDTO Request)
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
	}
	/*
	 * Método para consumir servicio de alta de relacion de documuentos
	 *
	public static ResAltaDocTCB altaRelacioDocumento(ReqAltaRelacionDocumentoDTO req, String url) {
		ResAltaDocTCB respuestaGeneral = new ResAltaDocTCB();

		try {
			String jsonRepuesta = util.callRestPost(req, url);
			respuestaGeneral = (ResAltaDocTCB) util.jsonToObject(respuestaGeneral, jsonRepuesta);
			if(respuestaGeneral == null){
				respuestaGeneral.setEstatus("0");
				respuestaGeneral.setMensaje("Error Inesperado");
			}
		} catch (Exception ex) {
			log.error("\nError en el metodo altaRelacioDocumento(ReqAltaRelacionDocumentoDTO req, String Url)" + "\nException Message: " + ex.getMessage()+"\nURL: " +url);
		}
		return respuestaGeneral;
	}

	/*
	 * Consulta Datos Persona Moral
	 *
	public ResConsultaDatosPersonaMoralDTO consultaDatosPersona(ReqConsultaDTO requestDTO) {
		ResConsultaDatosPersonaMoralDTO responseDTO = null;
		try {
			String jsonRes = util.callRestPost(requestDTO, urlRootContext + urlConsultaDatosPersonaMoral);
			responseDTO = new ResConsultaDatosPersonaMoralDTO();
			if (!jsonRes.equals("")) {
				System.out.println("---->" + jsonRes);
				responseDTO = (ResConsultaDatosPersonaMoralDTO) util.jsonToObject(responseDTO, jsonRes);
			} else {
				responseDTO.setESTATUS("0");
				responseDTO.setMENSAJE(mensajeErrorServicioCliente);
			}
		} catch (org.json.simple.parser.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return responseDTO;
	}
	public static EncriptarResponseDTO Desencriptar(String Encript, String Url) {
		Util util = Util.getInstance();
		EncriptarResponseDTO Response = new EncriptarResponseDTO();
		try {
			EncriptarResquestDTO Request = new EncriptarResquestDTO();
			Request.setText(Encript);
			ArrayList<String> Nodos = new ArrayList<String>();
			String jsonRepuesta = util.callRestPost(Request, Url);
			Response = (EncriptarResponseDTO) util.jsonToObject(Response, jsonRepuesta, Nodos);
		} catch (Exception ex) {
			Response.setCodRet("0");
			Response.setError(ex.getMessage());
			log.error("\nError en el metodo EncriptarResponseDTO Desencriptar(String Encript,String Url)" + "\nException Message: " + ex.getMessage());
		}
		return Response;

	}*/
}
