/**
 * Created by AppWhere on 08/09/2017.
 */
var contApuntes=0;
var contBloqueos=0;
var contRetenciones=0;
var anterior="";//Variable utilizada para quitar la seleccion del renglon anterior si es que existia uno ya seleccionado.
var actual=0;//Variable utilizada para guardar el id del elemento seleccionado
var loading;

$(document).ready(function () {
	nomPath = window.location.pathname;
    nomPath = nomPath.substring(1, nomPath.length);
    nomPath = nomPath.split("/", 1);
    nomPath = nomPath + "/";
    iniBootbox();
	if(lstApuntes!=null){
		cargaRow("ap");
	}
	startDB();
	if(error!=null){
		msjError(error);
	}
})

//Funcion para realizar el cambio de tipo de bloqueo por su descripcion.
function startDB() {
    try {

        dataBase = indexedDB.open('bansefi', 1);
        dataBase.onsuccess = function (e) {
        	if(lstBloqueos!=null){
        		catalogo("catalogo_bloqueos","b");
        	}
        	if(lstRetenciones!=null){
        		catalogo("catalogo_retenciones","r");
        	}
        };
    }catch (err) {
        console.log("Ocurrió un error startDB: " + err.message);
    }
}

//Funcion para realizar el cambio de tipo de retencion por su descripcion.
function cargarCatalogosRetenciones() {
    try {
    	console.log(dataBase);
    	if(dataBase.readyState=="done"){
    		console.log("cargada");
    	}
        dataBase2 = indexedDB.open('bansefi', 1);
        dataBase2.onsuccess = function (e) {
        	var active = dataBase2.result;
            var data = active.transaction(["catalogo_retenciones"], "readonly");
            var object = data.objectStore("catalogo_retenciones");
            object.openCursor().onsuccess = function (e) {
                var result = e.target.result;
                if (result === null) {
                	cargaRow("r");
                    return;
                }
                catalogoTpRetenciones.push(result.value);
                result.continue();
            };
        };
    }catch (err) {
        console.log("Ocurrió un error startDB: " + err.message);
    }
}

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

//Funcion para abrir el detalle del registro seleccionado
function detalleConsulta(info, tipo){
	var valido=false;
	switch(tipo){
		case 'a':{
			$("#tipo").val(tipo);
			valido=true;
		}break;
		case 'b':{
			if($("#tblBloqueos").find(".seleccionado").length>0){
				var str = JSON.stringify(lstBloqueos[actual]);
				$("#row").val(str);
				valido=true;
			}
		}break;
		case 'r':{
			if($("#tblRetenciones").find(".seleccionado").length>0){
				var str = JSON.stringify(lstRetenciones[actual]);
				$("#row").val(str);
				valido=true;
			}
		}break;
		case 'ap':{
			if($("#tblApuntes").find(".seleccionado").length>0){
				var str = JSON.stringify(lstApuntes[actual]);
				$("#row").val(str);
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

//Funcion para resaltar renglón de informacion seleccionada.
//Se ingresan tambien los valores en los input del form a enviar 
function seleccionado(id,tipo, index, desc){
	if(anterior!=""){
		$("#"+anterior).removeClass('seleccionado');
	}
	anterior=id;
	actual=index-1;
	$("#"+id).addClass('seleccionado');
	//Se ingresan los valores que serán enviados para la consulta
	$("#tipo").val(tipo);
	$("#descripcion").val(desc);
	
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
									"onclick='seleccionado(\"movimiento"+id+"\",\"ap\","+id+",\"\")'>" +
									"<td>"+lstApuntes[i].concepto+"</td>" +
									"<td>"+lstApuntes[i].fechaoperacion+"</td>" +
									"<td>"+lstApuntes[i].fechavalor+"</td>" +
									"<td>"+lstApuntes[i].ofterminal+"</td>" +
									"<td>"+lstApuntes[i].signo+"</td>" +
									"<td>"+lstApuntes[i].importe+"</td>" +
									"<td>"+lstApuntes[i].saldo+"</td>" +
								"</tr>";
					}
					else{
						
						i=contApuntes;
					}
				}
				$("#listaApuntes").append(StrHtml);
			}
			else{
				if(lstApuntes[contApuntes-1]!=undefined){
					var obj = {
							"tipo":"ap",
							"cadenaDatos":JSON.stringify(datosConsulta),
							"numsec":lstApuntes[contApuntes-1].detalle,
							"imp":lstApuntes[contApuntes-1].importe
					}
					$.ajax({
					      type: "POST",
					      url: window.location.protocol + "//" + window.location.host + "/" + nomPath + "cargarRegistros",
					      beforeSend : function() {
							    loading.modal('show');
							},
					      data: obj,
					      success: OnSuccess,
					      failure: function (response) {
					          bootbox.alert("Fallo: " + response);
					      }
					  });
				}
				else{
					if(contApuntes!=0){
						msjAlerta("No hay más registros para mostrar.");
					}
				}
				
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
						var objTipo = catalogoTpBloqueo.find(function (element){
							return element.identificador === lstBloqueos[i].tipo;
						});
						if(objTipo==undefined){
							objTipo={
								"descripcion":lstBloqueos[i].tipo
							}
						}
						StrHtml+="<tr id='bloqueo"+id+"'" +
									"onclick='seleccionado(\"bloqueo"+id+"\",\"b\","+id+", \""+objTipo.descripcion+"\")'>" +
									"<td>"+objTipo.descripcion +"</td>" +
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
						i=contBloqueos;
					}
				}
				$("#listaBloqueos").append(StrHtml);
			}
			else{
				msjAlerta("No hay más registros para mostrar.");
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
						var objTipo = catalogoTpRetenciones.find(function (element){
							return element.identificador === lstRetenciones[i].tipo.trim();
						});
						if(objTipo==undefined){
							objTipo={
								"descripcion":lstRetenciones[i].tipo
							}
						}
						StrHtml+="<tr id='retencion"+id+"'" +
									"onclick='seleccionado(\"retencion"+id+"\",\"r\","+id+",\""+objTipo.descripcion+"\")'>" +
									"<td>"+objTipo.descripcion+"</td>" +
									"<td>"+lstRetenciones[i].estado+"</td>" +
									"<td>"+lstRetenciones[i].fechaAlta+"</td>" +
									"<td>"+lstRetenciones[i].fechaVTO+"</td>" +
									"<td>"+lstRetenciones[i].concepto+"</td>" +
									"<td>"+lstRetenciones[i].empleado+"</td>" +
									"<td>"+lstRetenciones[i].origen+"</td>" +
									"<td>"+lstRetenciones[i].importe+"</td>" +
								"</tr>";
					}
					
					else{
						i=contRetenciones;
					}
				}
				$("#listaRetenciones").append(StrHtml);
			}
			else{
				msjAlerta("No hay más registros para mostrar.");
			}
			break;
		}
	}
}

