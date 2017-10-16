package mx.gob.bansefi.process;

import mx.gob.bansefi.dto.AnotacionesDTO;
import mx.gob.bansefi.dto.ApunteDTO;
import mx.gob.bansefi.dto.ConsultaPrincipalDTO;
import mx.gob.bansefi.dto.GralAnotacionDTO;
import mx.gob.bansefi.dto.GralBloqueoDTO;
import mx.gob.bansefi.dto.GralApunteDTO;
import mx.gob.bansefi.dto.GralRetencionDTO;
import mx.gob.bansefi.dto.RetencionesDTO;
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
public class SetConsultaPrincipalProccess {
    @Value("${url.context}")
    private String urlcontext;

    private DecimalFormat df = new DecimalFormat("0.00");
    //Funcion para llenar los campos de la tabla de bloqueos
    public List<GralBloqueoDTO> SetConsultaBloqueos(List<GralBloqueoDTO> lstBloqueos){
    	List<GralBloqueoDTO> nuevaLista = new ArrayList<GralBloqueoDTO>();
    	for (GralBloqueoDTO datoBloqueo : lstBloqueos) {
    		GralBloqueoDTO nuevoObj = new GralBloqueoDTO();
    		nuevoObj.setCentro(datoBloqueo.getCentro()==null?"":datoBloqueo.getCentro());
    		nuevoObj.setConcepto(datoBloqueo.getConcepto()==null?"":datoBloqueo.getConcepto());
    		nuevoObj.setEmpleado(datoBloqueo.getEmpleado()==null?"":datoBloqueo.getEmpleado());
    		nuevoObj.setFechaAlta(datoBloqueo.getFechaAlta()==null?"":datoBloqueo.getFechaAlta());
    		nuevoObj.setFechaVTO(datoBloqueo.getFechaVTO()==null?"":datoBloqueo.getFechaVTO());
    		/*try {
				String nueva_fecha=datoBloqueo.getFechaVTO().substring(6, 8)+"/"+datoBloqueo.getFechaVTO().substring(4, 6)+"/"+datoBloqueo.getFechaVTO().substring(0, 4);
				nuevoObj.setFechaVTO(nueva_fecha);
			} catch (Exception e) {
				nuevoObj.setFechaVTO("");
				e.printStackTrace();
			}*/
    		try {
    			nuevoObj.setImporte(datoBloqueo.getImporte()==null?"":""+df.format(Double.parseDouble(datoBloqueo.getImporte())));    			
    		}
    		catch(Exception e) {
    			nuevoObj.setImporte("");
				e.printStackTrace();
    		}
    		
    		nuevoObj.setMotivo(datoBloqueo.getMotivo()==null?"":datoBloqueo.getMotivo());
    		nuevoObj.setTipo(datoBloqueo.getTipo()==null?"":datoBloqueo.getTipo());
    		nuevoObj.setEstado("ACTIVO");
    		nuevaLista.add(nuevoObj);
    	}
    	
    	
    	return nuevaLista;
    }
    
