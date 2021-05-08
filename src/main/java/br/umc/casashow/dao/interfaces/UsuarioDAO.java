package br.umc.casashow.dao.interfaces;

import br.umc.casashow.model.Usuario;

public interface UsuarioDAO {

	void salvar(Usuario usuario);
	Usuario findByUsername(String username);
}
