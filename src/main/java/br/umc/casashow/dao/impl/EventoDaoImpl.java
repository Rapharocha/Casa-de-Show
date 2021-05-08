package br.umc.casashow.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.umc.casashow.connection.ConnectionFactory;
import br.umc.casashow.dao.interfaces.EventoDAO;
import br.umc.casashow.model.Evento;
import br.umc.casashow.model.enumeration.GeneroEvento;

public class EventoDaoImpl implements EventoDAO{

	public List<Evento> listAllLimitTen(int offset) {
		
		List<Evento> eventos = new ArrayList<>();
		
		try (Connection conn = ConnectionFactory.getConnection()){
			
		PreparedStatement prst = conn.prepareStatement("SELECT * FROM evento LIMIT 10 OFFSET ?");
		prst.setInt(1, offset);
		
		ResultSet rs = prst.executeQuery();
		
		while(rs.next()) {
			
			Evento evento = new Evento();
			evento.setId(rs.getLong("id"));
			evento.setNome(rs.getString("nome"));
			evento.setCapacidade(rs.getInt("capacidade"));
			evento.setQtdIngresso(rs.getInt("ingresso"));
			evento.setData(rs.getDate("data"));
			evento.setValor(rs.getDouble("valor"));
			
			String tipoGenero = rs.getString("genero");
			if(tipoGenero.equalsIgnoreCase("Sertanejo")) {
				evento.setGenero(GeneroEvento.SERTANEJO);
			} else if (tipoGenero.equalsIgnoreCase("Axé")) {
				evento.setGenero(GeneroEvento.AXE);
			} else if (tipoGenero.equalsIgnoreCase("Rock")) {
				evento.setGenero(GeneroEvento.ROCK);
			} else if (tipoGenero.equalsIgnoreCase("Funk")) {
				evento.setGenero(GeneroEvento.FUNK);
			} else if (tipoGenero.equalsIgnoreCase("Pop")) {
				evento.setGenero(GeneroEvento.POP);
			} else if (tipoGenero.equalsIgnoreCase("Eletrônico")) {
				evento.setGenero(GeneroEvento.ELETRONICO);
			} else if (tipoGenero.equalsIgnoreCase("Samba")) {
				evento.setGenero(GeneroEvento.SAMBA);
			} else if (tipoGenero.equalsIgnoreCase("Pagode")) {
				evento.setGenero(GeneroEvento.PAGODE);
			}
			
			CasaDaoImpl casa = new CasaDaoImpl();
			evento.setCasaDeShow(casa.getById(rs.getLong("casa_id")));			

			eventos.add(evento);
		}
			
		} catch (SQLException e){
			System.out.println(e.getCause());
		}
		
		return eventos;
	}

	public Evento getById(Long id) {
		
		Evento evento = new Evento();
		
		try(Connection conn = ConnectionFactory.getConnection()) {
			
			String sql = "SELECT * FROM evento WHERE id = ?";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setLong(1, id);
			
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				evento.setId(rs.getLong("id"));
				evento.setNome(rs.getString("nome"));
				evento.setCapacidade(rs.getInt("capacidade"));;
				evento.setQtdIngresso(rs.getInt("ingresso"));
				evento.setData(rs.getDate("data"));
				evento.setValor(rs.getDouble("valor"));
				
				String tipoGenero = rs.getString("genero");
				if(tipoGenero.equalsIgnoreCase("Sertanejo")) {
					evento.setGenero(GeneroEvento.SERTANEJO);
				} else if (tipoGenero.equalsIgnoreCase("Axé")) {
					evento.setGenero(GeneroEvento.AXE);
				} else if (tipoGenero.equalsIgnoreCase("Rock")) {
					evento.setGenero(GeneroEvento.ROCK);
				} else if (tipoGenero.equalsIgnoreCase("Funk")) {
					evento.setGenero(GeneroEvento.FUNK);
				} else if (tipoGenero.equalsIgnoreCase("Pop")) {
					evento.setGenero(GeneroEvento.POP);
				} else if (tipoGenero.equalsIgnoreCase("Eletrônico")) {
					evento.setGenero(GeneroEvento.ELETRONICO);
				} else if (tipoGenero.equalsIgnoreCase("Samba")) {
					evento.setGenero(GeneroEvento.SAMBA);
				} else if (tipoGenero.equalsIgnoreCase("Pagode")) {
					evento.setGenero(GeneroEvento.PAGODE);
				}
				
				CasaDaoImpl casa = new CasaDaoImpl();
				evento.setCasaDeShow(casa.getById(rs.getLong("casa_id")));
			}
			
			
		} catch (SQLException e) {
			System.out.println("Listagem de alunos falhou!");
			e.printStackTrace();
		}
		
