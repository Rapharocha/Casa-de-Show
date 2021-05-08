package br.umc.casashow.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.umc.casashow.connection.ConnectionFactory;
import br.umc.casashow.dao.interfaces.UsuarioDAO;
import br.umc.casashow.model.Permissao;
import br.umc.casashow.model.Usuario;

public class UsuarioDaoImpl implements UsuarioDAO{

	@Override
	public void salvar(Usuario usuario) {
		try (Connection conn = ConnectionFactory.getConnection()){
			
			String sql = "INSERT INTO usuario (username, nome, senha, permissao) VALUES (?, ?, ?, ?)";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, usuario.getUsername());
			stmt.setString(2, usuario.getNome());
			stmt.setString(3, usuario.getPassword());
			stmt.setString(4, usuario.getPermissao().get(0).getNome());
			
			int rowsAffected = stmt.executeUpdate();
			
			System.out.println("Inserção BEM SUCEDIDA!. Foi adicionada " + rowsAffected + " linha");
			
		} catch (SQLException e) {
			
			System.out.println("Inserção falhou");
			e.printStackTrace();
		}
	}

	@Override
	public Usuario findByUsername(String username) {
		Usuario usuario = new Usuario();
		List<Permissao> permissoes = new ArrayList<>();
		
		try(Connection conn = ConnectionFactory.getConnection()) {
			
			String sql = "SELECT * FROM usuario WHERE username ILIKE ?";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, username);
			
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				
				usuario.setUsername(rs.getString("username"));
				usuario.setNome(rs.getString("nome"));
				usuario.setPassword(rs.getString("senha"));
				PermissaoDaoImpl permissaoDao = new PermissaoDaoImpl();
				Permissao permissao = permissaoDao.get(rs.getString("permissao"));
				permissoes.add(permissao);
				usuario.setPermissao(permissoes);
			}
			
			
		} catch (SQLException e) {
			System.out.println("Busca de usuario falhou!");
			e.printStackTrace();
		}
		
		return usuario;
	}
	

}
