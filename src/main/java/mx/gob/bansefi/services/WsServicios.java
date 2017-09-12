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
	@Value("${url.altaPersonaMoral}")
	private String urlAltaPersonaMoral;
	@Value("${url.altaRelacionesPerfilTransaccional}")
	private String urlAltaRelacionesPerfilTransaccional;
	@Value("${url.altaRelacionDocumentoPersona}")
	private String urlAltaRelacionDocumentoPersona;
	@Value("${url.consultaPaisNacionalidad}")
	private String urlConsultaPaisNacionalidad;
	@Value("${url.consultaDocumentosRelacionados}")
	private String urlConsultaDocumentosRelacionados;
	@Value("${url.validaIdExterno}")
	private String urlValidaIdExterno;
	@Value("${url.consultaCatalogosPM}")
	private String urlConsultaCatalogosPM;
	@Value("${url.consultaMedioAltoRiesgo}")
	private String urlConsultaMedioAltoRiesgo;
	@Value("${url.consultaFechaRelacionPersonaPersona}")
	private String urlConsultaFechaRelacionPersonaPersona;
	@Value("${url.consultaCriterioBusqueda}")
	private String urlConsultaCriterioBusqueda;
	@Value("${url.consultaDatosPersonaMoral}")
	private String urlConsultaDatosPersonaMoral;
	@Value("${url.consultaCedulaBajoRiesgo}")
	private String urlConsultaCedulaBajoRiesgo;
	@Value("${url.consultaRelaciones}")
	private String urlConsultaRelaciones;
	@Value("${url.ConsultaFichaPersonaMoral}")
	private String urlConsultaFichaPersonaMoral;
	@Value("${url.consultaCedulaMedioAltoRiesgo}")
	private String urlConsultaCedulaMedioAltoRiesgo;
	@Value("${url.modificacionRelaciones}")
	private String urlModificacionRelaciones;
	@Value("${url.consultaDatosPersonaFisica}")
	private String urlConsultaDatosPersonaFisica;
	@Value("${url.modificaDatosPersonaMoral}")
	private String urlmodificaDatosPersonaMoral;

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

	public static GetLocalidadResponseDTO Localidad(GetLocalidadRequestDTO Request, String Url)
	{
		Util util = Util.getInstance();
		GetLocalidadResponseDTO Datos=new GetLocalidadResponseDTO();
		try
		{

			ArrayList<String> Nodos=new ArrayList<String>();
			Nodos.add("getLocalidadResp");
			Nodos.add("Localidad");
			String jsonRepuesta= util.callRestPost(Request,Url);
			Datos=(GetLocalidadResponseDTO)util.jsonToObject(Datos,jsonRepuesta,Nodos);
		}
		catch(Exception ex)
		{
			log.error("\nError en el metodo Consulta(BusquedaPersonaRequesDTO Request, String Url)"
					+ "\nException Message: " + ex.getMessage());

		}
		return Datos;
	}

	/*
	 * Metodo para consulta de datos de persona fisica
	 */
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
	 */
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
	 */
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

	}
}
