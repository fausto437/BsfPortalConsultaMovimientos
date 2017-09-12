package mx.gob.bansefi.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.json.JSONArray;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import mx.gob.bansefi.dto.ReqConsulRiego;
import mx.gob.bansefi.dto.Response.ResGralDTO;
import mx.gob.bansefi.dto.Response.ResponseAltaPM;
import mx.gob.bansefi.utils.Util;

public class WsClientRiesgo 
{
	private static final Logger log = LogManager.getLogger(WsClientRiesgo.class);
	
    public static ResGralDTO ConsultaMedioAltoRiegos(ReqConsulRiego Request, String Url)
    {
    	return null;
/*
        Util util = Util.getInstance();
        ReqAltaPersonaMoralDTO Datos=new ReqAltaPersonaMoralDTO();
        //GetConsRiesgoResponseDTO
        try
        {

            ArrayList<String> Nodos=new ArrayList<String>();
            String jsonRepuesta= util.callRestPost(Request,Url);
            EntGrupoFilial oGru   = new EntGrupoFilial();
            EntDonativos   oDonat = new EntDonativos();
            EntRelPersona oRelPer = new EntRelPersona();
            try
            {
            	
            	Nodos.add("grupoFilial");
            	oGru=(EntGrupoFilial)util.jsonToObject(oGru,jsonRepuesta,Nodos);
            	
            	Nodos=new ArrayList<String>();
            	Nodos.add("donativos");
            	oDonat=(EntDonativos)util.jsonToObject(oDonat,jsonRepuesta,Nodos);
            	
            	Nodos=new ArrayList<String>();
            	Nodos.add("relacionPersonas");
            	oRelPer=(EntRelPersona)util.jsonToObject(oRelPer,jsonRepuesta,Nodos);
            	if(oRelPer==null)
            	{
            		try
            		{
                    	JSONObject jsonSalida = new JSONObject(jsonRepuesta);
                		JSONArray listFiles = jsonSalida.getJSONObject("relacionPersonas").getJSONArray("relacionesComerciales");
    					Gson gson = new Gson();
    					List<EntRelComercial> Registros = gson.fromJson(listFiles.toString(), new TypeToken<List<EntRelComercial>>(){}.getType());
    					
    					EntRelPersona oRelPersona = new EntRelPersona();
    					oRelPersona.setRelacionesComerciales(Registros);
    					
    					
    					listFiles = jsonSalida.getJSONObject("relacionPersonas").getJSONObject("relacionesAccionistasFuncionarios").getJSONArray("pe_PERS_RL_ACC_V");
    					List<EntRelAccFunc> oAct= gson.fromJson(listFiles.toString(), new TypeToken<List<EntRelAccFunc>>(){}.getType());
    					oRelPersona.setRelacionesAccionistasFuncionarios(oAct);
    				//	Datos.setRelacionPersonas(oRelPersona);
    					
    					ArrayList<mx.gob.bansefi.dto.Request.AltaPersonaMoral.ReqRelacionDTO> oEntRelPer = new ArrayList<mx.gob.bansefi.dto.Request.AltaPersonaMoral.ReqRelacionDTO>();
    					
    					for(EntRelComercial oRelPerson :  Registros)
    					{
    						mx.gob.bansefi.dto.Request.AltaPersonaMoral.ReqRelacionDTO oEntPers = new mx.gob.bansefi.dto.Request.AltaPersonaMoral.ReqRelacionDTO();
    						oEntPers.setGiro(oRelPerson.getGiro());
    						oEntPers.setDomic(oRelPerson.getDomic_50());
    						oEntPers.setTelefono(oRelPerson.getTelefono());
    						oEntPers.setNombre(oRelPerson.getNomb_50());
    						oEntPers.setPersRl(oRelPerson.getPers_RL_TIT());
    						oEntPers.setApellido1(oRelPerson.getNum_SEC());
    						oEntRelPer.add(oEntPers);
    					}
    					for(mx.gob.bansefi.dto.AltoRiesgo.EntRelAccFunc oRelFunc :  oAct)
    					{
    						mx.gob.bansefi.dto.Request.AltaPersonaMoral.ReqRelacionDTO oEntPers = new mx.gob.bansefi.dto.Request.AltaPersonaMoral.ReqRelacionDTO();
    						oEntPers.setNombre(oRelFunc.getNomb_IN_NOMB_PILA());
    						oEntPers.setApellido1(oRelFunc.getNomb_IN_APE_1_IN());
    						oEntPers.setApellido2(oRelFunc.getNomb_IN_APE_2_IN());
    						oEntPers.setPuesto(oRelFunc.getPuesto());
    						oEntPers.setPorcentaje(oRelFunc.getPct_ACCNRIO());
    						oEntPers.setRfc(oRelFunc.getId_RFC());
    						oEntPers.setTelefono(oRelFunc.getNum_SEC());
    						oEntRelPer.add(oEntPers);
    					}
    					Datos.setReqRelacionDTOs(oEntRelPer);
    				
            		}
            		catch(Exception ex)
            		{
            			log.error("\nError en el metodo ConsultaMedioAltoRiegos " + "\nException Message: " + ex.getMessage());
            		}
            	}
            	
            	ReqAltaModPerfilTransDTO perTran = new ReqAltaModPerfilTransDTO();
            	perTran.setCodPaisNacionalidad(oGru.getCod_PAIS_NAL());
            	perTran.setPlazaOperativa1(oGru.getPe_INF_FINAN_PZA_V().getPlaza_OPER_01());
            	perTran.setPlazaOperativa2(oGru.getPe_INF_FINAN_PZA_V().getPlaza_OPER_02());
            	perTran.setPlazaOperativa3(oGru.getPe_INF_FINAN_PZA_V().getPlaza_OPER_03());
            	perTran.setPlazaOperativa4(oGru.getPe_INF_FINAN_PZA_V().getPlaza_OPER_04());
            	perTran.setPlazaOperativa5(oGru.getPe_INF_FINAN_PZA_V().getPlaza_OPER_05());
            	perTran.setPlazaOperativa6(oGru.getPe_INF_FINAN_PZA_V().getPlaza_OPER_06());
            	perTran.setIndFenChq(oDonat.getPe_ORG_DNTVS_V().getInd_FEN_CHQ());
            	perTran.setIndFenEfectivo(oDonat.getPe_ORG_DNTVS_V().getInd_FEN_EFTVO());
            	perTran.setIndFenEspc(oDonat.getPe_ORG_DNTVS_V().getInd_FEN_ESPC());
            	perTran.setIndFenTdc(oDonat.getPe_ORG_DNTVS_V().getInd_FEN_TDC());
            	perTran.setIndFenTrans(oDonat.getPe_ORG_DNTVS_V().getInd_FEN_TRANS());
            	perTran.setCodAmbitoOrganizacion(oGru.getCod_AMBTO_ORG());
            	perTran.setCodPaisResidencia(oGru.getCod_PAIS_RES());
            	perTran.setDescProdcuto(oGru.getPe_GRP_EMPRE_ADIC_V().getDescr_PROCTO());
            	perTran.setImpActCirculante(oDonat.pe_ORG_FINANC_V.getImp_ACT_CRCLT());
            	perTran.setImpActFijo(oDonat.pe_ORG_FINANC_V.getImp_ACT_FIJO());
            	perTran.setImpCostoVentas(oDonat.pe_ORG_FINANC_V.getImp_COST_VTA());
            	perTran.setImpDolaresMnda(oDonat.pe_ORG_FINANC_V.getImp_DLLS_MNDA());
            	perTran.setImpGastos(oDonat.pe_ORG_FINANC_V.getImp_GASTO());
            	perTran.setImpIngresoAnuales(oDonat.pe_ORG_FINANC_V.getImp_ING_ANUAL());
            	perTran.setImpIngresoExportaciones(oDonat.pe_ORG_FINANC_V.getImp_ING_EXP());
            	perTran.setImpoCapCtble(oDonat.pe_ORG_FINANC_V.getImp_CAP_CTBLE());
            	perTran.setImpPagoExportaciones(oDonat.pe_ORG_FINANC_V.getImp_PAGO_EXP());
            	perTran.setImpPasCorto(oDonat.pe_ORG_FINANC_V.getImp_PAS_CTPLZ());
            	perTran.setImpPasLargo(oDonat.pe_ORG_FINANC_V.getImp_PAS_LGPLZ());
            	perTran.setImpUtilidad(oDonat.pe_ORG_FINANC_V.getImp_UTILIDAD());
            	perTran.setImpVentasAnuales(oGru.getPe_GRP_EMPRE_ADIC_V().getImp_VTAS_ANUAL());
            	perTran.setImpVentasAnualGrp(oGru.getPe_GRP_EMPRE_ADIC_V().getImp_VTAS_ANUAL());
            	
            	//perTran.setIndAportCapital(indAportCapital);
            	//perTran.setIndAdminIngreso();
            	//perTran.setIndAdjJudiciales(indAdjJudiciales);
            	//perTran.setImpRetiros(oDonat.pe_ORG_FINANC_V.setre);
            	//perTran.setImpDepositos(oDonat.pe_ORG_FINANC_V);
            	//perTran.setIdInternoPe(idInternoPe);
            	
            	Datos.setReqAltaModPerfilTransDTO(perTran);
            	
            }
            catch(Exception ex)
            {
            	log.error("\nError en el metodo ConsultaMedioAltoRiegos " + "\nException Message: " + ex.getMessage());
            }
            //Datos.setGrupoFilial(oGru);
            //Datos.setDonativos(oDonat);
        }
        catch(Exception ex)
        {
            log.error("\nError en el metodo ConsultaMedioAltoRiegos " + "\nException Message: " + ex.getMessage());
        }
        return Datos;*/
    }
    
    public static ResponseAltaPM AltaPersonaMoral(ResGralDTO Request, String Url)
    {
    	ResponseAltaPM oResult = new ResponseAltaPM();
    	Util util = Util.getInstance();
    	try
    	{
    		String jsonRepuesta= util.callRestPost(Request,Url);
    		if(jsonRepuesta.length()>0){
    			ArrayList<String> Nodos=new ArrayList<String>();
    			oResult=(ResponseAltaPM)util.jsonToObject(oResult,jsonRepuesta,Nodos);
    		}
    		
    	}
    	catch(Exception ex)
    	{
    		oResult.setCodigo("-1");
    		oResult.setMensaje(ex.getMessage());
    		log.error("\nError en el metodo AltaPersonaMoral " + "\nException Message: " + ex.getMessage());
    	}
    	return oResult;
    }
    
    
}
