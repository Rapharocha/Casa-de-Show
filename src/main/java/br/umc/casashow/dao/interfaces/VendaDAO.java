package br.umc.casashow.dao.interfaces;

import java.util.List;

import br.umc.casashow.model.Venda;

public interface VendaDAO {

	Venda findByUsuarioAndEventos(String username, Long id);
	List<Venda> findByUsuario(String username);
	void salvar(Venda venda);
	void update(Venda venda);
	List<Venda> listAll();
}
