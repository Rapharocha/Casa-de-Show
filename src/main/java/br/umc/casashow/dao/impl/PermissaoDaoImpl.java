package br.umc.casashow.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.umc.casashow.connection.ConnectionFactory;
import br.umc.casashow.dao.interfaces.PermissaoDAO;
import br.umc.casashow.model.Permissao;

public class PermissaoDaoImpl implements PermissaoDAO {

	@Override
	public void salvar(Permissao permissao) {
		
		try (Connection conn = ConnectionFactory.getConnection()){
			
			String sql = "INSERT INTO permissao (nome) VALUES (?)";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, permissao.getNome());
			
			int rowsAffected = stmt.executeUpdate();
			
			System.out.println("Inserção BEM SUCEDIDA!. Foi adicionada " + rowsAffected + " linha");
			
		} catch (SQLException e) {
			
			System.out.println("Inserção falhou");
			e.printStackTrace();
		}	
		
	}

	@Override
	public Permissao get(String nome) {
		Permissao permissao = new Permissao();
		
		try(Connection conn = ConnectionFactory.getConnection()) {
			
			String sql = "SELECT * FROM permissao WHERE nome = ?";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, nome);
			
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				permissao.setNome(rs.getString("nome"));
			}
			
			
		} catch (SQLException e) {
			System.out.println("Busca de permissão falhou!");
			e.printStackTrace();
		}
		
		return permissao;
	}

}