    //Funcion para llenar las tablas de la pantalla principal con datos de prueba
    public ConsultaPrincipalDTO SetConsultaPrincipal() {
    	ConsultaPrincipalDTO detalles = new ConsultaPrincipalDTO();
    	
    	//BLOQUEOS
    	List<GralBloqueoDTO> lstBloqueos = new ArrayList<GralBloqueoDTO>();
    	GralBloqueoDTO bloqueo = new GralBloqueoDTO();
    	bloqueo.setTipo("Q7");
    	bloqueo.setEstado("ACTIVO");
    	bloqueo.setFechaAlta("12/05/2008");
    	bloqueo.setFechaVTO("");
    	bloqueo.setConcepto("");
    	bloqueo.setEmpleado("E1800160");
    	bloqueo.setCentro("5028");
    	bloqueo.setImporte("");
    	lstBloqueos.add(bloqueo);
    	
    	bloqueo = new GralBloqueoDTO();
    	bloqueo.setTipo("Q7");
    	bloqueo.setEstado("ACTIVO");
    	bloqueo.setFechaAlta("12/05/2008");
    	bloqueo.setFechaVTO("");
    	bloqueo.setConcepto("");
    	bloqueo.setEmpleado("E1800160");
    	bloqueo.setCentro("5028");
    	bloqueo.setImporte("");
    	lstBloqueos.add(bloqueo);
    	
    	bloqueo = new GralBloqueoDTO();
    	bloqueo.setTipo("Q7");
    	bloqueo.setEstado("ACTIVO");
    	bloqueo.setFechaAlta("12/05/2008");
    	bloqueo.setFechaVTO("");
    	bloqueo.setConcepto("");
    	bloqueo.setEmpleado("E1800160");
    	bloqueo.setCentro("5028");
    	bloqueo.setImporte("");
    	lstBloqueos.add(bloqueo);
    	
    	bloqueo = new GralBloqueoDTO();
    	bloqueo.setTipo("Q7");
    	bloqueo.setEstado("ACTIVO");
    	bloqueo.setFechaAlta("12/05/2008");
    	bloqueo.setFechaVTO("");
    	bloqueo.setConcepto("");
    	bloqueo.setEmpleado("E1800160");
    	bloqueo.setCentro("5028");
    	bloqueo.setImporte("");
    	lstBloqueos.add(bloqueo);
    	
    	detalles.setBloqueos(lstBloqueos);
    	
    	//RETENCIONES
    	List<GralRetencionDTO> lstRetenciones = new ArrayList<GralRetencionDTO>();
    	GralRetencionDTO retencion = new GralRetencionDTO();
    	retencion.setTipo("Q7");
    	retencion.setTipo("ACTIVO");
    	retencion.setFechaAlta("12/05/2008");
    	retencion.setFechaVTO("12/05/2008");
    	retencion.setConcepto("POR QUE NO 123456 BANAEX");
    	retencion.setEmpleado("E172379129");
    	retencion.setOrigen("0166-0069644029-15/07/2014-0000001");
    	retencion.setImporte("10.00");
    	lstRetenciones.add(retencion);
    	
    	retencion = new GralRetencionDTO();
    	retencion.setTipo("Q7");
    	retencion.setTipo("ACTIVO");
    	retencion.setFechaAlta("12/05/2008");
    	retencion.setFechaVTO("12/05/2008");
    	retencion.setConcepto("POR QUE NO 123456 BANAEX");
    	retencion.setEmpleado("E172379129");
    	retencion.setOrigen("0166-0069644029-15/07/2014-0000001");
    	retencion.setImporte("10.00");
    	lstRetenciones.add(retencion);
    	
    	retencion = new GralRetencionDTO();
    	retencion.setTipo("Q7");
    	retencion.setTipo("ACTIVO");
    	retencion.setFechaAlta("12/05/2008");
    	retencion.setFechaVTO("12/05/2008");
    	retencion.setConcepto("POR QUE NO 123456 BANAEX");
    	retencion.setEmpleado("E172379129");
    	retencion.setOrigen("0166-0069644029-15/07/2014-0000001");
    	retencion.setImporte("10.00");
    	lstRetenciones.add(retencion);
    	
    	//APUNTES
    	List<ApunteDTO> lstApuntes= new ArrayList<ApunteDTO>();
    	ApunteDTO apunte = new ApunteDTO();
    	apunte.setConcepto("PRUEBA FOLIO");
    	apunte.setFechaoperacion("04/03/2014");
    	apunte.setFechavalor("04/03/2014");
    	apunte.setImporte("-1.00 MXN");
    	apunte.setOfterminal("12012130");
    	apunte.setSaldo("1.00 MXN");
    	apunte.setSigno("D");
    	lstApuntes.add(apunte);
    	
    	apunte = new ApunteDTO();
    	apunte.setConcepto("C. OVI PRUEBA");
    	apunte.setFechaoperacion("04/03/2014");
    	apunte.setFechavalor("04/03/2014");
    	apunte.setImporte("-333.00 MXN");
    	apunte.setOfterminal("12012130");
    	apunte.setSaldo("2.00 MXN");
    	apunte.setSigno("D");
    	lstApuntes.add(apunte);
    	
    	apunte = new ApunteDTO();
    	apunte.setConcepto("C. OVI PRUEBA VALIDAR DECIMALES");
    	apunte.setFechaoperacion("04/03/2014");
    	apunte.setFechavalor("04/03/2014");
    	apunte.setImporte("-67,543.00 MXN");
    	apunte.setOfterminal("12012130");
    	apunte.setSaldo("3.00 MXN");
    	apunte.setSigno("H");
    	lstApuntes.add(apunte);
    	
    	apunte = new ApunteDTO();
    	apunte.setConcepto("PRUEBA FOLIO");
    	apunte.setFechaoperacion("04/03/2014");
    	apunte.setFechavalor("04/03/2014");
    	apunte.setImporte("-1.00 MXN");
    	apunte.setOfterminal("12012130");
    	apunte.setSaldo("4.00 MXN");
    	apunte.setSigno("D");
    	lstApuntes.add(apunte);
    	
    	apunte = new ApunteDTO();
    	apunte.setConcepto("C. OVI PRUEBA");
    	apunte.setFechaoperacion("04/03/2014");
    	apunte.setFechavalor("04/03/2014");
    	apunte.setImporte("-333.00 MXN");
    	apunte.setOfterminal("12012130");
    	apunte.setSaldo("5.00 MXN");
    	apunte.setSigno("D");
    	lstApuntes.add(apunte);
    	
    	apunte = new ApunteDTO();
    	apunte.setConcepto("C. OVI PRUEBA VALIDAR DECIMALES");
    	apunte.setFechaoperacion("04/03/2014");
    	apunte.setFechavalor("04/03/2014");
    	apunte.setImporte("-67,543.00 MXN");
    	apunte.setOfterminal("12012130");
    	apunte.setSaldo("6.00 MXN");
    	apunte.setSigno("H");
    	lstApuntes.add(apunte);
    	
    	apunte = new ApunteDTO();
    	apunte.setConcepto("PRUEBA FOLIO");
    	apunte.setFechaoperacion("04/03/2014");
    	apunte.setFechavalor("04/03/2014");
    	apunte.setImporte("-1.00 MXN");
    	apunte.setOfterminal("12012130");
    	apunte.setSaldo("7.00 MXN");
    	apunte.setSigno("D");
    	lstApuntes.add(apunte);
    	
    	apunte = new ApunteDTO();
    	apunte.setConcepto("C. OVI PRUEBA");
    	apunte.setFechaoperacion("04/03/2014");
    	apunte.setFechavalor("04/03/2014");
    	apunte.setImporte("-333.00 MXN");
    	apunte.setOfterminal("12012130");
    	apunte.setSaldo("8.00 MXN");
    	apunte.setSigno("D");
    	lstApuntes.add(apunte);
    	
    	apunte = new ApunteDTO();
    	apunte.setConcepto("C. OVI PRUEBA VALIDAR DECIMALES");
    	apunte.setFechaoperacion("04/03/2014");
    	apunte.setFechavalor("04/03/2014");
    	apunte.setImporte("-67,543.00 MXN");
    	apunte.setOfterminal("12012130");
    	apunte.setSaldo("9.00 MXN");
    	apunte.setSigno("H");
    	lstApuntes.add(apunte);
    	
    	apunte = new ApunteDTO();
    	apunte.setConcepto("PRUEBA FOLIO");
    	apunte.setFechaoperacion("04/03/2014");
    	apunte.setFechavalor("04/03/2014");
    	apunte.setImporte("-1.00 MXN");
    	apunte.setOfterminal("12012130");
    	apunte.setSaldo("10.00 MXN");
    	apunte.setSigno("D");
    	lstApuntes.add(apunte);
    	
    	apunte = new ApunteDTO();
    	apunte.setConcepto("C. OVI PRUEBA");
    	apunte.setFechaoperacion("04/03/2014");
    	apunte.setFechavalor("04/03/2014");
    	apunte.setImporte("-333.00 MXN");
    	apunte.setOfterminal("12012130");
    	apunte.setSaldo("11.00 MXN");
    	apunte.setSigno("D");
    	lstApuntes.add(apunte);
    	
    	apunte = new ApunteDTO();
    	apunte.setConcepto("C. OVI PRUEBA VALIDAR DECIMALES");
    	apunte.setFechaoperacion("04/03/2014");
    	apunte.setFechavalor("04/03/2014");
    	apunte.setImporte("-67,543.00 MXN");
    	apunte.setOfterminal("12012130");
    	apunte.setSaldo("12.00 MXN");
    	apunte.setSigno("H");
    	lstApuntes.add(apunte);
    	
    	detalles.setApuntes(lstApuntes);
    	detalles.setBloqueos(lstBloqueos);
    	detalles.setRetenciones(lstRetenciones);
        return detalles;
    }

