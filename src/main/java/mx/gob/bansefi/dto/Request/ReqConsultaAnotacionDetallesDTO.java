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
public class ReqConsultaAnotacionDetallesDTO extends ReqGralDTO{
    @Getter	@Setter
    public String codAnt;
    @Getter	@Setter
    public String codSubAnt;
    @Getter	@Setter
    public String anotacion;
    
}
