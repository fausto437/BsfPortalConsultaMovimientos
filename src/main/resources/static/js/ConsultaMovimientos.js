/**
 * Created by AppWhere on 08/09/2017.
 */
var contApuntes=0;
var contBloqueos=0;
var contRetenciones=0;
var anterior="";//Variable utilizada para quitar la seleccion del renglon anterior si es que existia uno ya seleccionado.
var actual=0;//Variable utilizada para guardar el id del elemento seleccionado
$(document).ready(function () {
	
	nomPath = window.location.pathname;
    nomPath = nomPath.substring(1, nomPath.length);
    nomPath = nomPath.split("/", 1);
    nomPath = nomPath + "/";
    
	if(lstApuntes!=null){
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
		case 'b':{
			if($("#tblBloqueos").find(".seleccionado").length>0){
				var str = JSON.stringify(lstBloqueos[actual]);
				console.log(str);
				$("#row").val(str);
				valido=true;
			}
		}
		case 'r':{
			if($("#tblRetenciones").find(".seleccionado").length>0){
				valido=true;
			}
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
		msjAlerta("Verifique que se ha seleccionado el registro correcto.");
	}
}

//Funcion para continuar con el flujo de la consulta de movimientos
function continuar(){
	$("#bsfoperador").val("bla");
	$("#formContinuar").submit();
}

//Funcion para resaltar renglón de informacion seleccionada.
//Se ingresan tambien los valores en los input del form a enviar 
function seleccionado(id,tipo, index){
	if(anterior!=""){
		$("#"+anterior).removeClass('seleccionado');
	}
	anterior=id;
	actual=index;
	$("#"+id).addClass('seleccionado');
	//Se ingresan los valores que serán enviados para la consulta
	$("#tipo").val(tipo);
}

//Funcion para cargar los primeros registros de las tablas.
//PARAM tipo Variable que especifica la seccion donde se cargaran los renglones.
function cargaRow(tipo){
	switch(tipo){
		case "ap":{
			if(lstApuntes.length>contApuntes){
				var StrHtml="";
				var i = contApuntes;
				contApuntes+=10;
				for(i; i<contApuntes;i++){
					if(lstApuntes.length>i){
						var id=i+1;
						StrHtml+="<tr id='movimiento"+id+"'" +
									"onclick='seleccionado(\"movimiento"+id+"\",\"ap\")'>" +
									"<td>"+lstApuntes[i].concepto+"</td>" +
									"<td>"+lstApuntes[i].fecOperacion+"</td>" +
									"<td>"+lstApuntes[i].fecValor+"</td>" +
									"<td>"+lstApuntes[i].ofTerminal+"</td>" +
									"<td>"+lstApuntes[i].signo+"</td>" +
									"<td>"+lstApuntes[i].importe+"</td>" +
									"<td>"+lstApuntes[i].saldo+"</td>" +
								"</tr>";
					}
					
					else{
						i=100;
					}
				}
				$("#listaApuntes").append(StrHtml);
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
									"onclick='seleccionado(\"bloqueo"+id+"\",\"b\","+id+")'>" +
									"<td>"+lstBloqueos[i].tipo+"</td>" +
									"<td>"+lstBloqueos[i].estado+"</td>" +
									"<td>"+lstBloqueos[i].fechaAlta+"</td>" +
									"<td>"+lstBloqueos[i].fechaVTO+"</td>" +
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

//Funcion para pintar la ventana de mensaje emergente.
//PARAM text Variable que contiene la cadena que se mostrara en el mensaje.
function msjAlerta(text) {
  bootbox.alert({
  	message : '<p style="overflow: hidden; float: left; margin-left: 5%;" class="">' + '<img style="margin: -220px 0px -240px 0px;" src="/' + nomPath + 'img/messages-g.png" /></p>'
		+ '<div class="text-center text-alert"><label>¡Atención!</label><br/>' + '<label>' + text + '</label></div>',
		callback : function() {
	
		}
  });
}