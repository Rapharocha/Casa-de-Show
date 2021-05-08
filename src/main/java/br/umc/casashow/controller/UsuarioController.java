package br.umc.casashow.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.umc.casashow.dao.impl.VendaDaoImpl;
import br.umc.casashow.error.Erro;
import br.umc.casashow.model.Usuario;
import br.umc.casashow.model.Venda;

@Controller
public class UsuarioController {

	private static final String LOGIN_VIEW = "Login";
	private static final String CADASTRO_VIEW = "CadastroUsuario";
	private static final String HOME = "Home";
	private static final String HISTORICO_VIEW = "Historico";
	
	@GetMapping("/login")
	public String telaLogin() {
		return LOGIN_VIEW;
	}
	
	@GetMapping("/cadastro")
	public ModelAndView telaCadastro() {
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		mv.addObject("usuario", new Usuario());
		return mv;
	}
	
	@PostMapping("/cadastro")
	public String cadastrar(@Valid Usuario usuario, Errors error, RedirectAttributes attributes) {
		if(error.hasErrors()) {
			return CADASTRO_VIEW;
		}
		usuario.inserirPermissao(usuario);
		usuario.salvar(usuario);
		if(Erro.getMessage() == null) {
			attributes.addFlashAttribute("mensagem", "Usuario cadastrado com sucesso !!");
		} else {
			attributes.addFlashAttribute("erro", Erro.getMessage());
			return "redirect:/cadastro";
		}
		return "redirect:/login";
	}
	
	@GetMapping("/")
	public String home() {
		return HOME;
	}
	
	@GetMapping("historico/{username}")
	public ModelAndView historico(@PathVariable String username) {
		VendaDaoImpl vendaDao = new VendaDaoImpl();
		List<Venda> vendas = vendaDao.findByUsuario(username);
		ModelAndView mv = new ModelAndView(HISTORICO_VIEW);
		mv.addObject("vendas", vendas);
		return mv;
	}
}
