package br.com.gerenciaprocessos.webapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.gerenciaprocessos.webapp.entity.UsuarioEntity;

/**
 * 
 * @author Flavio
 * 
 * - Realiza a consulta do Login de acesso através da Biblioteca Spring Data
 *
 */
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {

	UsuarioEntity findByLogin(String login);
	
}
