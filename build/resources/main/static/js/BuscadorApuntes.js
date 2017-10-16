/**
 * Created by AppWhere on 08/09/2017.
 */
var numId=false;
var validId=false;
var loading;
$(document).ready(function(){
	nomPath = window.location.pathname;
    nomPath = nomPath.substring(1, nomPath.length);
    nomPath = nomPath.split("/", 1);
    nomPath = nomPath + "/";
    
	startDB();
	maskInput();
	iniBootbox();
	$("#cboTipoIdentificacion").change(function () {
        $("#txtTipoIdentificacion").val($("#cboTipoIdentificacion option:selected").text());
        $("#codTipoIdentificacion").val($("#cboTipoIdentificacion option:selected").val());
    });
	if(verificaDigitalizacion=="2"){
		msjAlerta("Hubo un error en el proceso de digitalización. Intente de nuevo.");
	}
	if(verificaDigitalizacion!=null&&verificaDigitalizacion!="2"&&verificaDigitalizacion!="0"){
		//msjAlerta("La identificación fue validadá correctamente, ingrese las fechas y formato para realizar la consulta.");
		 $('#cboTipoIdentificacion').prop('disabled', 'disabled');
		$("#numId").prop('readonly', 'readonly');
		$("#btnValidarId").addClass('disabled');
	}
	if(numeroAcuerdo!=null&&verificaDigitalizacion=="0"){
		getNombre();
	}
})

//Funcion para crear el mensaje de carga de las solicitudes.
function iniBootbox(){
	var msg = '<div class="ui-dialog-content ui-widget-content"style="text-align: center">' + '<div class="progress-container"><div class="progress" style="height: 10px"><div class="progress-bar">'
    + '<div class="progress-shadow">' + '</div></div></div></div><br/>' + '<label class="ui-widget ui-state-default ui-corner-all">Cargando...</label></div>';
	loading = bootbox.dialog({
	message : msg,
	closeButton : false,
	show : false
	}).css({
	'top' : '50%',
	'margin-top' : function() {
	    return -(($(this).height() / 2));
	}
	});
}

//Funcion para llenar el combo de tipo de identificacion.
function startDB() {
    try {
        dataBase = indexedDB.open('bansefi', 1);
        dataBase.onsuccess = function (e) {
        	CargaComboLimitado($("#cboTipoIdentificacion"),"catalogo_identificacion",cboTipoIdentificacion);
        	CargaComboLimitado($("#cboTipoIdentificacionDigitalizacion"),"catalogo_identificacion",cboTipoIdentificacion);
        };
        dataBase.onerror = function (e) {
            console.log('Error al acceder a la Base de datos.');
        };
    } catch (err) {
        console.log("Ocurri&#243; un error: startDB: " + err.message);
    }
}

//Funcion para especificar mascaras de los input.
function maskInput()
{
	$("#numAcuerdo").mask('0000000000', {reverse: true});
}

//Funcion para iniciar el módulo de búsqueda.
function buscarPersona(){
	bootbox.hideAll();
	var obj = {
			BSFOPERADOR: $("#bsfoperador").val()
	    };
	$.ajax({
      type: "POST",
      url: window.location.protocol + "//" + window.location.host + "/" + nomPath + "busquedaPersonaEncriptar",
      data: obj,
      success: OnSuccessBusqueda,
      failure: function (response) {
          bootbox.alert("fallo: " + response.encriptado);
      }
  });
}

//========================================================================================================================================================
//Método para el manejo de la digitalización
//========================================================================================================================================================
function OnSuccessBusqueda(response){
	$("#BSFOPERADOR").val(response.respuesta);
	$('#formBuscarAcuerdo').submit();
}

//Funcion para validar la identificacion de la cuenta.
function validarId(){
	if(verificaDigitalizacion!="1"){
		if($("#numAcuerdo").val()==""||$("#txtTipoIdentificacion").val()==undefined){
			msjAlerta("Ingrese el número de cuenta al que se encuentra ligada la identificación.");
			return;
		}
		if($("#txtTipoIdentificacion").val()==""||$("#txtTipoIdentificacion").val()==undefined){
			msjAlerta("Seleccione un tipo de identificación a validar.");
			return;
		}
		if($("#numId").val()==""||$("#numId").val()==undefined){
			msjAlerta("Ingrese el número de identificación a validar.");
			return;
		}
		//var random_boolean = Math.random() >= 0.5;
		var datos={
				tipo_documento: $("#codTipoIdentificacion").val(),
				id_interno_pe: $("#idInternoPe").val()
		};
		$.ajax({
		type : 'POST',
		data : datos,
		url : window.location.protocol + "//" + window.location.host + "/" + nomPath + "getDocumento",
		beforeSend : function() {
		    loading.modal('show');
		},
		success : function(data) {
			loading.modal('hide');
			var tipoDocTCB=data.codTipoDocumento;
			if(data!=null){
				if(data.getDocumentoDigitalizadoResp!=null){
					bootbox.dialog({
						
				        size: "large",
				        message: '<iframe src="data:application/pdf;base64,'+data.getDocumentoDigitalizadoResp.documentoDigitalizado.documento+'" style="width:100%;height:100%" seamless=""></iframe>',
				        buttons: {
				            cancel: {
				                label: 'No es válida',
				                className: 'btn-danger',
				                callback: function () {
						            digitalizacion(tipoDocTCB);
						        }
				            },
				            confirm: {
				                label: 'Es válida',
				                className: 'btn-success',
				                callback: function () {
						            validId=true;
						        }
				            }
				        },
				        className: "alertDoc"
				    });
				}
				else{
					bootbox.dialog({
				        size: "large",
				        message: '<div class="alertNoDoc"><h3>No se encontró una identificación con la información ingresada</h3></div>',
				        buttons: {
				            cancel: {
				                label: 'Regresar',
				                className: 'btn-default',
				                callback: function () {
						        }
				            },
				            confirm: {
				                label: 'Digitalizar identificación',
				                className: 'btn-primary',
				                callback: function (result) {
						        	digitalizacion(tipoDocTCB);
						        }
				            }
				        }
				    });	
				}
			}
			else{
				msjAlerta("Hubo un error al verificar la información. Intentelo nuevamente o contacte al administrador.");
			}
		},
		error : function(e) {
			    console.log("Error " + e);
			    loading.modal('hide');
				msjAlerta("Verifique que el número de la cuenta sea correcto.");
				$("#numAcuerdo").val("");
		    }
	    });
	}
}

