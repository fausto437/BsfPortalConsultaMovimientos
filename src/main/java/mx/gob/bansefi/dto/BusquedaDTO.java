package mx.gob.bansefi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by AppWhere on 4/28/2017.
 */
@AllArgsConstructor
@NoArgsConstructor
public class BusquedaDTO {
	@Getter @Setter
	private String bsfoperador;
	@Getter @Setter
	private String numAcuerdo;
	@Getter @Setter
	private String titCuenta;
	@Getter @Setter
	private int idInternoPe;
	@Getter @Setter
	private String txtTipoIdentificacion;
	@Getter @Setter
	private String cboTipoIdentificacion;
	@Getter @Setter
	private String codTipoIdentificacion;
	@Getter @Setter
	private String numId;
	@Getter @Setter
	private String fechaDesde;
	@Getter @Setter
	private String fechaHasta;
	@Getter @Setter
	private String formato;
	@Getter @Setter
	private String verificaDigitalizacion;
	@Getter @Setter
	private AltaDocumentoTCBDTO relacionDoc;
}
