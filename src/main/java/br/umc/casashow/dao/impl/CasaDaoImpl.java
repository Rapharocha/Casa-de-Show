package br.umc.casashow.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.umc.casashow.connection.ConnectionFactory;
import br.umc.casashow.dao.interfaces.CasaDAO;
import br.umc.casashow.model.Casa;


public class CasaDaoImpl implements CasaDAO{

	public List<Casa> listAllLimitTen(int offset) {
			
			List<Casa> casas = new ArrayList<>();
			
			try (Connection conn = ConnectionFactory.getConnection()){
				
			PreparedStatement prst = conn.prepareStatement("SELECT * FROM casa LIMIT 10 OFFSET ?");
			prst.setInt(1, offset);
			
			ResultSet rs = prst.executeQuery();
			
			while(rs.next()) {
				
				Casa casa = new Casa();
				casa.setId(rs.getLong("id"));
				casa.setNome(rs.getString("nome"));
				casa.setEndereco(rs.getString("endereco"));
	
				casas.add(casa);
			}
				
			} catch (SQLException e){
				System.out.println(e.getCause());
			}
			
			return casas;
		}

	public Casa getById(Long id) {
		
		Casa casa = new Casa();
		
		try(Connection conn = ConnectionFactory.getConnection()) {
			
			String sql = "SELECT * FROM casa WHERE id = ?";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setLong(1, id);
			
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				casa.setId(rs.getLong("id"));
				casa.setNome(rs.getString("nome"));
				casa.setEndereco(rs.getString("endereco"));
			}
			
			
		} catch (SQLException e) {
			System.out.println("Listagem de casas falhou!");
			e.printStackTrace();
		}
		
		return casa;
		
	}

	public void salvar(Casa casa) {

		
		try (Connection conn = ConnectionFactory.getConnection()){
			
			String sql = "INSERT INTO casa (nome, endereco) VALUES (?, ?)";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, casa.getNome());
			stmt.setString(2, casa.getEndereco());
			
			int rowsAffected = stmt.executeUpdate();
			
			System.out.println("Inserção BEM SUCEDIDA!. Foi adicionada " + rowsAffected + " linha");
			
		} catch (SQLException e) {
			
			System.out.println("Inserção falhou");
			e.printStackTrace();
		}
	}

	public void excluir(Long id) {
		
		try(Connection conn = ConnectionFactory.getConnection()) {
			
			String sql = "DELETE FROM casa WHERE id = ?";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setLong(1, id);
			
			int rowsAffected = stmt.executeUpdate();
			
			System.out.println("Delete BEM SUCEDIDO! Foi deletada "+ rowsAffected + " linha");
			
		} catch (SQLException e) {
			
			System.out.println("Delete falhou");
			e.printStackTrace();
		}
	}

	public void update(Casa casa) {
		
		try (Connection conn = ConnectionFactory.getConnection()){
			
			String sql = "UPDATE casa SET nome = ?, endereco = ? WHERE id = ?";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, casa.getNome());
			stmt.setString(2, casa.getEndereco());
			stmt.setLong(3, casa.getId());
			
			int rowsAffected = stmt.executeUpdate();
			
			System.out.println("Atualização BEM SUCEDIDA! Foi atualizada "+ rowsAffected + "linha");
			
		} catch (SQLException e) {
			System.out.println("Atualização Falhou");
			e.printStackTrace();
		}
	}
	

	@Override
	public List<Casa> getByName(String nome) {
		List<Casa> casas = new ArrayList<>();
		
		
		try(Connection conn = ConnectionFactory.getConnection()) {
			
			String sql = "SELECT * FROM casa WHERE nome ILIKE ?";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, "%"+ nome +"%");
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				Casa casa = new Casa();
				casa.setId(rs.getLong("id"));
				casa.setNome(rs.getString("nome"));
				casa.setEndereco(rs.getString("endereco"));
				casas.add(casa);
			}
			
			
		} catch (SQLException e) {
			System.out.println("Listagem de casas falhou!");
			e.printStackTrace();
		}
		
		return casas;
	}

	@Override
	public String existsEndereco(String endereco) {
		Casa casa = new Casa();
		
		try(Connection conn = ConnectionFactory.getConnection()) {
			
			String sql = "SELECT * FROM casa WHERE endereco ILIKE ?";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, endereco);
			
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				casa.setId(rs.getLong("id"));
				casa.setNome(rs.getString("nome"));
				casa.setEndereco(rs.getString("endereco"));
			}
			
			
		} catch (SQLException e) {
			System.out.println("Listagem de casas falhou!");
			e.printStackTrace();
		}
		
		return casa.getEndereco();
	}

	@Override
	public List<Casa> listAll() {
		List<Casa> casas = new ArrayList<>();
		
		try (Connection conn = ConnectionFactory.getConnection()){
			
		PreparedStatement prst = conn.prepareStatement("SELECT * FROM casa");
		
		ResultSet rs = prst.executeQuery();
		
		while(rs.next()) {
			
			Casa casa = new Casa();
			casa.setId(rs.getLong("id"));
			casa.setNome(rs.getString("nome"));
			casa.setEndereco(rs.getString("endereco"));

			casas.add(casa);
		}
			
		} catch (SQLException e){
			System.out.println(e.getCause());
		}
		
		return casas;
	}
	
	
}
