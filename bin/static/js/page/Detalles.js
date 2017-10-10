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
	console.log(bsfOp);
	$("#bsfOperadorAuditoria").val(bsfOp);
	$("#formAuditoria").submit();
}

//Función para verificar el origen del apunte.
function origen(){
	console.log(bsfOp);
	$("#bsfOperadorOrigen").val(bsfOp);
	$("#formOrigen").submit();
}