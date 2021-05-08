package br.umc.casashow.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import br.umc.casashow.connection.ConnectionFactory;

public class TablesDAO {

	public static void createTables() {
		createCasaIfNotExists();
		createEventoIfNotExists();
		createPermissaoIfNotExists();
		createUsuarioIfNotExists();
		createVendaIfNotExists();
		System.out.println("Tabelas Criadas Com Sucesso!!");
	}
	
	private static void createCasaIfNotExists() {
		
		try (Connection conn = ConnectionFactory.getConnection()){
			
			String sql = "CREATE TABLE IF NOT EXISTS casa ("
					+ "id SERIAL NOT NULL, "
					+ "nome VARCHAR(30) NOT NULL, "
					+ "endereco VARCHAR(50) UNIQUE NOT NULL,"
					+ "CONSTRAINT pk_casa_id PRIMARY KEY (id)"
					+ ");";
			
			Statement stmt = conn.createStatement();
			stmt.execute(sql);
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
	}

	private static void createEventoIfNotExists() {
		
		try (Connection conn = ConnectionFactory.getConnection()){
			
			String sql = "CREATE TABLE IF NOT EXISTS evento ("
					+ "id SERIAL NOT NULL, "
					+ "nome VARCHAR(30) NOT NULL, "
					+ "capacidade INTEGER NOT NULL CHECK(capacidade > 0),"
					+ "ingresso INTEGER NOT NULL,"
					+ "data DATE NOT NULL,"
					+ "valor NUMERIC(8,2) NOT NULL,"
					+ "genero VARCHAR NOT NULL,"
					+ "casa_id BIGINT NOT NULL,"
					+ "CONSTRAINT pk_evento_id PRIMARY KEY(id),"
					+ "CONSTRAINT fk_evento_casa_id FOREIGN KEY(casa_id) REFERENCES casa(id)"
					+ "ON DELETE CASCADE "
					+ "ON UPDATE CASCADE"
					+ ");";
			
			Statement stmt = conn.createStatement();
			stmt.execute(sql);
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}

	private static void createPermissaoIfNotExists() {
		try (Connection conn = ConnectionFactory.getConnection()){
			
			String sql = "CREATE TABLE IF NOT EXISTS permissao ("
					+ "nome VARCHAR(10) NOT NULL,"
					+ "CONSTRAINT pk_permissao_nome PRIMARY KEY(nome)"
					+ ");";
			
			Statement stmt = conn.createStatement();
			stmt.execute(sql);
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}

	private static void createUsuarioIfNotExists() {
		
		try (Connection conn = ConnectionFactory.getConnection()){
			
			String sql = "CREATE TABLE IF NOT EXISTS usuario ("
					+ "username VARCHAR(30) NOT NULL, "
					+ "nome VARCHAR(30) NOT NULL, "
					+ "senha VARCHAR(30) NOT NULL,"
					+ "permissao VARCHAR(10) NOT NULL,"
					+ "CONSTRAINT pk_usuario_username PRIMARY KEY(username),"
					+ "CONSTRAINT fk_usuario_permissao FOREIGN KEY (permissao) REFERENCES permissao(nome)"
					+ ");";
			
			Statement stmt = conn.createStatement();
			stmt.execute(sql);
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}

	private static void createVendaIfNotExists() {
		
		try (Connection conn = ConnectionFactory.getConnection()){
			
			String sql = "CREATE TABLE IF NOT EXISTS venda ("
					+ "id SERIAL NOT NULL, "
					+ "ingressos_comprados INTEGER NOT NULL, "
					+ "valor_total NUMERIC(8,2) NOT NULL,"
					+ "evento_id BIGINT NOT NULL,"
					+ "usuario_username VARCHAR(30) NOT NULL,"
					+ "CONSTRAINT pk_venda_id PRIMARY KEY(id),"
					+ "CONSTRAINT fk_venda_usuario_username FOREIGN KEY (usuario_username) REFERENCES usuario(username),"
					+ "CONSTRAINT fk_venda_evendo_id FOREIGN KEY (evento_id) REFERENCES evento(id)"
					+ "ON DELETE CASCADE "
					+ "ON UPDATE CASCADE"
					+ ");";
			
			Statement stmt = conn.createStatement();
			stmt.execute(sql);
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
}
