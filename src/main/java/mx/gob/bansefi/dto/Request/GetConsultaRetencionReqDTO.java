package mx.gob.bansefi.dto.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by AppWhere on 26/06/2017.
 */
@AllArgsConstructor
@NoArgsConstructor
public class GetConsultaRetencionReqDTO extends ReqGralDTO{
    @Getter	@Setter
    public String acuerdo;
    @Getter	@Setter
    public String estado;
}
