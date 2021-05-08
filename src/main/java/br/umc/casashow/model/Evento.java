package br.umc.casashow.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import br.umc.casashow.dao.impl.EventoDaoImpl;
import br.umc.casashow.error.Erro;
import br.umc.casashow.model.enumeration.GeneroEvento;

public class Evento {

	private Long id;
	
	@NotBlank(message = "Nome não pode ser nulo")
	@Size(max = 30, message = "O campo NOME não pode conter mais de 30 caracteres")
	private String nome;
	
	@NotNull(message = "O campo capacidade não pode estar vazio")
	@DecimalMin(value = "1", message = "Capacidade minima é 1")
	@NumberFormat(pattern = "#,##0")
	private Integer capacidade;
	
	@NumberFormat(pattern = "#,##0")
	private int qtdIngresso;
	
	private Casa casaDeShow;
	
	@NotNull(message = "Data do evento não pode ser nulo")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date data;	
	
	@NotNull(message = "Valor não pode ser nulo")
	@DecimalMin(value = "0.01", message = "Valor não pode ser menor que 0,01")
	@DecimalMax(value = "999999999999999", message = "Valor não pode ser maior que 999.999.999.999.999,00")
	@NumberFormat(pattern = "#,##0.00")
	private Double valor;
	
	private GeneroEvento genero;
	
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
	public Integer getCapacidade() {
		return capacidade;
	}
	public void setCapacidade(Integer capacidade) {
		this.capacidade = capacidade;
	}
	public int getQtdIngresso() {
		return qtdIngresso;
	}
	public void setQtdIngresso(int qtdIngresso) {
		this.qtdIngresso = qtdIngresso;
	}
	public Casa getCasaDeShow() {
		return casaDeShow;
	}
	public void setCasaDeShow(Casa casaDeShow) {
		this.casaDeShow = casaDeShow;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
	public GeneroEvento getGenero() {
		return genero;
	}
	public void setGenero(GeneroEvento genero) {
		this.genero = genero;
	}
	
	
	public Evento verificaSeIdEstaNull(Evento evento) {
		Evento objEvento = new Evento();
		if(evento.getId() != null) {

			objEvento.editar(evento);
		} else {
			
			objEvento.criar(evento);
		}
		return objEvento;
	}
	
	public void criar(Evento evento) {
		EventoDaoImpl eventoDao = new EventoDaoImpl();
		Erro.setMessage(null);
		List<Evento> eventos = eventoDao.listAll();
		for(Evento e : eventos) {
			if(e.getData().equals(evento.getData()) && e.getCasaDeShow().getId() == evento.getCasaDeShow().getId()) {
				Erro.setMessage("Nessa casa de show, está data já está com evento marcado!!");
			}
		}
		if(Erro.getMessage() == null) {
			evento.setQtdIngresso(evento.getCapacidade());
			eventoDao.salvar(evento);
		}
	}
	
	
	public Evento buscarPorId(Long id) {
		EventoDaoImpl eventoDao = new EventoDaoImpl();
		Evento evento = eventoDao.getById(id);
		return evento;
	}
	
	public Evento editar(Evento evento) {
		Erro.setMessage(null);
		EventoDaoImpl eventoDao = new EventoDaoImpl();
		capacidadeAntigaCompareToCapacidadeAtual(evento, eventoDao);
		eventoDao.update(evento);
		Evento eventoEditado = eventoDao.getById(evento.getId());
		return eventoEditado;
	}
	
	public void excluir(Long id) {
		EventoDaoImpl eventoDao = new EventoDaoImpl();
		eventoDao.excluir(id);
	}

	
	public List<Evento> pesquisar(String nome, int offset) {
		EventoDaoImpl eventoDao = new EventoDaoImpl();
		List<Evento> eventos = new ArrayList<Evento>();
		if(!nome.equals("%")) {
			eventos = eventoDao.getByName(nome);
		} else {
			eventos = eventoDao.listAllLimitTen(offset);
		}
		return eventos;
	}
	
	public void comprar(Evento evento, String username) {
		if(evento.getQtdIngresso() > 0) {
			evento.setQtdIngresso(evento.getQtdIngresso() - 1);
			EventoDaoImpl eventoDaoImpl = new EventoDaoImpl();
			eventoDaoImpl.update(evento);
			Erro.setMessage(null);
		} else {
			Erro.setMessage("Ingresso Esgotado");
		}
		Venda.popularHistoricoDeVenda(evento, username);
	}
	
	private void capacidadeAntigaCompareToCapacidadeAtual(Evento evento, EventoDaoImpl eventoDao) {
		Evento eventoComparativo = eventoDao.getById(evento.getId());
		if(eventoComparativo.getCapacidade() < evento.getCapacidade()) {
			int diferenca = evento.getCapacidade() - eventoComparativo.getCapacidade();
			evento.setQtdIngresso(eventoComparativo.getQtdIngresso() + diferenca);
		} else {
			evento.setQtdIngresso(evento.getCapacidade());
		}
	}
	
	
}
