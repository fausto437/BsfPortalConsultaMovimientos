package mx.gob.bansefi.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class documentos {

    public String getId_tipo_documento() {
        return id_tipo_documento;
    }

    public void setId_tipo_documento(String id_tipo_documento) {
        this.id_tipo_documento = id_tipo_documento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getId_documento() {
        return id_documento;
    }

    public void setId_documento(String id_documento) {
        this.id_documento = id_documento;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String id_tipo_documento = "";
    public String descripcion = "";
    public String id_documento = "";
    public String fecha = "";
    @Getter @Setter
    public String base64 = "";

    public List<documentos> getListaDocumentos() {
        return listaDocumentos;
    }

    public void setListaDocumentos(List<documentos> listaDocumentos) {
        this.listaDocumentos = listaDocumentos;
    }

    public List<documentos> listaDocumentos = null;


}

