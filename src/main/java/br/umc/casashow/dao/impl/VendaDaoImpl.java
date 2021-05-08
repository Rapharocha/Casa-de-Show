package br.umc.casashow.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.umc.casashow.connection.ConnectionFactory;
import br.umc.casashow.dao.interfaces.VendaDAO;
import br.umc.casashow.model.Venda;

public class VendaDaoImpl implements VendaDAO {

	@Override
	public void salvar(Venda venda) {
		
		try (Connection conn = ConnectionFactory.getConnection()){
			
			String sql = "INSERT INTO venda (ingressos_comprados, valor_total, evento_id, usuario_username) VALUES (?, ?, ?, ?)";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, venda.getIngressosComprados());
			stmt.setDouble(2, venda.getValorTotal());
			stmt.setLong(3, venda.getEvento().getId());
			stmt.setString(4, venda.getUsuario().getUsername());
			
			int rowsAffected = stmt.executeUpdate();
			
			System.out.println("Inserção BEM SUCEDIDA!. Foi adicionada " + rowsAffected + " linha");
			
		} catch (SQLException e) {
			
			System.out.println("Inserção falhou");
			e.printStackTrace();
		}
	}

	@Override
	public Venda findByUsuarioAndEventos(String username, Long id) {
		Venda venda = new Venda();
		
		try(Connection conn = ConnectionFactory.getConnection()) {
			
			String sql = "SELECT * FROM venda WHERE usuario_username = ? AND evento_id = ? ";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, username);
			stmt.setLong(2, id);
			
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				venda.setId(rs.getLong("id"));
				venda.setIngressosComprados(rs.getInt("ingressos_comprados"));
				venda.setValorTotal(rs.getDouble("valor_total"));
				EventoDaoImpl evento = new EventoDaoImpl();
				venda.setEvento(evento.getById(rs.getLong("evento_id")));
				UsuarioDaoImpl usuario = new UsuarioDaoImpl();
				venda.setUsuario(usuario.findByUsername(rs.getString("usuario_username")));
			}

		} catch (SQLException e) {
			System.out.println("Listagem de venda falhou!");
			e.printStackTrace();
		}
		
		return venda;
	}

	@Override
	public void update(Venda venda) {
		try (Connection conn = ConnectionFactory.getConnection()){
			String sql = "UPDATE venda SET ingressos_comprados = ?, valor_total = ?, evento_id = ?, usuario_username = ? WHERE id = ?";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, venda.getIngressosComprados());
			stmt.setDouble(2, venda.getValorTotal());
			stmt.setLong(3, venda.getEvento().getId());
			stmt.setString(4, venda.getUsuario().getUsername());
			stmt.setLong(5, venda.getId());
			
			int rowsAffected = stmt.executeUpdate();
			
			System.out.println("Atualização BEM SUCEDIDA! Foi atualizada "+ rowsAffected + "linha");
			
		} catch (SQLException e) {
			System.out.println("Atualização Falhou");
			e.printStackTrace();
		}
			
	}

	@Override
	public List<Venda> listAll() {
		List<Venda> vendas = new ArrayList<>();
		
		try (Connection conn = ConnectionFactory.getConnection()){
			
		PreparedStatement prst = conn.prepareStatement("SELECT * FROM venda");
		
		ResultSet rs = prst.executeQuery();
		
		while(rs.next()) {
			Venda venda = new Venda();
			venda.setId(rs.getLong("id"));
			venda.setIngressosComprados(rs.getInt("ingressos_comprados"));
			venda.setValorTotal(rs.getDouble("valor_total"));
			EventoDaoImpl evento = new EventoDaoImpl();
			venda.setEvento(evento.getById(rs.getLong("evento_id")));
			UsuarioDaoImpl usuario = new UsuarioDaoImpl();
			venda.setUsuario(usuario.findByUsername(rs.getString("usuario_username")));

			vendas.add(venda);
		}
			
		} catch (SQLException e){
			System.out.println(e.getCause());
		}
		
		return vendas;
	}

	@Override
	public List<Venda> findByUsuario(String username) {
		
		List<Venda> vendas = new ArrayList<Venda>();
		
		try(Connection conn = ConnectionFactory.getConnection()) {
			
			String sql = "SELECT * FROM venda WHERE usuario_username = ?";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, username);
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				Venda venda = new Venda();
				venda.setId(rs.getLong("id"));
				venda.setIngressosComprados(rs.getInt("ingressos_comprados"));
				venda.setValorTotal(rs.getDouble("valor_total"));
				EventoDaoImpl evento = new EventoDaoImpl();
				venda.setEvento(evento.getById(rs.getLong("evento_id")));
				UsuarioDaoImpl usuario = new UsuarioDaoImpl();
				venda.setUsuario(usuario.findByUsername(rs.getString("usuario_username")));
				vendas.add(venda);
			}

		} catch (SQLException e) {
			System.out.println("Listagem de venda falhou!");
			e.printStackTrace();
		}
		
		return vendas;
	}

}
