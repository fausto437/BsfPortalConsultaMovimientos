package mx.gob.bansefi.process;

import mx.gob.bansefi.dto.ConsultaPrincipalDTO;
import mx.gob.bansefi.dto.DetalleConsultaDTO;
import mx.gob.bansefi.dto.GralApunteDTO;
import mx.gob.bansefi.dto.GralBloqueoDTO;
import mx.gob.bansefi.dto.GralRetencionDTO;
import mx.gob.bansefi.dto.SituacionApunteDTO;
import mx.gob.bansefi.services.WsServicios;
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
    
    public DetalleConsultaDTO SetConsultaDetallesRetencion(GralRetencionDTO renglonRetencion) {
    	DetalleConsultaDTO detalles = new DetalleConsultaDTO();
    	detalles.setTitulo("de Retención");
    	detalles.setCodEmpleado(renglonRetencion.getEmpleado());
    	detalles.setIdTipoRetencion(renglonRetencion.getTipo());
    	detalles.setTipoRetencion("");
    	detalles.setFechaAlta(renglonRetencion.getFechaAlta());
		detalles.setFechaVto(renglonRetencion.getFechaVTO());
		detalles.setMotivo(renglonRetencion.getConcepto());
		detalles.setSituacion(renglonRetencion.getEstado());
		detalles.setImporte(renglonRetencion.getImporte());
		detalles.setOrigen(renglonRetencion.getOrigen());
		detalles.setMoneda("MXN");
		try {
			String centro = renglonRetencion.getOrigen().substring(0, renglonRetencion.getOrigen().indexOf('-'));
			detalles.setCentro(centro);
		}
		catch(Exception e) {
			detalles.setCentro("");
			e.printStackTrace();
		}
    	return detalles;
    }

	public DetalleConsultaDTO SetConsultaDetallesBloqueo(GralBloqueoDTO renglonBloqueo) {
		DetalleConsultaDTO detalles = new DetalleConsultaDTO();
		detalles.setTitulo("de Bloqueo");
		detalles.setIdTipoBloqueo(renglonBloqueo.getTipo());
		detalles.setMotivo(renglonBloqueo.getConcepto());
		detalles.setSituacion(renglonBloqueo.getEstado());
		detalles.setFechaAlta(renglonBloqueo.getFechaAlta());
		detalles.setFechaVto(renglonBloqueo.getFechaVTO());
		detalles.setCodEmpleado(renglonBloqueo.getEmpleado());
		detalles.setCentro(renglonBloqueo.getCentro());
		detalles.setImporte(renglonBloqueo.getImporte());
		
		return detalles;
	}
}
