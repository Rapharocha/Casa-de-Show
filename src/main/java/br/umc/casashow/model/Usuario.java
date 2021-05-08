package br.umc.casashow.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.umc.casashow.dao.impl.UsuarioDaoImpl;
import br.umc.casashow.error.Erro;

public class Usuario implements UserDetails{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@NotBlank(message = "Username não pode ser nulo")
	@Size(max = 30, message = "O campo USERNAME não pode conter mais de 30 caracteres")
	private String username;
	
	@NotBlank(message = "Nome não pode ser nulo")
	@Size(max = 30, message = "O campo NOME não pode conter mais de 30 caracteres")
	private String nome;
	
	@NotBlank(message = "Senha não pode ser nulo")
	@Size(min = 8, message = "Senha fraca. Deve conter no mínimo 8 caracteres")
	private String password;
	
	
	private List<Permissao> permissao;
	
	
	public String getUsername() {
		return this.username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getPassword() {
		return this.password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public List<Permissao> getPermissao() {
		return permissao;
	}
	public void setPermissao(List<Permissao> permissao) {
		this.permissao = permissao;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return (Collection<? extends GrantedAuthority>) this.permissao;
	}
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
	public void inserirPermissao (Usuario usuario) {
		List<Permissao> permissoes = new ArrayList<Permissao>();
		Permissao permissao = new Permissao();
		if(usuario.getUsername().equalsIgnoreCase("ADMIN")) {
			permissao.setNome("ROLE_ADMIN");
			permissao.salvar(permissao);
			permissoes.add(permissao);
			usuario.setPermissao(permissoes);
		} else {
			permissao.setNome("ROLE_USER");
			permissao.salvar(permissao);
			permissoes.add(permissao);
			usuario.setPermissao(permissoes);
		}
		
	}
	
	public void salvar(Usuario usuario) {
		UsuarioDaoImpl usuarioDao = new UsuarioDaoImpl();
		Usuario usuarioCompara = usuarioDao.findByUsername(usuario.getUsername());
		if(usuario.getUsername().equalsIgnoreCase(usuarioCompara.getUsername())) {
			Erro.setMessage("Este username já foi cadastrado!!");
		} else { 
			usuarioDao.salvar(usuario);
			Erro.setMessage(null);
		}
	}
	

	
}
