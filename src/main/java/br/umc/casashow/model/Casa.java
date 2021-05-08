package br.umc.casashow.model;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import br.umc.casashow.dao.impl.CasaDaoImpl;
import br.umc.casashow.error.Erro;

public class Casa {

	private Long id;
	
	@NotBlank(message = "Nome não pode ser nulo")
	@Size(max = 30, message = "O campo NOME não pode conter mais de 30 caracteres")
	private String nome;
	
	@NotBlank(message = "Endereço não pode ser nulo")
	@Size(max = 50, message = "O campo ENDEREÇO não pode conter mais de 50 caracteres")
	private String endereco;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	
	@Override
	public String toString() {
		return "Casa [id=" + id + ", nome=" + nome + ", endereco=" + endereco + "]";
	}
	
	public void criar(Casa casa) {
		CasaDaoImpl casaDao = new CasaDaoImpl();
		if(casa.getEndereco().equalsIgnoreCase(casaDao.existsEndereco(casa.getEndereco()))) {
			Erro.setMessage("Este endereço já foi cadastrado");
		} else { 
			casaDao.salvar(casa);
			Erro.setMessage(null);
			}
		
	}
	
	public Casa buscarPorId(Long id) {
		CasaDaoImpl casaDao = new CasaDaoImpl();
		Casa casa = casaDao.getById(id);
		return casa;
	}
	
	public Casa verificaSeIdentificadorEstaNull(Casa casa) {
		Casa objCasa = new Casa();
		if(casa.getId() != null) {

			objCasa.editar(casa);
		} else {
			
			objCasa.criar(casa);
		}
		return objCasa;
	}
	
	public Casa editar(Casa casa) {
		CasaDaoImpl casaDao = new CasaDaoImpl();
		casaDao.update(casa);
		Casa casaEditada = casaDao.getById(casa.getId());
		return casaEditada;
	}
	
	public void excluir(Long id) {
		CasaDaoImpl casaDao = new CasaDaoImpl();
		casaDao.excluir(id);
	}
	
	public List<Casa> pesquisar(String nome, int offset) {
		CasaDaoImpl casaDao = new CasaDaoImpl();
		List<Casa> casas = new ArrayList<Casa>();
		if(!nome.equals("%")) {
			casas = casaDao.getByName(nome);
		} else {
			casas = casaDao.listAllLimitTen(offset);
		}
		return casas;
	}
	
	
}