    //Funcion para llenar la tabla de anotaciones con datos de prueba
   public ArrayList<GralAnotacionDTO> SetConsultaAnotaciones(ArrayList<GralAnotacionDTO> lstAnotaciones) {
    	
	   ArrayList<GralAnotacionDTO> nuevaLista = new ArrayList<GralAnotacionDTO>();
    	for (GralAnotacionDTO datoAnotacion : lstAnotaciones) {
    		GralAnotacionDTO nuevoObj= new GralAnotacionDTO();
    		nuevoObj = datoAnotacion;
    		nuevoObj.setAnotacion(datoAnotacion.getAnotacion()!=null||datoAnotacion.getAnotacion()!=""?""+Integer.parseInt(datoAnotacion.getAnotacion()):"");
    		switch(nuevoObj.getCodAnt()) {
	    		case "1":{
	    			nuevoObj.setTipoAnotacion("INFORMATIVA");
	    		}break;
	    		case "2":{
	    			nuevoObj.setTipoAnotacion("ALERTA");
	    		}break;
	    		case "3":{
	    			nuevoObj.setTipoAnotacion("IMPERATIVA");
	    		}break;
	    		case "4":{
	    			nuevoObj.setTipoAnotacion("AVISO");
	    		}break;
    		}
    		switch(nuevoObj.getPrioridad()) {
	    		case "1":{
	    			nuevoObj.setPrioridad("BAJA");
	    		}break;
	    		case "2":{
	    			nuevoObj.setPrioridad("MEDIA");
	    		}break;
	    		case "3":{
	    			nuevoObj.setPrioridad("ALTA");
	    		}break;
    		}
    		
    		nuevaLista.add(nuevoObj);
    	}
    	
    	return nuevaLista;
    }

