package br.umc.casashow.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.umc.casashow.dao.impl.EventoDaoImpl;
import br.umc.casashow.error.Erro;
import br.umc.casashow.model.Evento;
import br.umc.casashow.pageable.Page;

@Controller
@RequestMapping("/venda")
public class VendaController {

	private final static String VENDA_VIEW = "VendaDeIngresso";
	
	@GetMapping
	public ModelAndView telaVenda(@RequestParam(defaultValue = "0") String o) {
		ModelAndView mv = buscaEventosComPaginacao(o);
		return mv;
	}

	
	@PostMapping("/{id}/{username}")
	public String comprar(@PathVariable Long id, @PathVariable String username, RedirectAttributes attributes) {
		buscaEventoParaComprar(id, username,  attributes);
		return "redirect:/venda";
	}


	private void buscaEventoParaComprar(Long id, String username, RedirectAttributes attributes) {
		Evento objEvento = new Evento();
		Evento evento = objEvento.buscarPorId(id);
		objEvento.comprar(evento, username);
		if(Erro.getMessage() == null) {
			attributes.addFlashAttribute("mensagem", "Ingresso Comprado com Sucesso!!");
		} else {
			attributes.addFlashAttribute("erro", Erro.getMessage());
		}
	}
	
	private ModelAndView buscaEventosComPaginacao(String o) {
		int offset = Integer.parseInt(o);
		Page page = new Page(1, offset);
		page.paginacaoIngresso(offset);
		EventoDaoImpl eventoDao = new EventoDaoImpl();
		ModelAndView mv = new ModelAndView(VENDA_VIEW);
		mv.addObject("page", page);
		mv.addObject("eventos", eventoDao.listAllLimitOne(offset));
		return mv;
	}
	
}