		return evento;
		
	}

	public void salvar(Evento evento) {

		
		try (Connection conn = ConnectionFactory.getConnection()){
			
			String sql = "INSERT INTO evento (nome, capacidade, ingresso, data, valor, genero, casa_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, evento.getNome());
			stmt.setInt(2, evento.getCapacidade());
			stmt.setInt(3, evento.getQtdIngresso());
			stmt.setDate(4, new java.sql.Date(evento.getData().getTime()));
			stmt.setDouble(5, evento.getValor());
			stmt.setString(6, evento.getGenero().getEstilo());
			stmt.setLong(7, evento.getCasaDeShow().getId());
			
			int rowsAffected = stmt.executeUpdate();
			
			System.out.println("Inserção BEM SUCEDIDA!. Foi adicionada " + rowsAffected + " linha");
			
		} catch (SQLException e) {
			
			System.out.println("Inserção falhou");
			e.printStackTrace();
		}
	}

	public void excluir(Long id) {
		
		try(Connection conn = ConnectionFactory.getConnection()) {
			
			String sql = "DELETE FROM evento WHERE id = ?";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setLong(1, id);
			
			int rowsAffected = stmt.executeUpdate();
			
			System.out.println("Delete BEM SUCEDIDO! Foi deletada "+ rowsAffected + " linha");
			
		} catch (SQLException e) {
			
			System.out.println("Delete falhou");
			e.printStackTrace();
		}
	}

	public void update(Evento evento) {
		
		try (Connection conn = ConnectionFactory.getConnection()){
			
			String sql = "UPDATE evento SET nome = ?, capacidade = ?, ingresso = ?, data = ?, valor = ?, genero = ?, casa_id = ? WHERE id = ?";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, evento.getNome());
			stmt.setInt(2, evento.getCapacidade());
			stmt.setInt(3, evento.getQtdIngresso());
			stmt.setDate(4, new java.sql.Date(evento.getData().getTime()));
			stmt.setDouble(5, evento.getValor());
			stmt.setString(6, evento.getGenero().getEstilo());
			stmt.setLong(7, evento.getCasaDeShow().getId());
			stmt.setLong(8, evento.getId());
			
			int rowsAffected = stmt.executeUpdate();
			
			System.out.println("Atualização BEM SUCEDIDA! Foi atualizada "+ rowsAffected + "linha");
			
		} catch (SQLException e) {
			System.out.println("Atualização Falhou");
			e.printStackTrace();
		}
	}

	@Override
	public List<Evento> getByName(String nome) {
		
		List<Evento> eventos = new ArrayList<Evento>();
		try(Connection conn = ConnectionFactory.getConnection()) {
			
			String sql = "SELECT * FROM evento WHERE nome ILIKE ?";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, "%"+ nome +"%");
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				Evento evento = new Evento();
				evento.setId(rs.getLong("id"));
				evento.setNome(rs.getString("nome"));
				evento.setCapacidade(rs.getInt("capacidade"));;
				evento.setQtdIngresso(rs.getInt("ingresso"));
				evento.setData(rs.getDate("data"));
				evento.setValor(rs.getDouble("valor"));
				
				String tipoGenero = rs.getString("genero");
				if(tipoGenero.equalsIgnoreCase("Sertanejo")) {
					evento.setGenero(GeneroEvento.SERTANEJO);
				} else if (tipoGenero.equalsIgnoreCase("Axé")) {
					evento.setGenero(GeneroEvento.AXE);
				} else if (tipoGenero.equalsIgnoreCase("Rock")) {
					evento.setGenero(GeneroEvento.ROCK);
				} else if (tipoGenero.equalsIgnoreCase("Funk")) {
					evento.setGenero(GeneroEvento.FUNK);
				} else if (tipoGenero.equalsIgnoreCase("Pop")) {
					evento.setGenero(GeneroEvento.POP);
				} else if (tipoGenero.equalsIgnoreCase("Eletrônico")) {
					evento.setGenero(GeneroEvento.ELETRONICO);
				} else if (tipoGenero.equalsIgnoreCase("Samba")) {
					evento.setGenero(GeneroEvento.SAMBA);
				} else if (tipoGenero.equalsIgnoreCase("Pagode")) {
					evento.setGenero(GeneroEvento.PAGODE);
				}
				
				CasaDaoImpl casa = new CasaDaoImpl();
				evento.setCasaDeShow(casa.getById(rs.getLong("casa_id")));
				eventos.add(evento);
			}
			
			
		} catch (SQLException e) {
			System.out.println("Listagem de alunos falhou!");
			e.printStackTrace();
		}
		
		return eventos;
	}

	@Override
	public List<Evento> listAll() {
		List<Evento> eventos = new ArrayList<>();
		
		try (Connection conn = ConnectionFactory.getConnection()){
			
		PreparedStatement prst = conn.prepareStatement("SELECT * FROM evento");
		
		ResultSet rs = prst.executeQuery();
		
		while(rs.next()) {
			
			Evento evento = new Evento();
			evento.setId(rs.getLong("id"));
			evento.setNome(rs.getString("nome"));
			evento.setCapacidade(rs.getInt("capacidade"));
			evento.setQtdIngresso(rs.getInt("ingresso"));
			evento.setData(rs.getDate("data"));
			evento.setValor(rs.getDouble("valor"));
			
			String tipoGenero = rs.getString("genero");
			if(tipoGenero.equalsIgnoreCase("Sertanejo")) {
				evento.setGenero(GeneroEvento.SERTANEJO);
			} else if (tipoGenero.equalsIgnoreCase("Axé")) {
				evento.setGenero(GeneroEvento.AXE);
			} else if (tipoGenero.equalsIgnoreCase("Rock")) {
				evento.setGenero(GeneroEvento.ROCK);
			} else if (tipoGenero.equalsIgnoreCase("Funk")) {
				evento.setGenero(GeneroEvento.FUNK);
			} else if (tipoGenero.equalsIgnoreCase("Pop")) {
				evento.setGenero(GeneroEvento.POP);
			} else if (tipoGenero.equalsIgnoreCase("Eletrônico")) {
				evento.setGenero(GeneroEvento.ELETRONICO);
			} else if (tipoGenero.equalsIgnoreCase("Samba")) {
				evento.setGenero(GeneroEvento.SAMBA);
			} else if (tipoGenero.equalsIgnoreCase("Pagode")) {
				evento.setGenero(GeneroEvento.PAGODE);
			}
			
			CasaDaoImpl casa = new CasaDaoImpl();
			evento.setCasaDeShow(casa.getById(rs.getLong("casa_id")));

			eventos.add(evento);
		}
			
		} catch (SQLException e){
			System.out.println(e.getCause());
		}
		
		return eventos;
	}

	@Override
	public List<Evento> listAllLimitOne(int offset) {
		List<Evento> eventos = new ArrayList<>();
		
		try (Connection conn = ConnectionFactory.getConnection()){
			
		PreparedStatement prst = conn.prepareStatement("SELECT * FROM evento LIMIT 1 OFFSET ?");
		prst.setInt(1, offset);
		
		ResultSet rs = prst.executeQuery();
		
		while(rs.next()) {
			
			Evento evento = new Evento();
			evento.setId(rs.getLong("id"));
			evento.setNome(rs.getString("nome"));
			evento.setCapacidade(rs.getInt("capacidade"));
			evento.setQtdIngresso(rs.getInt("ingresso"));
			evento.setData(rs.getDate("data"));
			evento.setValor(rs.getDouble("valor"));
			
			String tipoGenero = rs.getString("genero");
			if(tipoGenero.equalsIgnoreCase("Sertanejo")) {
				evento.setGenero(GeneroEvento.SERTANEJO);
			} else if (tipoGenero.equalsIgnoreCase("Axé")) {
				evento.setGenero(GeneroEvento.AXE);
			} else if (tipoGenero.equalsIgnoreCase("Rock")) {
				evento.setGenero(GeneroEvento.ROCK);
			} else if (tipoGenero.equalsIgnoreCase("Funk")) {
				evento.setGenero(GeneroEvento.FUNK);
			} else if (tipoGenero.equalsIgnoreCase("Pop")) {
				evento.setGenero(GeneroEvento.POP);
			} else if (tipoGenero.equalsIgnoreCase("Eletrônico")) {
				evento.setGenero(GeneroEvento.ELETRONICO);
			} else if (tipoGenero.equalsIgnoreCase("Samba")) {
				evento.setGenero(GeneroEvento.SAMBA);
			} else if (tipoGenero.equalsIgnoreCase("Pagode")) {
				evento.setGenero(GeneroEvento.PAGODE);
			}
			
			CasaDaoImpl casa = new CasaDaoImpl();
			evento.setCasaDeShow(casa.getById(rs.getLong("casa_id")));			

			eventos.add(evento);
		}
			
		} catch (SQLException e){
			System.out.println(e.getCause());
		}
		
		return eventos;
	}
}
