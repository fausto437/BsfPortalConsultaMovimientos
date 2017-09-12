/**
 * Created by AppWhere on 08/09/2017.
 */
var contAcuerdos=0;
var contBloqueos=0;
var contRetenciones=0;
var anterior="";//Variable utilizada para quitar la seleccion del renglon anterior si es que existia uno ya seleccionado.
$(document).ready(function () {
	if(lstAcuerdos!=null){
		cargaRow("ap");
	}
	if(lstBloqueos!=null){
		cargaRow("b");
	}
	if(lstRetenciones!=null){
		cargaRow("r");
	}
	
})

//Funcion para abrir el detalle del registro seleccionado
function detalleConsulta(info, tipo){
	console.log(info);
	var valido=false;
	switch(tipo){
		case 'a':{
			$("#bsfoperador").val("bla");
			$("#numAnotacion").val("bla2");
			$("#tipo").val(tipo);
			valido=true;
		}break;
		case 'bloqueo':{
			alert("bloque");
		}
		case 'retencion':{
			alert("retencion");
		}break;
		case 'ap':{
			if($("#tblApuntes").find(".seleccionado").length>0){
				valido=true;
			}
		}break;
	}
	if(valido==true){
		$("#formMostrarDetalle").submit();
	}
	else{
		alert("Verifique que se ha seleccionado el registro correcto.");
	}
}

//Funcion para continuar con el flujo de la consulta de movimientos
function continuar(){
	$("#bsfoperador").val("bla");
	$("#formContinuar").submit();
}

//Funcion para resaltar renglón de informacion seleccionada.
//Se ingresan tambien los valores en los input del form a enviar 
function seleccionado(id,tipo){
	if(anterior!=""){
		$("#"+anterior).removeClass('seleccionado');
	}
	anterior=id;
	$("#"+id).addClass('seleccionado');
	//Se ingresan los valores que serán enviados para la consulta
	$("#tipo").val(tipo);
}

//Funcion para cargar los primeros registros de las tablas.
//PARAM tipo Variable que especifica la seccion donde se cargaran los renglones.
function cargaRow(tipo){
	switch(tipo){
		case "ap":{
			if(lstAcuerdos.length>contAcuerdos){
				var StrHtml="";
				var i = contAcuerdos;
				contAcuerdos+=10;
				for(i; i<contAcuerdos;i++){
					if(lstAcuerdos.length>i){
						var id=i+1;
						StrHtml+="<tr id='movimiento"+id+"'" +
									"onclick='seleccionado(\"movimiento"+id+"\",\"ap\")'>" +
									"<td>"+lstAcuerdos[i].concepto+"</td>" +
									"<td>"+lstAcuerdos[i].fecOperacion+"</td>" +
									"<td>"+lstAcuerdos[i].fecValor+"</td>" +
									"<td>"+lstAcuerdos[i].ofTerminal+"</td>" +
									"<td>"+lstAcuerdos[i].signo+"</td>" +
									"<td>"+lstAcuerdos[i].importe+"</td>" +
									"<td>"+lstAcuerdos[i].saldo+"</td>" +
								"</tr>";
					}
					
					else{
						i=100;
					}
				}
				$("#listaAcuerdos").append(StrHtml);
			}
			else{
				alert("No hay más registros");
			}
			break;
		}
		case "b":{
			if(lstBloqueos.length>contBloqueos){
				var StrHtml="";
				var i = contBloqueos;
				contBloqueos+=10;
				for(i; i<contBloqueos;i++){
					if(lstBloqueos.length>i){
						var id=i+1;
						StrHtml+="<tr id='bloqueo"+id+"'" +
									"onclick='seleccionado(\"bloqueo"+id+"\",\"b\")'>" +
									"<td>"+lstBloqueos[i].tipo+"</td>" +
									"<td>"+lstBloqueos[i].estado+"</td>" +
									"<td>"+lstBloqueos[i].fecAlta+"</td>" +
									"<td>"+lstBloqueos[i].fecVto+"</td>" +
									"<td>"+lstBloqueos[i].motivo+"</td>" +
									"<td>"+lstBloqueos[i].empleado+"</td>" +
									"<td>"+lstBloqueos[i].centro+"</td>" +
									"<td>"+lstBloqueos[i].importe+"</td>" +
								"</tr>";
					}
					
					else{
						i=100;
					}
				}
				$("#listaBloqueos").append(StrHtml);
			}
			else{
				alert("No hay más registros");
			}
			break;
		}
		case "r":{
			if(lstRetenciones.length>contRetenciones){
				var StrHtml="";
				var i = contRetenciones;
				contRetenciones+=10;
				for(i; i<contRetenciones;i++){
					if(lstRetenciones.length>i){
						var id=i+1;
						StrHtml+="<tr id='retencion"+id+"'" +
									"onclick='seleccionado(\"retencion"+id+"\",\"r\")'>" +
									"<td>"+lstRetenciones[i].tipo+"</td>" +
									"<td>"+lstRetenciones[i].estado+"</td>" +
									"<td>"+lstRetenciones[i].fecAlta+"</td>" +
									"<td>"+lstRetenciones[i].fecVto+"</td>" +
									"<td>"+lstRetenciones[i].concepto+"</td>" +
									"<td>"+lstRetenciones[i].empleado+"</td>" +
									"<td>"+lstRetenciones[i].origen+"</td>" +
									"<td>"+lstRetenciones[i].importe+"</td>" +
								"</tr>";
					}
					
					else{
						i=100;
					}
				}
				$("#listaRetenciones").append(StrHtml);
			}
			else{
				alert("No hay más registros");
			}
			break;
		}
	}
}