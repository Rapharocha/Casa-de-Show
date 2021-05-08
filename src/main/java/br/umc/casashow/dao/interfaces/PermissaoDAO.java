package br.umc.casashow.dao.interfaces;

import br.umc.casashow.model.Permissao;

public interface PermissaoDAO {

	void salvar(Permissao permissao);
	Permissao get(String nome);
}
