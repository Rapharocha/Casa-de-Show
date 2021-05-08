package br.umc.casashow.converter;

import java.beans.PropertyEditorSupport;

import br.umc.casashow.dao.impl.CasaDaoImpl;
import br.umc.casashow.model.Casa;

public class CasaPropertyEditor extends PropertyEditorSupport{

	private CasaDaoImpl casaDaoImpl;
	
	public CasaPropertyEditor(CasaDaoImpl casaDaoImpl) {
		this.casaDaoImpl = casaDaoImpl;
	}
	
	public void setAsText(String text) {
		Long id = Long.valueOf(text);
		Casa casa = casaDaoImpl.getById(id);
		super.setValue(casa);
	}
}
