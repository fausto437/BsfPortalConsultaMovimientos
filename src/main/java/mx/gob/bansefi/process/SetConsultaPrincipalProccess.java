package mx.gob.bansefi.process;

import mx.gob.bansefi.dto.ConsultaPrincipalDTO;
import mx.gob.bansefi.dto.GralBloqueoDTO;
import mx.gob.bansefi.dto.GralApunteDTO;
import mx.gob.bansefi.dto.GralRetencionDTO;
import mx.gob.bansefi.dto.Modelos.AnotacionesDTO;
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
public class SetConsultaPrincipalProccess {
    @Value("${url.context}")
    private String urlcontext;
    @Value("${url.getLocalidad}")
    private String urlgetLocalidad;

    private DecimalFormat df = new DecimalFormat("0.00");
    
    public List<GralBloqueoDTO> SetConsultaBloqueos(List<GralBloqueoDTO> lstBloqueos){
    	List<GralBloqueoDTO> nuevaLista = new ArrayList<GralBloqueoDTO>();
    	for (GralBloqueoDTO datoBloqueo : lstBloqueos) {
    		GralBloqueoDTO nuevoObj = new GralBloqueoDTO();
    		nuevoObj.setCentro(datoBloqueo.getCentro()==null?"":datoBloqueo.getCentro());
    		nuevoObj.setConcepto(datoBloqueo.getConcepto()==null?"":datoBloqueo.getConcepto());
    		nuevoObj.setEmpleado(datoBloqueo.getEmpleado()==null?"":datoBloqueo.getEmpleado());
    		nuevoObj.setFechaAlta(datoBloqueo.getFechaAlta()==null?"":datoBloqueo.getFechaAlta());
    		try {
				String nueva_fecha=datoBloqueo.getFechaVTO().substring(6, 8)+"/"+datoBloqueo.getFechaVTO().substring(4, 6)+"/"+datoBloqueo.getFechaVTO().substring(0, 4);
				nuevoObj.setFechaVTO(nueva_fecha);
			} catch (Exception e) {
				nuevoObj.setFechaVTO("");
				e.printStackTrace();
			}
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
    	retencion.setFecAlta("12/05/2008");
    	retencion.setFecVto("12/05/2008");
    	retencion.setConcepto("POR QUE NO 123456 BANAEX");
    	retencion.setEmpleado("E172379129");
    	retencion.setOrigen("0166-0069644029-15/07/2014-0000001");
    	retencion.setImporte("10.00");
    	lstRetenciones.add(retencion);
    	
    	retencion = new GralRetencionDTO();
    	retencion.setTipo("Q7");
    	retencion.setTipo("ACTIVO");
    	retencion.setFecAlta("12/05/2008");
    	retencion.setFecVto("12/05/2008");
    	retencion.setConcepto("POR QUE NO 123456 BANAEX");
    	retencion.setEmpleado("E172379129");
    	retencion.setOrigen("0166-0069644029-15/07/2014-0000001");
    	retencion.setImporte("10.00");
    	lstRetenciones.add(retencion);
    	
    	retencion = new GralRetencionDTO();
    	retencion.setTipo("Q7");
    	retencion.setTipo("ACTIVO");
    	retencion.setFecAlta("12/05/2008");
    	retencion.setFecVto("12/05/2008");
    	retencion.setConcepto("POR QUE NO 123456 BANAEX");
    	retencion.setEmpleado("E172379129");
    	retencion.setOrigen("0166-0069644029-15/07/2014-0000001");
    	retencion.setImporte("10.00");
    	lstRetenciones.add(retencion);
    	
    	//ACUERDOS
    	List<GralApunteDTO> lstAcuerdos = new ArrayList<GralApunteDTO>();
    	GralApunteDTO acuerdo = new GralApunteDTO();
    	acuerdo.setConcepto("PRUEBA FOLIO");
    	acuerdo.setFecOperacion("04/03/2014");
    	acuerdo.setFecValor("04/03/2014");
    	acuerdo.setImporte("-1.00 MXN");
    	acuerdo.setOfTerminal("12012130");
    	acuerdo.setSaldo("1.00 MXN");
    	acuerdo.setSigno("D");
    	lstAcuerdos.add(acuerdo);
    	
    	acuerdo = new GralApunteDTO();
    	acuerdo.setConcepto("C. OVI PRUEBA");
    	acuerdo.setFecOperacion("04/03/2014");
    	acuerdo.setFecValor("04/03/2014");
    	acuerdo.setImporte("-333.00 MXN");
    	acuerdo.setOfTerminal("12012130");
    	acuerdo.setSaldo("2.00 MXN");
    	acuerdo.setSigno("D");
    	lstAcuerdos.add(acuerdo);
    	
    	acuerdo = new GralApunteDTO();
    	acuerdo.setConcepto("C. OVI PRUEBA VALIDAR DECIMALES");
    	acuerdo.setFecOperacion("04/03/2014");
    	acuerdo.setFecValor("04/03/2014");
    	acuerdo.setImporte("-67,543.00 MXN");
    	acuerdo.setOfTerminal("12012130");
    	acuerdo.setSaldo("3.00 MXN");
    	acuerdo.setSigno("H");
    	lstAcuerdos.add(acuerdo);
    	
    	acuerdo = new GralApunteDTO();
    	acuerdo.setConcepto("PRUEBA FOLIO");
    	acuerdo.setFecOperacion("04/03/2014");
    	acuerdo.setFecValor("04/03/2014");
    	acuerdo.setImporte("-1.00 MXN");
    	acuerdo.setOfTerminal("12012130");
    	acuerdo.setSaldo("4.00 MXN");
    	acuerdo.setSigno("D");
    	lstAcuerdos.add(acuerdo);
    	
    	acuerdo = new GralApunteDTO();
    	acuerdo.setConcepto("C. OVI PRUEBA");
    	acuerdo.setFecOperacion("04/03/2014");
    	acuerdo.setFecValor("04/03/2014");
    	acuerdo.setImporte("-333.00 MXN");
    	acuerdo.setOfTerminal("12012130");
    	acuerdo.setSaldo("5.00 MXN");
    	acuerdo.setSigno("D");
    	lstAcuerdos.add(acuerdo);
    	
    	acuerdo = new GralApunteDTO();
    	acuerdo.setConcepto("C. OVI PRUEBA VALIDAR DECIMALES");
    	acuerdo.setFecOperacion("04/03/2014");
    	acuerdo.setFecValor("04/03/2014");
    	acuerdo.setImporte("-67,543.00 MXN");
    	acuerdo.setOfTerminal("12012130");
    	acuerdo.setSaldo("6.00 MXN");
    	acuerdo.setSigno("H");
    	lstAcuerdos.add(acuerdo);
    	
    	acuerdo = new GralApunteDTO();
    	acuerdo.setConcepto("PRUEBA FOLIO");
    	acuerdo.setFecOperacion("04/03/2014");
    	acuerdo.setFecValor("04/03/2014");
    	acuerdo.setImporte("-1.00 MXN");
    	acuerdo.setOfTerminal("12012130");
    	acuerdo.setSaldo("7.00 MXN");
    	acuerdo.setSigno("D");
    	lstAcuerdos.add(acuerdo);
    	
    	acuerdo = new GralApunteDTO();
    	acuerdo.setConcepto("C. OVI PRUEBA");
    	acuerdo.setFecOperacion("04/03/2014");
    	acuerdo.setFecValor("04/03/2014");
    	acuerdo.setImporte("-333.00 MXN");
    	acuerdo.setOfTerminal("12012130");
    	acuerdo.setSaldo("8.00 MXN");
    	acuerdo.setSigno("D");
    	lstAcuerdos.add(acuerdo);
    	
    	acuerdo = new GralApunteDTO();
    	acuerdo.setConcepto("C. OVI PRUEBA VALIDAR DECIMALES");
    	acuerdo.setFecOperacion("04/03/2014");
    	acuerdo.setFecValor("04/03/2014");
    	acuerdo.setImporte("-67,543.00 MXN");
    	acuerdo.setOfTerminal("12012130");
    	acuerdo.setSaldo("9.00 MXN");
    	acuerdo.setSigno("H");
    	lstAcuerdos.add(acuerdo);
    	
    	acuerdo = new GralApunteDTO();
    	acuerdo.setConcepto("PRUEBA FOLIO");
    	acuerdo.setFecOperacion("04/03/2014");
    	acuerdo.setFecValor("04/03/2014");
    	acuerdo.setImporte("-1.00 MXN");
    	acuerdo.setOfTerminal("12012130");
    	acuerdo.setSaldo("10.00 MXN");
    	acuerdo.setSigno("D");
    	lstAcuerdos.add(acuerdo);
    	
    	acuerdo = new GralApunteDTO();
    	acuerdo.setConcepto("C. OVI PRUEBA");
    	acuerdo.setFecOperacion("04/03/2014");
    	acuerdo.setFecValor("04/03/2014");
    	acuerdo.setImporte("-333.00 MXN");
    	acuerdo.setOfTerminal("12012130");
    	acuerdo.setSaldo("11.00 MXN");
    	acuerdo.setSigno("D");
    	lstAcuerdos.add(acuerdo);
    	
    	acuerdo = new GralApunteDTO();
    	acuerdo.setConcepto("C. OVI PRUEBA VALIDAR DECIMALES");
    	acuerdo.setFecOperacion("04/03/2014");
    	acuerdo.setFecValor("04/03/2014");
    	acuerdo.setImporte("-67,543.00 MXN");
    	acuerdo.setOfTerminal("12012130");
    	acuerdo.setSaldo("12.00 MXN");
    	acuerdo.setSigno("H");
    	lstAcuerdos.add(acuerdo);
    	
    	detalles.setApuntes(lstAcuerdos);
    	detalles.setBloqueos(lstBloqueos);
    	detalles.setRetenciones(lstRetenciones);
        return detalles;
    }
    
    public ArrayList<AnotacionesDTO> SetConsultaAnotaciones() {
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
    	
    	return anotaciones;
    }
}
