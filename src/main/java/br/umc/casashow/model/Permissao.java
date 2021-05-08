package br.umc.casashow.model;

import org.springframework.security.core.GrantedAuthority;

import br.umc.casashow.dao.impl.PermissaoDaoImpl;

public class Permissao implements GrantedAuthority{

	private static final long serialVersionUID = 1L;
	
	private String nome;
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	@Override
	public String getAuthority() {
		return this.getNome();
	}
	
	public void salvar(Permissao permissao) {
		PermissaoDaoImpl permissaoDao = new PermissaoDaoImpl();
		permissaoDao.salvar(permissao);
	}
	
	
	
	
}
