package br.umc.casashow.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.umc.casashow.converter.CasaPropertyEditor;
import br.umc.casashow.dao.impl.CasaDaoImpl;
import br.umc.casashow.error.Erro;
import br.umc.casashow.model.Casa;
import br.umc.casashow.model.Evento;
import br.umc.casashow.model.enumeration.GeneroEvento;
import br.umc.casashow.pageable.Page;

@Controller
@RequestMapping("/evento")
public class EventoController {

	private static final String CADASTRO_VIEW = "CadastroEvento";
	private static final String PESQUISA_VIEW = "PesquisaEventos";
	private CasaDaoImpl casaDao = new CasaDaoImpl();
	private Evento evento = new Evento();
	
	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
		binder.registerCustomEditor(Casa.class, new CasaPropertyEditor(casaDao));
	}
	
	@GetMapping("/cadastro")
	public ModelAndView telaCadastro() {
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		mv.addObject("evento", new Evento());
		return mv;
	}
	
	@PostMapping("/cadastro")
	public String salvarOuEditar(@Valid Evento evento, Errors errors, RedirectAttributes attributes) {
		if(errors.hasErrors()) {
			return CADASTRO_VIEW;
		}
		return verificaSeDeveEditarOuInserir(evento,attributes);
	}

	@GetMapping("/pesquisa")
	public ModelAndView pesquisar(@RequestParam(defaultValue = "%") String nome,
			@RequestParam(defaultValue = "0") String o) {
		int offset = Integer.parseInt(o);
		ModelAndView mv = pesquisar(nome, offset);
		return mv;
	}
	
	@GetMapping("/{id}")
	public ModelAndView edicao(@PathVariable Long id) {
		Evento eventoRecuperado = evento.buscarPorId(id);
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		mv.addObject("evento", eventoRecuperado);
		return mv;
	}
	
	@DeleteMapping("/{id}")
	public String excluir(@PathVariable Long id, RedirectAttributes attributes) {
		evento.excluir(id);
		attributes.addFlashAttribute("mensagem", "Evento excluído com sucesso!");
		return "redirect:/evento/pesquisa";
	}
	
	
	@ModelAttribute("generos")
	public List<GeneroEvento> todosGeneros() {
		return Arrays.asList(GeneroEvento.values());
	}
	
	@ModelAttribute("casas")
	public List<Casa> todasCasas(){
		List<Casa> casas = casaDao.listAll();
		return casas;
	}
	
	private String verificaSeDeveEditarOuInserir(Evento evento, RedirectAttributes attributes) {
		this.evento.verificaSeIdEstaNull(evento);
		
		if(Erro.getMessage() == null) {
			attributes.addFlashAttribute("mensagem", "Evento salvo com sucesso!");
		} else {
			attributes.addFlashAttribute("erro", Erro.getMessage());
		}
		return "redirect:/evento/cadastro";
	}

	private ModelAndView pesquisar(String nome, int offset) {
		Page page = new Page(10, offset);
		page.paginacaoPesquisa(offset);
		List<Evento> eventos = evento.pesquisar(nome, offset);
		ModelAndView mv = new ModelAndView(PESQUISA_VIEW);
		mv.addObject("page", page);
		mv.addObject("eventos", eventos);
		return mv;
	}
}
