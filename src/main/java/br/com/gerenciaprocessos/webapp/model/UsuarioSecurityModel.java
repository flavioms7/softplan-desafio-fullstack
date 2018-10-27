package br.com.gerenciaprocessos.webapp.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * 
 * @author Flavio
 * 
 * - Classe que concede as devidas permissões ao usuário.
 *
 */
public class UsuarioSecurityModel extends User {
	
	private static final long serialVersionUID = 1L;
	
	public UsuarioSecurityModel(String login, String senha, Boolean ativo, Collection<? extends GrantedAuthority> authorities) {		
		super(login, senha, ativo,	true, true,true, authorities);
	}
}
