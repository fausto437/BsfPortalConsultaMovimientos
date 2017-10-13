/**
 * Created by AppWhere on 08/09/2017.
 */

$(document).ready(function () {
	
	nomPath = window.location.pathname;
    nomPath = nomPath.substring(1, nomPath.length);
    nomPath = nomPath.split("/", 1);
    nomPath = nomPath + "/";
    	
})

//Funcion para abrir el detalle del registro seleccionado
function detalleConsulta(num, desc, subcod, area, codAnt, codSubAnt, tipo){
	$("#numAnotacion").val(num);
	$("#desc").val(desc);
	$("#codAnt").val(codAnt);
	$("#codSubAnt").val(codSubAnt);
	$("#tipo").val(tipo);
	$("#subcodigo").val(subcod);
	$("#area").val(area);
	$("#formMostrarDetalle").submit();
}

//Funcion para continuar con el flujo de la consulta de movimientos
function continuar(){
	$("#lstAnotaciones").val(JSON.stringify(lstAnotaciones))
	$("#formContinuar").submit();
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