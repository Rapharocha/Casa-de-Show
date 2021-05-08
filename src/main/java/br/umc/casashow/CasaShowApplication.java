package br.umc.casashow;

import java.util.Locale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;

import br.umc.casashow.dao.TablesDAO;


@SpringBootApplication
public class CasaShowApplication {

	public static void main(String[] args) {
		SpringApplication.run(CasaShowApplication.class, args);
		TablesDAO.createTables();
	}

	@Bean
	public LocaleResolver localeResolver ( ) {
		return new FixedLocaleResolver(new Locale("pt","BR"));
		
	}


}