//Funcion para solicitar la digitalización de la identificacion.
function digitalizacion(tipo){
	bootbox.hideAll();
	var obj = {
			BSFOPERADOR: $("#bsfoperador").val(),
	        idInternoPe: $("#idInternoPe").val(),
	        descDoc: $("#txtTipoIdentificacion").val(),
	        descDocDB: $("#numId").val(),
	        codDoc: tipo,
	        cuenta: $("#numAcuerdo").val(),
	        titular:$("#titCuenta").val(),
	        alta: "1"
	    };
	var datos = {
			objStr:JSON.stringify(obj)
		};
	$.ajax({
      type: "POST",
      url: window.location.protocol + "//" + window.location.host + "/" + nomPath + "encriptar",
      data: datos,
      success: OnSuccess,
      failure: function (response) {
          bootbox.alert("fallo: " + response.encriptado);
      }
  });
}

//Método para el manejo de la digitalización
function OnSuccess(response){
	$("#ifrmModalDigitaContainerRet").removeClass('hidden');
	$("#digitalizar").removeClass('hidden');
	$("#formBusqueda").addClass('hidden');
	$('#TRANSPORT').val(response.respuesta);
	$('#formDigita').submit();
}

//Método para mostrar opciones de digitalizacion en caso de una consulta de visualización de un documento haya tenido un error.
function mostrarOpcDigitalizar(element){
	bootbox.hideAll();
}

//Funcion para realizar la busqueda del nombre del titular de la cuenta.
function getNombre(){
	verificaDigitalizacion=="0";
	validId=false;
	if($("#numAcuerdo").val().length<1){
		numId=false;
		msjAlerta("Verificar el número de cuenta");
		return;
	}
	var datos ={
			"bsfoperador":$("#bsfoperador").val(),
			"acuerdo":$("#numAcuerdo").val()
	}
	$.ajax({
		type : 'POST',
		data : datos,
		url : "./getNombre",
		beforeSend : function() {
		    loading.modal('show');
		},
		success : function(data) {
			loading.modal('hide');
			if(data.nombre!=null){
				numId=true;
			    $("#titCuenta").val(data.nombre);
			    $("#idInternoPe").val(data.idInternoPe);
			    $('#cboTipoIdentificacion').prop('disabled', false);
				$("#numId").prop('readonly', false);
				$("#btnValidarId").removeClass('disabled');
				verificaDigitalizacion="0";
			}
			else{
				$("#titCuenta").val("");
				$("#idInternoPe").val("");
				numId=false;
				msjAlerta("Hubo un error. Verifique que el número de la cuenta sea correcto.");
			}
		},
		error : function(e) {
				numId=false;
			    console.log("Error " + e);
			    loading.modal('hide');
			    msjAlerta("Verifique que el número de la cuenta sea correcto.");
				$("#numAcuerdo").val("");
		    }
    });
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


//Funcion para realizar la consulta de la informacion. 
function consultarInformacion(){
	if(verificaDigitalizacion=="nA"||verificaDigitalizacion=="0"){
		if(numId==false){
			msjAlerta("Verificar el número de cuenta.");
			return;
		}
		if(validId==false){
			msjAlerta("Validar identificación del titular.");
			return;
		}
	}
	$("#relacion").val(JSON.stringify(relacionDoc));
	//Se valida si se han ingresado ambas fechas.
	if($("#fechaDesde").val()!=""&&$("#fechaDesde").val()!=undefined){
		if($("#fechaHasta").val()!=""&&$("#fechaHasta").val()!=undefined){
			//Se validan que los rangos de fechas sean correctos.
			var desde = Date.parse($("#fechaDesde").val());
			var hasta = Date.parse($("#fechaHasta").val());
			if (hasta < desde) {
				msjAlerta("Verifique que las fechas de búsqueda se hayan especificado correctamente.");
				return;
			}
		}
		else{
			msjAlerta("Verifique que se hayan especificado las fechas de búsqueda.");
			return;
		}
	}
	else if($("#fechaHasta").val()!=""&&$("#fechaDesde").val()!=undefined){
		msjAlerta("Verifique que se hayan especificado las fechas de búsqueda.");
		return;
	}
	loading.modal('show');
	$("#frmBusquedaMovimientosGral").submit();
}

//Función para cancelar la ditalización del documento
function cancelarDigitalizacion(){
	$("#formBusqueda").removeClass('hidden');
	$("#digitalizar").addClass('hidden');
}