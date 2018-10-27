/**
 * 
 */
package br.com.gerenciaprocessos.webapp.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @author Flavio Miranda
 * 
 * - Classe Responsável por povoar a tabela de Permissão
 *
 */
@Table(name="permissao")
@Entity
public class PermissaoEntity implements Serializable {
 
	private static final long serialVersionUID = 1L;
 
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="id_permissao")
	@SequenceGenerator(name="id_permissao", sequenceName="id_permissao")  	
	@Column(name="id_permissao")	
	private Long codigo;
 
	@Column(name="nm_permissao")	
	private String permissao;
 
	@Column(name="ds_permissao")	
	private String descricao;	
 
	@ManyToMany(mappedBy = "permissoes")
	private List<GrupoEntity> grupos;

	public Long getCodigo() {
		return codigo;
	}

	public String getPermissao() {
		return permissao;
	}

	public String getDescricao() {
		return descricao;
	}

	public List<GrupoEntity> getGrupos() {
		return grupos;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public void setPermissao(String permissao) {
		this.permissao = permissao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void setGrupos(List<GrupoEntity> grupos) {
		this.grupos = grupos;
	}
	
}