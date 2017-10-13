//variables de los Bootbox
var loading;
var nomPath;
// ############################
// init Docunemt ready
// ############################
$(document).ready(function () {
    // ========================
    // Obtener contexto
    // ========================
    nomPath = window.location.pathname;
    nomPath = nomPath.substring(1, nomPath.length);
    nomPath = nomPath.split("/", 1);
    nomPath = nomPath + "/";
});

//Función para consultar los datos de auditoría.
function auditoria(){
	$("#bsfOperadorAuditoria").val(bsfOp);
	$("#formAuditoria").submit();
}

//Función para verificar el origen del apunte.
function origen(){
	if($("#concepto").val().includes("LIQ")||$("#concepto").val().includes("EMI")){
		$("#bsfOperadorOrigen").val(bsfOp);
		$("#formOrigen").submit();
	}
	else{
		msjAlerta("El registro que desea verificar no cuenta con información de ORIGEN.");
	}
	
}

//Funcion para pintar la ventana de mensaje emergente.
//PARAM text Variable que contiene la cadena que se mostrara en el mensaje.
function msjAlerta(text) {
  bootbox.alert({
  	message : '<p style="overflow: hidden; float: left; margin-left: 5%;" class="">' + '<img style="margin: -220px 0px -240px 0px;" src="./img/messages-g.png" /></p>'
		+ '<div class="text-center text-alert"><label>¡Atención!</label><br/>' + '<label>' + text + '</label></div>',
		callback : function() {
	
		}
  });
}
