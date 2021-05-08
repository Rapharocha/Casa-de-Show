package br.umc.casashow.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.umc.casashow.error.Erro;
import br.umc.casashow.model.Casa;
import br.umc.casashow.pageable.Page;

@Controller
@RequestMapping("/casa")
public class CasaController {
	
	private static final String CADASTRO_VIEW = "CadastroCasa";
	private static final String PESQUISA_VIEW = "PesquisaCasas";
	
	@GetMapping("/cadastro")
	public ModelAndView telaCadastro() {
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		mv.addObject("casa" , new Casa());
		return mv;
	}

	@PostMapping("/cadastro")
	public String salvarOuEditar(@Valid Casa casa, Errors error, RedirectAttributes attributes) {
		if(error.hasErrors()) {
			return CADASTRO_VIEW;
		}
		return verificaSeDeveEditarOuInserir(casa, attributes);
	}
	
	@GetMapping("/pesquisa")
	public ModelAndView telaPesquisa(@RequestParam(defaultValue = "%") String nome,
			@RequestParam(defaultValue = "0") String o) {
		int offset = Integer.parseInt(o);
		ModelAndView mv = pesquisar(nome, offset);
		return mv;
	}
	
	@GetMapping("/{id}")
	public ModelAndView telaEdicao(@PathVariable Long id) {
		ModelAndView mv = buscaCasaPorIdParaView(id);
		return mv;
	}
	
	@DeleteMapping("/{id}")
	public String excluir(@PathVariable Long id, RedirectAttributes attributes) {
		Casa casa = new Casa();
		casa.excluir(id);
		
		attributes.addFlashAttribute("mensagem", "Casa excluída com sucesso!");
		return "redirect:/casa/pesquisa";
	}
	

	private ModelAndView buscaCasaPorIdParaView(Long id) {
		Casa objCasa = new Casa();
		Casa casaRecuperada = objCasa.buscarPorId(id);
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		mv.addObject(casaRecuperada);
		return mv;
	}
	
	private ModelAndView pesquisar(String nome, int offset) {
		Casa objCasa = new Casa();
		Page page = new Page(10, offset);
		page.paginacaoPesquisa(offset);
		List<Casa> casas = objCasa.pesquisar(nome, offset);
		ModelAndView mv = new ModelAndView(PESQUISA_VIEW);
		mv.addObject("page", page);
		mv.addObject("casas", casas);
		return mv;
	}
	
	private String verificaSeDeveEditarOuInserir(Casa casa, RedirectAttributes attributes) {
		Casa casaNova = new Casa();
		casaNova.verificaSeIdentificadorEstaNull(casa);
		if(Erro.getMessage() == null) {
		attributes.addFlashAttribute("mensagem", "Casa salva com sucesso!");
		} else {
		attributes.addFlashAttribute("erro", Erro.getMessage());
		}
		return "redirect:/casa/cadastro";
	}
}
