package br.com.gerenciaprocessos.webapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gerenciaprocessos.webapp.entity.GrupoEntity;
import br.com.gerenciaprocessos.webapp.entity.PermissaoEntity;

/**
 * 
 * @author Flavio
 * 
 * - Realiza a consulta das permissoes X grupo de acesso através da Biblioteca Spring Data
 *
 */
public interface PermissaoRepository extends JpaRepository<PermissaoEntity, Long> {

	List<PermissaoEntity> findByGruposIn(GrupoEntity grupoEntity);

}
