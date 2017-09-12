/**
 * Created by AppWhere on 23/06/2017.
 */
var Form;
$(document).ready(function()
{
    Form=$("#frmDatosGenerales");
    startDB();
    $("#txtCodigoPostal").focusout(function (){
        GetLocalidad()
    });
	
	
	var descLarga;
	var operador;
    $("#cboTipoIdentificacion").change(function () {

        $("#txtTipoIdentificacion").val($("#cboTipoIdentificacion option:selected").text())
    })
    if(cboPaisResidencia!=""&&cboPaisResidencia!=null)
    {
        var data = [{ id: cboPaisResidencia, text: TxtPaisResidencia }];
    }
    else
    {
        var data = [{ id: 412, text: 'MEXICO' }];
    }

    $("#cboPaisResidencia").select2({
        data: data
    })

    $("#cboPaisResidencia").change(function(){
        $("#TxtPaisResidencia").val($("#cboPaisResidencia option:selected").text())
    })
    $(".select2").click(function(){
        $("#cboPaisResidencia").select2({

            d: $("#cboPaisResidencia").val(),
            //getPais: JSON.stringify({descLarga: $("#cboPaisResidencia").val(),numLogico: 'PAIS'}),
            //getPais : '{"descLarga": "'+$("#cboPaisResidencia").val()+'","numLogico": "PAIS"}',

            ajax: {
                url: "./consultaPais",
                dataType: 'json',
                type: 'post',
                delay: 250,
                contentType: 'application/json',
                data: function (params) {
                    return {
                        descLarga: params.term,
                        operador: $("#BsfOperador").val(),
                        page: params.page

                    };
                },
                processResults: function (data, params) {

                    return {
                        results: $.map(data.listaPaisesNacionalidad, function (listaPaisesNacionalidad) {
                            return {
                                text: listaPaisesNacionalidad.nomb_PAIS_AG,
                                id: listaPaisesNacionalidad.cod_PAIS_AG
                            }
                        })
                    };

                },

                cache: true
            },
            escapeMarkup: function (markup) { return markup; }, // let our custom formatter work
            minimumInputLength: 1,
        });
    })
    $(".select2").click();

    Navegacion()

    //GetLocalidad()
})
function Navegacion()
{
    var txtPasoActual =$("#txtPasoActual").val();


    if(txtPasoActual=="1" || txtPasoActual=="2")
    {
        $("#btnRelacion").prop( "disabled", "disabled" );
        $("#btnCedula").prop( "disabled", true );
        $("#btnDocumentos").prop( "disabled", true );
    }
    else if(txtPasoActual=="3")
    {
        $("#btnCedula").prop( "disabled", true );
        $("#btnDocumentos").prop( "disabled", true );
    }
    else if(txtPasoActual=="4")
    {
        $("#btnDocumentos").prop( "disabled", true );
    }
}

function GetLocalidad()
{
    var cp=$("#txtCodigoPostal").val();
    if(cp.length>0) {
        if (cp.length != 5) {
            bootbox.alert({
                message: '<p style="overflow: hidden; float: left;" class="">' + '<img style="margin: -220px 0px -240px 0px;" src="./img/messages-g.png" /></p>'
                + '<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&#161;Atenci&oacute;n&#33; </p>' + '<center><label>Formato del C&oacute;digo Postal no valido</label></center>',
                callback: function () {
                    setTimeout(function () {
                        $("#txtCodigoPostal").focus();
                    }, 100);
                }
            });

        }
        else {
            var GetLocalidad =
            {
                CP: cp
            }
            $.ajax({
                type: 'POST',
                data: GetLocalidad,
                url: './GetLocalidad',
                beforeSend: function () {
                    var dialog = bootbox.dialog({
                        message: '<div class="ui-dialog-content ui-widget-content" style="text-align: center"><div class="progress-container"><div class="progress" style="height: 10px"><div class="progress-bar"><div class="progress-shadow"></div></div></div></div><br/><label class="ui-widget ui-state-default ui-corner-all">Cargando Su Peticion</label></div>',
                        closeButton: false
                    }).css({
                        'margin-top': function () {
                            var w = $(window).height();
                            var b = $(".modal-dialog").height();
                            // should not be (w-h)/2
                            var h = (w) / 2;
                            return h + "px";
                        },
                    });
                },
                success: function (data) {
                    bootbox.hideAll()
                    $("#txtMunicipio").val(data.nombLocalidadAg);
                    $("#txtEstado").val(data.estado);
                    $("#txtPais").val("M\u00e9xico");
                    //console.log(data)
                },
                error: function () {

                    bootbox.hideAll()
                    var dialog = bootbox.alert({
                        message: '<p class="text-center">' + "No se encontro resultados con los criterios introducidos" + '</p>',
                        closeButton: true
                    });
                }
            })
        }
    }
}

function startDB() {
    try {
        dataBase = indexedDB.open('bansefi', 1);
        dataBase.onsuccess = function (e) {
            CargaCombo($("#cboTipoIdentificacion"),"catalogo_tipo_de_identificacion_pm",cboTipoIdentificacion)
            CargaCombo($("#cboRazonAlta"),"catalogo_razon_alta_pm",cboRazonAlta)

            CargaCombo($("#cboEstructuraLegal"),"catalogo_estructura_legal_pm",cboEstructuraLegal)
            CargaCombo($("#cboCNAE"),"catalogo_cnae_pm",cboCNAE)
            CargaCombo($("#cboAmbito"),"catalogo_ambito_pm",cboAmbito)
            CargaCombo($("#cboSituacionEconomica"),"catalogo_situacion_economica_pm",cboSituacionEconomica)
            CargaCombo($("#cboRegimenOcupacion"),"catalogo_regimen_de_ocupacion_pm",cboRegimenOcupacion)
            CargaCombo($("#cboTipoCalle"),"catalogo_tipo_de_calle_pm",cboTipoCalle)
            CargaCombo($("#cboComprobanteDomicilio"),"catalogo_comprobante_de_domicilio_pm",cboComprobanteDomicilio)

        };
        dataBase.onerror = function (e) {
            console.log('Error al acceder a la Base de datos.');
        };
    } catch (err) {
        console.log("Ocurri&#243; un error: startDB: " + err.message);
    }
}