    //Funcion para llenar la tabla de retenciones con los datos de la consulta
	public List<GralRetencionDTO> setConsultaRetenciones(List<GralRetencionDTO> lstRetenciones) {
		List<GralRetencionDTO> nuevaLista = new ArrayList<GralRetencionDTO>();
    	for (GralRetencionDTO datoRetencion : lstRetenciones) {
    		GralRetencionDTO nuevoObj = new GralRetencionDTO();
    		nuevoObj.setTipo(datoRetencion.getTipo()==null?"":datoRetencion.getTipo());
    		nuevoObj.setEstado("ACTIVO");
    		try {
				String nueva_fecha=datoRetencion.getFechaAlta().substring(6, 8)+"/"+datoRetencion.getFechaAlta().substring(4, 6)+"/"+datoRetencion.getFechaAlta().substring(0, 4);
				nuevoObj.setFechaAlta(nueva_fecha);
			} catch (Exception e) {
				nuevoObj.setFechaAlta("");
				e.printStackTrace();
			}
    		try {
				String nueva_fecha=datoRetencion.getFechaVTO().substring(6, 8)+"/"+datoRetencion.getFechaVTO().substring(4, 6)+"/"+datoRetencion.getFechaVTO().substring(0, 4);
				nuevoObj.setFechaVTO(nueva_fecha);
			} catch (Exception e) {
				nuevoObj.setFechaVTO("");
				e.printStackTrace();
			}
    		nuevoObj.setConcepto(datoRetencion.getConcepto()==null?"":datoRetencion.getConcepto());
    		nuevoObj.setEmpleado(datoRetencion.getEmpleado()==null?"":datoRetencion.getEmpleado());
    		nuevoObj.setOrigen(datoRetencion.getOrigen()==null?"":datoRetencion.getOrigen());
    		try {
    			nuevoObj.setImporte(datoRetencion.getImporte()==null?"":""+df.format(Double.parseDouble(datoRetencion.getImporte())));    			
    		}
    		catch(Exception e) {
    			nuevoObj.setImporte("");
				e.printStackTrace();
    		}
    		nuevaLista.add(nuevoObj);
    	}
    	
    	
    	return nuevaLista;
	}

