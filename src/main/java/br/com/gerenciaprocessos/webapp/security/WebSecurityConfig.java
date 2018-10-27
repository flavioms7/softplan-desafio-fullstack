package br.com.gerenciaprocessos.webapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.gerenciaprocessos.webapp.service.UsuarioService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
 
	@Autowired
	private UsuarioService usuarioRepositoryImpl;
 
	/**
	 * - Realiza as configurações de acesso ao sistema
	 * 
	 * param http
	 * */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//Determina as permissões de acesso a cada request de página ou funcionalidade
		http.authorizeRequests()
			.antMatchers("/menu/usuarios").access("hasRole('MANTER_USUARIOS')")
			.antMatchers("/menu/processos").access("hasRole('INCLUIR_PROCESSOS') or hasRole('VIZUALIZAR_PROCESSOS')")
			
			//Determina que para acessar o menu da aplicação o usuário precisa estar autenticado
			.antMatchers("/menu").authenticated()
			.anyRequest().authenticated()			
			.and()			
				.formLogin()
				
				//Determina o caminho da página de login e se o login foi efetuado com sucesso.
				.loginPage("/index").defaultSuccessUrl("/menu",true)
				//Determina que todos os usuários tem acesso a página de login
				.permitAll()
				.and()
				//Determina a finalização da sessão e redirecionamento para a página de login ao realizar o logout.
				.logout()
				.logoutSuccessUrl("/")
				.logoutUrl("/index") 
				.permitAll();
 
		//Redireciona o usuário para página de acesso negado quando o usuário não tem permição de acesso
		http.exceptionHandling().accessDeniedPage("/acessoNegado");
 
		//Informa que qualquer requisição tem acesso ao diretório src/main/resources
		http.authorizeRequests().antMatchers("/resources/**").permitAll().anyRequest().permitAll();
	}
 
	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
 
		//Informa a criptografia que debe ser usada para a senha do usuário
		auth.userDetailsService(usuarioRepositoryImpl).passwordEncoder(new BCryptPasswordEncoder());
    }
}
