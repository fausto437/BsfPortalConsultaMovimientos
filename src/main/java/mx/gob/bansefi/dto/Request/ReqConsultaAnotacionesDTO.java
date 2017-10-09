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
public class ReqConsultaAnotacionesDTO extends ReqGralDTO{
    @Getter	@Setter
    public String acuerdo;
    @Getter	@Setter
    public String fechaDesde;
    @Getter	@Setter
    public String fechaHasta;
    @Getter	@Setter
    public String antInformativa;
    @Getter	@Setter
    public String antAlerta;
    @Getter	@Setter
    public String antImperativa;
    @Getter	@Setter
    public String antAvisos;
    
}
