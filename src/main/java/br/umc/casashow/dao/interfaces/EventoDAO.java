package br.umc.casashow.dao.interfaces;

import java.util.List;

import br.umc.casashow.model.Evento;

public interface EventoDAO {

	List<Evento> listAllLimitTen(int offset);
	List<Evento> listAllLimitOne(int offset);
	Evento getById(Long id);
	void salvar(Evento evento);
	void excluir(Long id);
	void update(Evento evento);
	List<Evento> getByName(String nome);
	List<Evento> listAll();
}
