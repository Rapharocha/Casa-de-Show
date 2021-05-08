package br.umc.casashow.dao.interfaces;

import java.util.List;

import br.umc.casashow.model.Casa;

public interface CasaDAO {

	List<Casa> listAllLimitTen(int offset);
	Casa getById(Long id);
	void salvar(Casa casa);
	void excluir(Long id);
	void update(Casa casa);
	List<Casa> getByName(String nome);
	String existsEndereco(String endereco);
	List<Casa> listAll();
	
}