//Función en caso de encontrar más registros para las tablas
function OnSuccess(response){
	loading.modal('hide');
	if(response.apuntes!=null){
		lstApuntes=response.apuntes;
		contApuntes=0;
		$("#listaApuntes tr").remove();
		cargaRow("ap");
	}
	else if(response.bloqueos!=null){
		lstBloqueos=lstBloqueos.concat(response.bloqueos);
		cargaRow("b");
	}
	else if(response.retenciones!=null){
		lstRetenciones=lstRetenciones.concat(response.retenciones);
		cargaRow("r");
	}
	else{
		msjAlerta("No hay más registros para mostrar.");
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

//Funcion para pintar la ventana de mensaje emergente de error.
//PARAM text Variable que contiene la cadena que se mostrara en el mensaje.
function msjError(text) {
bootbox.alert({
	message : '<p style="overflow: hidden; float: left; margin-left: 5%;" class="">' + '<img style="margin: -220px 0px -240px 0px;" src="/' + nomPath + 'img/messages-g.png" /></p>'
		+ '<div class="text-center text-alert"><label>¡Atención!</label><br/>' + '<label>' + text + '</label></div>',
		callback : function() {
			$("#formOtraBusqueda").submit();
		}
});
}

//Función para imprimir el reporte.
function imprimirReporte(){
	var obj={
			"lista":JSON.stringify(lstApuntesImpresion),
			"numAcuerdo":$("#acuerdo").val(),
			"titCuenta":$("#titular").val(),
			"fechaDesde":$("#fechaDesde").val(),
			"fechaHasta":$("#fechaHasta").val()
	}
	$.ajax({
	      type: "POST",
	      url: window.location.protocol + "//" + window.location.host + "/" + nomPath + "imprimir",
	      beforeSend : function() {
			    loading.modal('show');
			},
	      data: obj,
	      success: function(data){
	    	  loading.modal('hide');
              bootbox.alert({
                  size: "large",
                  message: '<iframe src="data:application/pdf;base64,' + data + '" style="width:100%;height:100%" seamless=""></iframe>',
                  className: "alertDoc"
              });
	      },
	      failure: function (response) {
	          bootbox.alert("Fallo: " + response);
	      }
	  });
}