var nomPath="";
$(document).ready(function () {
	
	nomPath = window.location.pathname;
    nomPath = nomPath.substring(1, nomPath.length);
    nomPath = nomPath.split("/", 1);
    nomPath = nomPath + "/";
    
    //Acciones para la funcionalidad de los botones de salir o atras
    $("#breadcum").click(function() {
	window.history.go(-1);
    });
    $("#breadcumSpecial").click(function() {
    	if($("#formBusqueda").hasClass('hidden')){
    		$("#formBusqueda").removeClass('hidden');
    		$("#digitalizar").addClass('hidden');
    	}
    	else{
    		window.history.go(-1);
    	}
    });
    $(".btnMenuGlobal").click(function() {
	parent.irMenuGloblaPerderAvance();
    });
    $(".btnMenuDinamico").click(function() {
	parent.regresarMenuFrecuente();
    });
    redimencionar();
})

function redimencionar() {
	setTimeout(function() {
	    parent.setFrame();
	}, 1500);
}

//Función para regresar a la pantalla principal
function realizarOtraBusqueda(){
	function buscarPersona(){
		bootbox.hideAll();
		var obj = {
				BSFOPERADOR: $("#BSFOPERADOR").val()
		    };
		$.ajax({
	      type: "POST",
	      url: window.location.protocol + "//" + window.location.host + "/" + nomPath + "/",
	      data: obj,
	      success: function(){
	    	  bootbox.hideAll();
	      },
	      failure: function (response) {
	          bootbox.alert("Hubo un error. Intente de nuevo.");
	      }
	  });
	}
}