package mx.gob.bansefi.process;

import mx.gob.bansefi.dto.ConsultaPrincipalDTO;
import mx.gob.bansefi.dto.DetalleConsultaDTO;
import mx.gob.bansefi.dto.GralApunteDTO;
import mx.gob.bansefi.dto.GralBloqueoDTO;
import mx.gob.bansefi.dto.GralRetencionDTO;
import mx.gob.bansefi.dto.LiquidacionDTO;
import mx.gob.bansefi.dto.SituacionApunteDTO;
import mx.gob.bansefi.dto.Response.ResConsultaAnotacionDetalleDTO;
import mx.gob.bansefi.dto.Response.ResConsultaApunteDetalleDTO;
import mx.gob.bansefi.dto.Response.ResConsultaAuditoriaDTO;
import mx.gob.bansefi.dto.Response.ResConsultaAuditoriaDetalleDTO;
import mx.gob.bansefi.dto.Response.ResConsultaEmisionDTO;
import mx.gob.bansefi.dto.Response.ResConsultaLiquidacionDTO;
import mx.gob.bansefi.services.WsServicios;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
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
    
    private DecimalFormat df = new DecimalFormat("0.00");
    public DetalleConsultaDTO SetConsultaDetallesApunte(ResConsultaApunteDetalleDTO detalleApunte) {
    	DetalleConsultaDTO detalles = new DetalleConsultaDTO();
    	
    	detalles.setTitulo("de Apunte");
		detalles.setTitular(detalleApunte.getNombre());
		detalles.setProdVendible(detalleApunte.getProducto());
		detalles.setTipoCuenta(detalleApunte.getTipoDescripcion());
		detalles.setNumero(detalleApunte.getNumero());
		detalles.setFechaContable(detalleApunte.getFechaContable()==null?"":detalleApunte.getFechaContable());
		detalles.setFechaValor(detalleApunte.getFechaValor()==null?"":detalleApunte.getFechaValor());
		detalles.setMoneda(detalleApunte.getMoneda());
		detalles.setTipoOperacion(detalleApunte.getTipoOperacion());
		detalles.setConcepto(detalleApunte.getConcepto());
		detalles.setConceptoCorto(detalleApunte.getTexto()==null?"":detalleApunte.getTexto());
		try {
			detalles.setImporte(""+df.parse(detalleApunte.getImporte()));
		} catch (ParseException e) {
			detalles.setImporte("");
			e.printStackTrace();
		}
		
    	
		List<SituacionApunteDTO> lstSitApunte = new ArrayList<SituacionApunteDTO>();
		SituacionApunteDTO sitApunte = new SituacionApunteDTO();
		if(detalleApunte.getIndicador1()!=null) {
			sitApunte.setIndicador(detalleApunte.getIndicador_texto1());
			sitApunte.setSituacion(detalleApunte.getIndicador_valor1());
			lstSitApunte.add(sitApunte);
		}
		if(detalleApunte.getIndicador2()!=null) {
			sitApunte = new SituacionApunteDTO();
			sitApunte.setIndicador(detalleApunte.getIndicador_texto2());
			sitApunte.setSituacion(detalleApunte.getIndicador_valor2());
			lstSitApunte.add(sitApunte);
		}
		if(detalleApunte.getIndicador3()!=null) {
			sitApunte = new SituacionApunteDTO();
			sitApunte.setIndicador(detalleApunte.getIndicador_texto3());
			sitApunte.setSituacion(detalleApunte.getIndicador_valor3());
			lstSitApunte.add(sitApunte);
		}
		if(detalleApunte.getIndicador4()!=null) {
			sitApunte = new SituacionApunteDTO();
			sitApunte.setIndicador(detalleApunte.getIndicador_texto4());
			sitApunte.setSituacion(detalleApunte.getIndicador_valor4());
			lstSitApunte.add(sitApunte);
		}
		if(detalleApunte.getIndicador5()!=null) {
			sitApunte = new SituacionApunteDTO();
			sitApunte.setIndicador(detalleApunte.getIndicador_texto5());
			sitApunte.setSituacion(detalleApunte.getIndicador_valor5());
			lstSitApunte.add(sitApunte);
		}
		if(detalleApunte.getIndicador6()!=null) {
			sitApunte = new SituacionApunteDTO();
			sitApunte.setIndicador(detalleApunte.getIndicador_texto6());
			sitApunte.setSituacion(detalleApunte.getIndicador_valor6());
			lstSitApunte.add(sitApunte);
		}
		if(detalleApunte.getIndicador7()!=null) {
			sitApunte = new SituacionApunteDTO();
			sitApunte.setIndicador(detalleApunte.getIndicador_texto7());
			sitApunte.setSituacion(detalleApunte.getIndicador_valor7());
			lstSitApunte.add(sitApunte);
		}
		if(detalleApunte.getIndicador8()!=null) {
			sitApunte = new SituacionApunteDTO();
			sitApunte.setIndicador(detalleApunte.getIndicador_texto8());
			sitApunte.setSituacion(detalleApunte.getIndicador_valor8());
			lstSitApunte.add(sitApunte);
		}
		if(detalleApunte.getIndicador9()!=null) {
			sitApunte = new SituacionApunteDTO();
			sitApunte.setIndicador(detalleApunte.getIndicador_texto9());
			sitApunte.setSituacion(detalleApunte.getIndicador_valor9());
			lstSitApunte.add(sitApunte);
		}
		if(detalleApunte.getIndicador10()!=null) {
			sitApunte = new SituacionApunteDTO();
			sitApunte.setIndicador(detalleApunte.getIndicador_texto10());
			sitApunte.setSituacion(detalleApunte.getIndicador_valor10());
			lstSitApunte.add(sitApunte);
		}
		
		
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
		detalles.setFechaVto(renglonRetencion.getFechaVTO().equals("00/00/0000")?"":renglonRetencion.getFechaVTO());
		detalles.setMotivo(renglonRetencion.getConcepto());
		detalles.setSituacion(renglonRetencion.getEstado());
		detalles.setImporte(renglonRetencion.getImporte());
		detalles.setOrigen(renglonRetencion.getOrigen());
		detalles.setMoneda("MXN");
		try {
			String centro = renglonRetencion.getOrigen().substring(0,4);
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
		detalles.setFechaVto(renglonBloqueo.getFechaVTO().equals("00/00/0000")?"":renglonBloqueo.getFechaVTO());
		detalles.setCodEmpleado(renglonBloqueo.getEmpleado());
		detalles.setCentro(renglonBloqueo.getCentro());
		detalles.setImporte(renglonBloqueo.getImporte());
		
		return detalles;
	}
	
	public DetalleConsultaDTO SetAnotacionDetalles(ResConsultaAnotacionDetalleDTO res, String codTipo) {
		DetalleConsultaDTO detalles = new DetalleConsultaDTO();
		detalles.setTitulo("Detalle de Anotación");
		detalles.setCodEmpleado(res.getIdEmpleado());
		detalles.setNombreEmpleado(res.getNombreEmpleado().equals("no hay")?"":res.getNombreEmpleado());
		detalles.setFecha(res.getFechaUltimaModificacion());
		switch(codTipo) {
			case "1":{
				detalles.setTipo("INFORMATIVA");
			}break;
			case "2":{
				detalles.setTipo("ALERTA");
			}break;
			case "3":{
				detalles.setTipo("IMPERATIVA");
			}break;
			case "4":{
				detalles.setTipo("AVISO");
			}break;
		}
		return detalles;
	}

	public DetalleConsultaDTO SetConsultaAuditoria(ResConsultaAuditoriaDTO res) {
		DetalleConsultaDTO detalles = new DetalleConsultaDTO();
		detalles.setTitulo("Básica de auditoría");
		detalles.setTipoDetalle("au");
		detalles.setOrigen(res.getAuditorias().get(0).getEmpleado());
		detalles.setTerminal(res.getAuditorias().get(0).getTerminal());
		detalles.setFechaContable(res.getAuditorias().get(0).getFechaContable());
		detalles.setFechaOperacion(res.getAuditorias().get(0).getFechaOperacion());
		detalles.setCodAuditoria(res.getAuditorias().get(0).getCodigo());
		detalles.setCodCentro(res.getAuditorias().get(0).getCentro());
		detalles.setDescCentroActual(res.getAuditorias().get(0).getNombreSucursal());
		detalles.setCodEmpleado(res.getAuditorias().get(0).getEmpleado());
		detalles.setNombreEmpleado(res.getAuditorias().get(0).getNombreEmpleado());
		detalles.setDescCentro(res.getAuditorias().get(0).getNombreCentro());
		detalles.setAutorizador(res.getAuditorias().get(0).getAutorizador());
		detalles.setSituacion(SetSituacion(res.getAuditorias().get(0).getEstado()));
		try {
			String nueva_hora=res.getAuditorias().get(0).getHoraOperacion().substring(0, 2)+":"+res.getAuditorias().get(0).getHoraOperacion().substring(2, 4)+":"+res.getAuditorias().get(0).getHoraOperacion().substring(4, 6);
			detalles.setHoraOperacion(nueva_hora);
		} catch (Exception e) {
			detalles.setHoraOperacion("");
			e.printStackTrace();
		}
		
		return detalles;
	}

	public DetalleConsultaDTO SetConsultaAuditoriaDetalles(ResConsultaAuditoriaDetalleDTO res) {
		DetalleConsultaDTO detalles = new DetalleConsultaDTO();
		detalles.setTitulo("de Auditoría");
		detalles.setTipoDetalle("dAu");
		detalles.setNombreEmpleado(res.getNombreEmpleado());
		detalles.setNombreAutorizador(res.getNombreAutorizador());
		detalles.setCodCentro(res.getCodigoCentro());
		detalles.setNombEnt(res.getNombEnt());
		
		return detalles;
	}

	public DetalleConsultaDTO SetConsultaLiquidacion(ResConsultaLiquidacionDTO res) {
		DetalleConsultaDTO detalles = new DetalleConsultaDTO();
		detalles.setTipoDetalle("li");
		detalles.setTitulo("de liquidación");
		detalles.setFechaLiquidacion(res.getFechaLiquidacion());
		detalles.setNumAcuerdo(res.getAcuerdo());
		detalles.setFechaDesde(res.getFechaDesde());
		detalles.setFechaHasta(res.getFechaHasta());
		detalles.setSituacion(res.getSituacion());
		detalles.setFechaUltimoCobro(res.getFechaUltimoCobro());
		detalles.setCodigoOperacionLiquidacion(res.getCodigoOperacionLiquidacion());
		detalles.setMoneda(res.getMoneda());
		
		try {
			detalles.setImporteTotal(""+df.parse(res.getImporteTotal()));
		} catch (ParseException e) {
			detalles.setImporteTotal("");
			e.printStackTrace();
		}
		try {
			detalles.setImportePendiente(""+df.parse(res.getImportePendiente()));
		} catch (ParseException e) {
			detalles.setImportePendiente("");
			e.printStackTrace();
		}
		ArrayList<LiquidacionDTO> nuevaLista = new ArrayList<LiquidacionDTO>();
		for(LiquidacionDTO liq :res.getLiquidaciones()) {
			LiquidacionDTO nuevaLiq= new LiquidacionDTO();
			try {
				nuevaLiq.setImporteFacturado(""+df.parse(liq.getImporteFacturado()));
			} catch (ParseException e) {
				nuevaLiq.setImporteFacturado("");
				e.printStackTrace();
			}
			try {
				nuevaLiq.setImportePendiente(""+df.parse(liq.getImportePendiente()));
			} catch (ParseException e) {
				nuevaLiq.setImportePendiente("");
				e.printStackTrace();
			}
			try {
				nuevaLiq.setImporteCondonado(""+df.parse(liq.getImporteCondonado()));
			} catch (ParseException e) {
				nuevaLiq.setImporteCondonado("");
				e.printStackTrace();
			}
			try {
				nuevaLiq.setImporteAjustado(""+df.parse(liq.getImporteAjustado()));
			} catch (ParseException e) {
				nuevaLiq.setImporteAjustado("");
				e.printStackTrace();
			}
			nuevaLiq.setCodigoOrigen(liq.getCodigoOrigen());
			nuevaLista.add(nuevaLiq);
		}
		detalles.setLiquidaciones(nuevaLista);
		return detalles;
	}

	public DetalleConsultaDTO SetConsultaEmision(ResConsultaEmisionDTO res) {
		DetalleConsultaDTO detalles = new DetalleConsultaDTO();
		detalles.setTipoDetalle("em");
		detalles.setTitulo("Emisión de cheque");
		detalles.setTipoTalonario(res.getTipoTalonario());
		detalles.setNumeroTalonario(res.getNumeroTalonario());
		detalles.setNombre(res.getNombre());
		detalles.setNumeroCheque(res.getNumeroCheque());
		detalles.setCodigoCaja(res.getCodigoCaja());
		detalles.setConforma(res.getConforma());
		detalles.setDisposicion(res.getDisposicion());
		try {
			detalles.setPagoCheque(df.format(Double.parseDouble(res.getPagoCheque())));
		} catch (Exception e) {
			detalles.setPagoCheque("");
			e.printStackTrace();
		}
		try {
			detalles.setPagoPendiente(""+df.format(Double.parseDouble(res.getPagoPendiente())));
		} catch (Exception e) {
			detalles.setPagoPendiente("");
			e.printStackTrace();
		}
		try {
			String nueva_fecha=res.getFechaVencimiento().substring(6, 8)+"/"+res.getFechaVencimiento().substring(4, 6)+"/"+res.getFechaVencimiento().substring(0, 4);
			detalles.setFechaVto(nueva_fecha);
		} catch (Exception e) {
			detalles.setFechaVto("");
			e.printStackTrace();
		}
		detalles.setCentro(res.getCentro());
		detalles.setNumAcuerdo(res.getAcuerdo());
		detalles.setEstado(res.getEstado());
		detalles.setSituacionPago(res.getSituacionPago());	
		return detalles;
	}
	
	public String SetSituacion(String codSituacion) {
		String situacion="";
		switch(codSituacion) {
		case "A":{
			situacion= "ACTIVO";
		}break;
		case "I":{
			situacion= "INACTIVO";
		}break;
		case "V":{
			situacion= "VENCIDO";
		}break;
		case "C":{
			situacion= "CANCELADO";
		}break;
		default:{
			situacion= "";
		}break;
	}
	return situacion;
	}
}
