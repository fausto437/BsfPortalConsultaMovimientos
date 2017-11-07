package mx.gob.bansefi.services;

import java.util.ArrayList;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import mx.gob.bansefi.dto.Modelos.BsfOperador;
import mx.gob.bansefi.dto.Request.Documentos.ReqEncryptORDecryptDTO;
import mx.gob.bansefi.dto.Response.ResEncryptORDecryptDTO;
import mx.gob.bansefi.utils.Util;

@Service
public class SecurityWS {

	private String urlEncrypt;
	private String urlDecrypt;
	Util util = Util.getInstance();
	private static final Logger log = LogManager.getLogger(SecurityWS.class);

	public SecurityWS(@Value("${domain.services}") String domainServices, @Value("${path.encrypt}") String pathEncrypt, @Value("${path.decrypt}") String pathDecrypt) {
		this.urlDecrypt = domainServices + pathDecrypt;
		this.urlEncrypt = domainServices + pathEncrypt;
	}

	
	public BsfOperador decriptBsfOperadorDoc(ReqEncryptORDecryptDTO req) {
		ResEncryptORDecryptDTO res = null;
		BsfOperador bsfOperador = new BsfOperador();
		try {
			String jsonRes = util.callRestPost(req, urlDecrypt);
			res = new ResEncryptORDecryptDTO();
			ArrayList<String> nodos = new ArrayList<String>();
			res = (ResEncryptORDecryptDTO) util.jsonToObject(res, jsonRes, nodos);
			if (res.getCodRet() == 1) {
				res.setError(res.getError().replace('\\', ' '));
				res.setRespuesta(res.getRespuesta().replace('\\', ' '));
				bsfOperador = (BsfOperador) util.jsonToObject(bsfOperador, res.getRespuesta(), nodos);
				bsfOperador.getBSFOPERADOR().setStatus(res.getCodRet());
			} else {
				log.error("\nError en el servicio decript URL:" + urlDecrypt + " \nStatus: " + res.getCodRet() + "\nMsgError: " + res.getError());
				bsfOperador.getBSFOPERADOR().setStatus(res.getCodRet());
				bsfOperador.getBSFOPERADOR().setDescripcion(res.getError());
			}
		} catch (Exception ex) {
			log.error("\nError en el metodo decriptBsfOperador(ReqEncryptDTO req, String Url)" + "\nException Message: " + ex.getMessage());
			bsfOperador.getBSFOPERADOR().setStatus(0);
		}
		return bsfOperador;
	}
	public mx.gob.bansefi.dto.Modelos.BsfOperadorDTO decriptBsfOperador2(ReqEncryptORDecryptDTO req) {
		ResEncryptORDecryptDTO res = null;
		mx.gob.bansefi.dto.Modelos.BsfOperadorDTO bsfOperador = new mx.gob.bansefi.dto.Modelos.BsfOperadorDTO();
		try {
			String jsonRes = util.callRestPost(req, urlDecrypt);
			System.out.println(jsonRes);
			res = new ResEncryptORDecryptDTO();
			ArrayList<String> nodos = new ArrayList<String>();
			res = (ResEncryptORDecryptDTO) util.jsonToObject(res, jsonRes, nodos);
			if (res.getCodRet() == 1) {
				res.setError(res.getError().replace('\\', ' '));
				bsfOperador = (mx.gob.bansefi.dto.Modelos.BsfOperadorDTO) util.jsonToObject(bsfOperador, res.getRespuesta(), nodos);
				bsfOperador.setStatus(res.getCodRet());
			} else {
				log.error("\nError en el servicio decript URL:" + urlDecrypt + " \nStatus: " + res.getCodRet() + "\nMsgError: " + res.getError());
				bsfOperador.setStatus(res.getCodRet());
				bsfOperador.setDescripcion(res.getError());
			}
		} catch (Exception ex) {
			log.error("\nError en el metodo decriptBsfOperador(ReqEncryptDTO req, String Url)" + "\nException Message: " + ex.getMessage());
			bsfOperador.setStatus(0);
		}
		return bsfOperador;
	}

	//METODO DE ENCRIPCION
	public ResEncryptORDecryptDTO encrypt(ReqEncryptORDecryptDTO req) {
		ResEncryptORDecryptDTO res = new ResEncryptORDecryptDTO();
		try {
			String jsonRes = util.callRestPost(req, urlEncrypt);
			res = new ResEncryptORDecryptDTO();
			ArrayList<String> nodos = new ArrayList<String>();
			res = (ResEncryptORDecryptDTO) util.jsonToObject(res, jsonRes, nodos);
			if (res.getCodRet() != 1) {
				log.error("\nError en el servicio encrypt URL:" + urlEncrypt + " \nStatus: " + res.getCodRet() + "\nMsgError: " + res.getError());
			}
		} catch (Exception ex) {
			log.error("\nError en el metodo encrypt(ReqEncryptORDecryptDTO req)" + "\nException Message: " + ex.getMessage());
			res.setCodRet(0);
		}
		return res;
	}
	
	//METODO DE DESENCRIPCIPON
	public ResEncryptORDecryptDTO decrypt(ReqEncryptORDecryptDTO req) {
		ResEncryptORDecryptDTO res = new ResEncryptORDecryptDTO ();
		try {
			String jsonRes = util.callRestPost(req, urlDecrypt);
			ArrayList<String> nodos = new ArrayList<String>();
			res = (ResEncryptORDecryptDTO) util.jsonToObject(res, jsonRes, nodos);
			if (res.getCodRet() != 1) {
				log.error("\nError en el servicio decrypt URL:" + urlDecrypt + " \nStatus: " + res.getCodRet() + "\nMsgError: " + res.getError());
			}
		} catch (Exception ex) {
			log.error("\nError en el metodo decrypt(ReqEncryptORDecryptDTO req)" + "\nException Message: " + ex.getMessage());
			res.setCodRet(0);
		}
		return res;
	}
}
