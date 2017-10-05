package mx.gob.bansefi.process;

import mx.gob.bansefi.services.WsServicios;
//import scala.annotation.meta.setter;

import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by AppWhere on 27/07/2017.
 */
@Service
public class SetReporteProccess {
    @Value("${url.context}")
    private String urlcontext;

    private DecimalFormat df = new DecimalFormat("0.00");
    public WsServicios ArmarRequest( ) {
        
		
		return null;
	}
}
