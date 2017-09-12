package mx.gob.bansefi.dto.Response;

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
public class GetLocalidadResponseDTO {

    @Getter
    @Setter
    private String codProvinciaAg;
    @Getter
    @Setter
    private String nombLocalidadAg;
    @Getter
    @Setter
    private String nombProvinciaAg;
    @Getter
    @Setter
    private String codArGeo;
    @Getter
    @Setter
    private String numArGeo;
    @Getter
    @Setter
    private String codEntColecAg;
    @Getter
    @Setter
    private String codEntSingAg;
    @Getter
    @Setter
    private String codMunicipioAg;
    @Getter
    @Setter
    private String estado;
}
