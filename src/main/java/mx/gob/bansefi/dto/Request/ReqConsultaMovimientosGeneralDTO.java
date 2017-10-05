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
public class ReqConsultaMovimientosGeneralDTO extends ReqGralDTO{
    @Getter	@Setter
    public String numsec;
    @Getter	@Setter
    public String acuerdo;
    @Getter	@Setter
    public String fechadesde;
    @Getter	@Setter
    public String fechahasta;
    @Getter	@Setter
    public String acceso;
    @Getter	@Setter
    public String impsdo;
    @Getter	@Setter
    public String formato;
}
