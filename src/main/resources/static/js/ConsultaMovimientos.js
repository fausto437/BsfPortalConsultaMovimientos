/**
 * Created by AppWhere on 23/06/2017.
 */
var contAcuerdos=10;
var anterior="";//Variable utilizada para quitar la seleccion del renglon anterior si es que existia uno ya seleccionado.
$(document).ready(function () {
	
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

//Funcion para ver más registros en pantalla
function verMas(){
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
			
			else
				i=100;
		}
		$("#listaAcuerdos").append(StrHtml);
	}
	else{
		alert("No hay más registros");
	}
}

function test(){
	return 5;
}