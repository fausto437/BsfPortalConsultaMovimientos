package mx.gob.bansefi.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import mx.gob.bansefi.dto.ShowCaseDTO;

@Service
public class ShowCaseService {

	private String parametro2;
	
	public ShowCaseService(
			@Value("${parametros.parametro2}") String parametro2) {
		this.parametro2 = parametro2;
	}
	
	public void serviceShowCase(ShowCaseDTO request, Model model) {
		model.addAttribute("parametro2", this.parametro2);
		model.addAttribute("nombre", request.getNombre());
	}
	
}
