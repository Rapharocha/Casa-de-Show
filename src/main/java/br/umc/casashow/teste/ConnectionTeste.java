package br.umc.casashow.teste;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import br.umc.casashow.dao.TablesDAO;

public class ConnectionTeste {

	public static void main(String[] args) throws SQLException {
		
		
		Connection connection = null;
		
		String url = "jdbc:postgresql://localhost:5432/casa_show";
		 
		
			connection = DriverManager.getConnection(url, "postgres", "admadm");
			System.out.println("Conexão com Sucesso");
			
//			System.out.println("TESTE CASA");
//			CasaDaoImpl casaDAO = new CasaDaoImpl();
//			System.out.println(casaDAO.listAll());
//			System.out.println("---------------------------");
//			System.out.println(casaDAO.getById(3));
//			System.out.println("---------------------------");
//			Casa casa = new Casa();
//			casa.setNome("Villa's Bar");
//			casa.setEndereco("Rua Doutor Nelson Cruz");
////			casaDAO.create(casa);
////			casaDAO.deletar(4);
//			Casa casaUpdate = new Casa();
//			casaUpdate.setId(3L);
//			casaUpdate.setNome("NYX");
//			casaUpdate.setEndereco("Avenida das Orquideas");
//			casaDAO.update(casaUpdate);
//			System.out.println(casaDAO.listAll());
//			System.out.println("----------------------------------------------");
//			System.out.println("TESTE EVENTO");
//			EventoDaoImpl eventoDAO = new EventoDaoImpl();
//			System.out.println(eventoDAO.listAll());
//			System.out.println("---------------------------");
//			System.out.println(eventoDAO.getById(2));
//			System.out.println("---------------------------");
//			Evento evento = new Evento();
//			evento.setNome("Alok");
//			evento.setCapacidade(30000);
//			evento.setQtdIngresso(evento.getCapacidade());
//			evento.setData(new Date());
//			evento.setValor(1000.00);
//			evento.setGenero(GeneroEvento.ELETRONICO);
//			evento.setCasaDeShow(casaDAO.getById(3));
////			eventoDAO.create(evento);
////			System.out.println(eventoDAO.getById(3));
////			eventoDAO.deletar(4);
//			System.out.println(eventoDAO.listAll());
//			Evento eventoUpdate = new Evento();
//			eventoUpdate.setId(1L);
//			eventoUpdate.setNome("Gusttavo Lima");
//			eventoUpdate.setCapacidade(5000);
//			eventoUpdate.setQtdIngresso(eventoUpdate.getCapacidade());
//			eventoUpdate.setData(new Date());
//			eventoUpdate.setValor(250.00);
//			eventoUpdate.setGenero(GeneroEvento.SERTANEJO);
//			eventoUpdate.setCasaDeShow(casaDAO.getById(3L));
//			eventoDAO.update(eventoUpdate);
//			System.out.println("--------------------------------------");
//			System.out.println("TESTE USUARIO");
//			Usuario usuario = new Usuario();
//			usuario.setUsername("ADMIN");
//			usuario.setNome("Raphael Gustavo");
//			usuario.setPassword("1234");
//			Permissao permissao = new Permissao();
//			permissao.inserirPermissao(usuario);
//			PermissaoDaoImpl permissaoDAO = new PermissaoDaoImpl();
//			permissaoDAO.salvar(permissao);
//			usuario.setPermissao(permissao);
//			UsuarioDaoImpl userDAO = new UsuarioDaoImpl();
//			userDAO.salvar(usuario);
//			Venda venda = new Venda();
//			
//			venda.setIngressosComprados(2);
//			venda.setValorTotal(250D);
//			venda.setEvento(eventoUpdate);
//			venda.setUsuario(usuario);
//			VendaDaoImpl vendaDAO = new VendaDaoImpl();
//			vendaDAO.salvar(venda);
			
//			EventoDaoImpl eventoDao = new EventoDaoImpl();
//			System.out.println(eventoDao.listAll());
			
			TablesDAO.createTables();
			
//			
//			
		connection.close();
		

	}

}
