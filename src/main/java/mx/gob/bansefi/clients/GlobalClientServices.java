package mx.gob.bansefi.clients;

import mx.gob.bansefi.dto.Request.ReqEncryptDecryptDTO;
import mx.gob.bansefi.dto.Response.ResEncryptDecryptDTO;
import mx.gob.bansefi.utils.Util;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * Created by AppWIns on 15/05/2017.
 */
@Component
public class GlobalClientServices {

    /*
     * Se declaran variables de clase.
     */
    private Util util = Util.getInstance();
    private String urlEncrypt;
    private String urlDecrypt;
    private String msjServicioInaccesible;

    public GlobalClientServices(
            @Value("${domain.services}") String domainServices,
            @Value("${path.encrypt}") String pathEncrypt,
            @Value("${path.decrypt}") String pathDecrypt,
            @Value("${mensajes.servicioInaccesible}") String msjServicioInaccesible) {
        this.urlEncrypt = domainServices + pathEncrypt;
        this.urlDecrypt = domainServices + pathDecrypt;
        this.msjServicioInaccesible = msjServicioInaccesible;
    }

    /*
     * Metodo para consumir servicio encrypt de WSO2.
     */
    public ResEncryptDecryptDTO encrypt(ReqEncryptDecryptDTO req) {
        ResEncryptDecryptDTO res = null;
        try {
            String jsonRes = this.util.callRestPost(req, urlEncrypt);
            res = new ResEncryptDecryptDTO();

            if (!jsonRes.equals("")) {
                ArrayList<String> nodos = new ArrayList<String>();

                res = (ResEncryptDecryptDTO) this.util.jsonToObject(res, jsonRes, nodos);
            } else {
                res.setCodRet(-1);
                res.setError(msjServicioInaccesible);
            }

        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return res;
    }

    /*
     * Metodo para consumir servicio decrypt de WSO2.
     */
    public ResEncryptDecryptDTO decrypt(ReqEncryptDecryptDTO req) {
        ResEncryptDecryptDTO res = null;
        try {
            String jsonRes = this.util.callRestPost(req, urlDecrypt);
            res = new ResEncryptDecryptDTO();

            if (!jsonRes.equals("")) {
                ArrayList<String> nodos = new ArrayList<String>();

                res = (ResEncryptDecryptDTO) this.util.jsonToObject(res, jsonRes, nodos);
            } else {
                res.setCodRet(-1);
                res.setError(msjServicioInaccesible);
            }

        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return res;
    }
}
