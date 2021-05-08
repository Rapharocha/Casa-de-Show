package br.umc.casashow.config;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.umc.casashow.dao.impl.UsuarioDaoImpl;
import br.umc.casashow.model.Usuario;


@Service
@Transactional
public class ImplementsUserDetailsService implements UserDetailsService{

	  UsuarioDaoImpl ur = new UsuarioDaoImpl();
	
	  @Override
      public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		  
		  Usuario usuario = ur.findByUsername(username);
		  
		
		if(usuario.getUsername() == null) {
			throw new UsernameNotFoundException("Usuário não encontrado");
		}
		
     	return new User(usuario.getUsername(),usuario.getPassword(),true,true,true,true,usuario.getAuthorities());
	}


}
