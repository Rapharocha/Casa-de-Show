package br.umc.casashow.model;

import org.springframework.format.annotation.NumberFormat;

import br.umc.casashow.dao.impl.UsuarioDaoImpl;
import br.umc.casashow.dao.impl.VendaDaoImpl;

public class Venda {

	private Long id;
	private Integer ingressosComprados;
	
	@NumberFormat(pattern = "#,##0.00")
	private Double valorTotal;
	private Evento evento;
	private Usuario usuario;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getIngressosComprados() {
		return ingressosComprados;
	}
	public void setIngressosComprados(Integer ingressosComprados) {
		this.ingressosComprados = ingressosComprados;
	}
	public Double getValorTotal() {
		return valorTotal;
	}
	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}
	public Evento getEvento() {
		return evento;
	}
	public void setEvento(Evento evento) {
		this.evento = evento;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public static void popularHistoricoDeVenda(Evento evento, String username) {
		VendaDaoImpl vendaDao = new VendaDaoImpl();
		Venda venda = vendaDao.findByUsuarioAndEventos(username, evento.getId());
		if(venda.getId() == null) {
			venda.setIngressosComprados(1);
			venda.setValorTotal(evento.getValor());
			venda.setEvento(evento);
			UsuarioDaoImpl usuarioDao = new UsuarioDaoImpl();
			venda.setUsuario(usuarioDao.findByUsername(username));
			vendaDao.salvar(venda);
		} else {
			venda.setIngressosComprados(venda.getIngressosComprados() + 1);
			venda.setValorTotal(evento.getValor() * venda.getIngressosComprados());
			vendaDao.update(venda);;
		}
	}
	
}
