package mx.gob.bansefi.process;

import mx.gob.bansefi.dto.ConsultaPrincipalDTO;
import mx.gob.bansefi.dto.GralBloqueoDTO;
import mx.gob.bansefi.dto.GralMovimientoDTO;

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
    public ConsultaPrincipalDTO SetConsultaPrincipal() {
    	ConsultaPrincipalDTO detalles = new ConsultaPrincipalDTO();
    	
    	//BLOQUEOS
    	List<GralBloqueoDTO> lstBloqueos = new ArrayList<GralBloqueoDTO>();
    	GralBloqueoDTO bloqueo = new GralBloqueoDTO();
    	bloqueo.setTipo("Q7");
    	bloqueo.setEstado("ACTIVO");
    	bloqueo.setFecAlta("12/05/2008");
    	bloqueo.setFecVto("");
    	bloqueo.setMotivo("");
    	bloqueo.setEmpleado("E1800160");
    	bloqueo.setCentro("5028");
    	bloqueo.setImporte("");
    	lstBloqueos.add(bloqueo);
    	
    	bloqueo = new GralBloqueoDTO();
    	bloqueo.setTipo("Q7");
    	bloqueo.setEstado("ACTIVO");
    	bloqueo.setFecAlta("12/05/2008");
    	bloqueo.setFecVto("");
    	bloqueo.setMotivo("");
    	bloqueo.setEmpleado("E1800160");
    	bloqueo.setCentro("5028");
    	bloqueo.setImporte("");
    	lstBloqueos.add(bloqueo);
    	
    	bloqueo = new GralBloqueoDTO();
    	bloqueo.setTipo("Q7");
    	bloqueo.setEstado("ACTIVO");
    	bloqueo.setFecAlta("12/05/2008");
    	bloqueo.setFecVto("");
    	bloqueo.setMotivo("");
    	bloqueo.setEmpleado("E1800160");
    	bloqueo.setCentro("5028");
    	bloqueo.setImporte("");
    	lstBloqueos.add(bloqueo);
    	
    	bloqueo = new GralBloqueoDTO();
    	bloqueo.setTipo("Q7");
    	bloqueo.setEstado("ACTIVO");
    	bloqueo.setFecAlta("12/05/2008");
    	bloqueo.setFecVto("");
    	bloqueo.setMotivo("");
    	bloqueo.setEmpleado("E1800160");
    	bloqueo.setCentro("5028");
    	bloqueo.setImporte("");
    	lstBloqueos.add(bloqueo);
    	
    	detalles.setBloqueos(lstBloqueos);
    	
    	//ACUERDOS
    	List<GralMovimientoDTO> lstAcuerdos = new ArrayList<GralMovimientoDTO>();
    	GralMovimientoDTO acuerdo = new GralMovimientoDTO();
    	acuerdo.setConcepto("PRUEBA FOLIO");
    	acuerdo.setFecOperacion("04/03/2014");
    	acuerdo.setFecValor("04/03/2014");
    	acuerdo.setImporte("-1.00 MXN");
    	acuerdo.setOfTerminal("12012130");
    	acuerdo.setSaldo("1.00 MXN");
    	acuerdo.setSigno("D");
    	lstAcuerdos.add(acuerdo);
    	
    	acuerdo = new GralMovimientoDTO();
    	acuerdo.setConcepto("C. OVI PRUEBA");
    	acuerdo.setFecOperacion("04/03/2014");
    	acuerdo.setFecValor("04/03/2014");
    	acuerdo.setImporte("-333.00 MXN");
    	acuerdo.setOfTerminal("12012130");
    	acuerdo.setSaldo("2.00 MXN");
    	acuerdo.setSigno("D");
    	lstAcuerdos.add(acuerdo);
    	
    	acuerdo = new GralMovimientoDTO();
    	acuerdo.setConcepto("C. OVI PRUEBA VALIDAR DECIMALES");
    	acuerdo.setFecOperacion("04/03/2014");
    	acuerdo.setFecValor("04/03/2014");
    	acuerdo.setImporte("-67,543.00 MXN");
    	acuerdo.setOfTerminal("12012130");
    	acuerdo.setSaldo("3.00 MXN");
    	acuerdo.setSigno("H");
    	lstAcuerdos.add(acuerdo);
    	
    	acuerdo = new GralMovimientoDTO();
    	acuerdo.setConcepto("PRUEBA FOLIO");
    	acuerdo.setFecOperacion("04/03/2014");
    	acuerdo.setFecValor("04/03/2014");
    	acuerdo.setImporte("-1.00 MXN");
    	acuerdo.setOfTerminal("12012130");
    	acuerdo.setSaldo("4.00 MXN");
    	acuerdo.setSigno("D");
    	lstAcuerdos.add(acuerdo);
    	
    	acuerdo = new GralMovimientoDTO();
    	acuerdo.setConcepto("C. OVI PRUEBA");
    	acuerdo.setFecOperacion("04/03/2014");
    	acuerdo.setFecValor("04/03/2014");
    	acuerdo.setImporte("-333.00 MXN");
    	acuerdo.setOfTerminal("12012130");
    	acuerdo.setSaldo("5.00 MXN");
    	acuerdo.setSigno("D");
    	lstAcuerdos.add(acuerdo);
    	
    	acuerdo = new GralMovimientoDTO();
    	acuerdo.setConcepto("C. OVI PRUEBA VALIDAR DECIMALES");
    	acuerdo.setFecOperacion("04/03/2014");
    	acuerdo.setFecValor("04/03/2014");
    	acuerdo.setImporte("-67,543.00 MXN");
    	acuerdo.setOfTerminal("12012130");
    	acuerdo.setSaldo("6.00 MXN");
    	acuerdo.setSigno("H");
    	lstAcuerdos.add(acuerdo);
    	
    	acuerdo = new GralMovimientoDTO();
    	acuerdo.setConcepto("PRUEBA FOLIO");
    	acuerdo.setFecOperacion("04/03/2014");
    	acuerdo.setFecValor("04/03/2014");
    	acuerdo.setImporte("-1.00 MXN");
    	acuerdo.setOfTerminal("12012130");
    	acuerdo.setSaldo("7.00 MXN");
    	acuerdo.setSigno("D");
    	lstAcuerdos.add(acuerdo);
    	
    	acuerdo = new GralMovimientoDTO();
    	acuerdo.setConcepto("C. OVI PRUEBA");
    	acuerdo.setFecOperacion("04/03/2014");
    	acuerdo.setFecValor("04/03/2014");
    	acuerdo.setImporte("-333.00 MXN");
    	acuerdo.setOfTerminal("12012130");
    	acuerdo.setSaldo("8.00 MXN");
    	acuerdo.setSigno("D");
    	lstAcuerdos.add(acuerdo);
    	
    	acuerdo = new GralMovimientoDTO();
    	acuerdo.setConcepto("C. OVI PRUEBA VALIDAR DECIMALES");
    	acuerdo.setFecOperacion("04/03/2014");
    	acuerdo.setFecValor("04/03/2014");
    	acuerdo.setImporte("-67,543.00 MXN");
    	acuerdo.setOfTerminal("12012130");
    	acuerdo.setSaldo("9.00 MXN");
    	acuerdo.setSigno("H");
    	lstAcuerdos.add(acuerdo);
    	
    	acuerdo = new GralMovimientoDTO();
    	acuerdo.setConcepto("PRUEBA FOLIO");
    	acuerdo.setFecOperacion("04/03/2014");
    	acuerdo.setFecValor("04/03/2014");
    	acuerdo.setImporte("-1.00 MXN");
    	acuerdo.setOfTerminal("12012130");
    	acuerdo.setSaldo("10.00 MXN");
    	acuerdo.setSigno("D");
    	lstAcuerdos.add(acuerdo);
    	
    	acuerdo = new GralMovimientoDTO();
    	acuerdo.setConcepto("C. OVI PRUEBA");
    	acuerdo.setFecOperacion("04/03/2014");
    	acuerdo.setFecValor("04/03/2014");
    	acuerdo.setImporte("-333.00 MXN");
    	acuerdo.setOfTerminal("12012130");
    	acuerdo.setSaldo("11.00 MXN");
    	acuerdo.setSigno("D");
    	lstAcuerdos.add(acuerdo);
    	
    	acuerdo = new GralMovimientoDTO();
    	acuerdo.setConcepto("C. OVI PRUEBA VALIDAR DECIMALES");
    	acuerdo.setFecOperacion("04/03/2014");
    	acuerdo.setFecValor("04/03/2014");
    	acuerdo.setImporte("-67,543.00 MXN");
    	acuerdo.setOfTerminal("12012130");
    	acuerdo.setSaldo("12.00 MXN");
    	acuerdo.setSigno("H");
    	lstAcuerdos.add(acuerdo);
    	
    	detalles.setAcuerdos(lstAcuerdos);
        return detalles;
    }
}
