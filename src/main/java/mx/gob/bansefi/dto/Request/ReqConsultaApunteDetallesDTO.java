package mx.gob.bansefi.dto.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by AppWhere on 12/09/2017.
 */
@AllArgsConstructor
@NoArgsConstructor
public class ReqConsultaApunteDetallesDTO extends ReqGralDTO{
    @Getter	@Setter
    public String numsec;
    @Getter	@Setter
    public String acuerdo;
    @Getter	@Setter
    public String fecha;
    @Getter	@Setter
    public String detalle;
    @Getter	@Setter
    public String importe;
    @Getter	@Setter
    public String codcuenta;
    @Getter	@Setter
    public String signo;
    @Getter	@Setter
    public String codorigen;
    @Getter	@Setter
    public String codapunte;
}
