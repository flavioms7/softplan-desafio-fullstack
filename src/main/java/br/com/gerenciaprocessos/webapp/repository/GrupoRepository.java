package br.com.gerenciaprocessos.webapp.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import br.com.gerenciaprocessos.webapp.entity.GrupoEntity;
import br.com.gerenciaprocessos.webapp.entity.UsuarioEntity;

/**
 * 
 * @author Flavio
 * 
 * - Realiza a consulta de grupos X usuarios através da biblioteca Spring Data
 *
 */
public interface GrupoRepository extends JpaRepository<GrupoEntity, Long> {
	
	List<GrupoEntity> findByUsuariosIn(UsuarioEntity usuarioEntity);

}
