package mx.gob.bansefi.process;

import mx.gob.bansefi.dto.ConsultaPrincipalDTO;
import mx.gob.bansefi.dto.DetalleConsultaDTO;
import mx.gob.bansefi.dto.GralApunteDTO;
import mx.gob.bansefi.dto.SituacionApunteDTO;
import mx.gob.bansefi.dto.Response.GetLocalidadResponseDTO;
import mx.gob.bansefi.services.WsServicios;
//import scala.annotation.meta.setter;

import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by AppWhere on 12/09/2017.
 */
@Service
public class SetConsultaDetallesProccess {
    @Value("${url.context}")
    private String urlcontext;
    @Value("${url.getLocalidad}")
    private String urlgetLocalidad;

    private DecimalFormat df = new DecimalFormat("0.00");
    public DetalleConsultaDTO SetConsultaDetallesApunte() {
    	DetalleConsultaDTO detalles = new DetalleConsultaDTO();
    	
    	detalles.setTitulo("de Apunte");
		detalles.setTitular("MARIA DE LOS ANGELES PEREZ FRAGA MONTES ALATORRE");
		detalles.setProdVendible("CUENTA AHORRO");
		detalles.setIdTipoCuenta("01");
		detalles.setTipoCuenta("CLIENTE");
		detalles.setNumero("25");
		detalles.setFechaContable("07/03/2014");
		detalles.setFechaValor("10/03/2014");
		detalles.setImporte("65000");
		detalles.setEstatus("H");
		detalles.setMoneda("MXN");
    	
		List<SituacionApunteDTO> lstSitApunte = new ArrayList<SituacionApunteDTO>(); 
		SituacionApunteDTO sitApunte = new SituacionApunteDTO();
		sitApunte.setIndicador("COMUNICACION CLIENTE");
		sitApunte.setSituacion("NORMAL");
		lstSitApunte.add(sitApunte);
		
		sitApunte = new SituacionApunteDTO();
		sitApunte.setIndicador("SIT. DE EXTRACTO");
		sitApunte.setSituacion("EXTRACTO");
		lstSitApunte.add(sitApunte);
		
		sitApunte = new SituacionApunteDTO();
		sitApunte.setIndicador("SIT. DE ANULACION");
		sitApunte.setSituacion("NORMAL");
		lstSitApunte.add(sitApunte);
		
		sitApunte = new SituacionApunteDTO();
		sitApunte.setIndicador("CONTRAPARTIDA");
		sitApunte.setSituacion("AUTOMATICA");
		lstSitApunte.add(sitApunte);
		
		detalles.setSitApunte(lstSitApunte);
		
        return detalles;
    }
    
    public DetalleConsultaDTO SetConsultaDetallesRetencion() {
    	DetalleConsultaDTO detalles = new DetalleConsultaDTO();
    	detalles.setTitulo("de Retención");
    	detalles.setNumAcuerdo("0923498");
    	detalles.setCodEmpleado("EOJJKRF");
    	detalles.setCentro("");
    	detalles.setTitular("");
    	detalles.setIdTipoBloqueo("");
    	detalles.setTipoBloqueo("");
    	detalles.setFechaAlta("07/03/2014");
		detalles.setFechaVto("10/03/2014");
		detalles.setMotivo("");
		detalles.setSituacion("");
		
    	return detalles;
    }
}