	public List<ApunteDTO> setConsultaApuntes(ArrayList<GralApunteDTO> lstApuntes) {
		List<ApunteDTO> nuevaLista = new ArrayList<ApunteDTO>();
    	for (GralApunteDTO datoApunte : lstApuntes) {
    		ApunteDTO nuevoObj = new ApunteDTO();
    		nuevoObj.setConcepto(datoApunte.getConcepto()==null?"":datoApunte.getConcepto());
    		nuevoObj.setFechaoperacion(datoApunte.getFechaOperacion()==null?"":datoApunte.getFechaOperacion());
    		nuevoObj.setFechavalor(datoApunte.getFechaValor()==null?"":datoApunte.getFechaValor());
    		nuevoObj.setOfterminal(datoApunte.getOfiTerminal()==null?"":datoApunte.getOfiTerminal());
    		nuevoObj.setSigno(datoApunte.getSigno()==null?"":datoApunte.getSigno());
    		
    		try {
    			nuevoObj.setImporte(datoApunte.getImporte()==null?"":""+df.format(Double.parseDouble(datoApunte.getImporte())));    			
    		}
    		catch(Exception e) {
    			nuevoObj.setImporte("");
				e.printStackTrace();
    		}
    		try {
    			nuevoObj.setSaldo(datoApunte.getSaldo()==null?"":""+df.format(Double.parseDouble(datoApunte.getSaldo())));    			
    		}
    		catch(Exception e) {
    			nuevoObj.setImporte("");
				e.printStackTrace();
    		}
    		
    		nuevoObj.setCodigoerror(datoApunte.getCodigoError()==null?"":datoApunte.getCodigoError());
    		nuevoObj.setDescripcionerror(datoApunte.getDescripcionError()==null?"":datoApunte.getDescripcionError());
    		nuevoObj.setDetalle(datoApunte.getDetalle()==null?"":datoApunte.getDetalle());
    		nuevoObj.setCodcuenta(datoApunte.getCodcuenta()==null?"":datoApunte.getCodcuenta());
    		nuevoObj.setCodorigen(datoApunte.getCodorigen()==null?"":datoApunte.getCodorigen());
    		nuevoObj.setCodapunte(datoApunte.getCodapunte()==null?"":datoApunte.getCodapunte());
    		nuevoObj.setIdorigen(datoApunte.getIdOrigen()==null?"":datoApunte.getIdOrigen());
    		
    		nuevaLista.add(nuevoObj);
    	}
    	
    	
    	return nuevaLista;
	}
}
