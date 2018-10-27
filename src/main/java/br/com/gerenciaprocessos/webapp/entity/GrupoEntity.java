package br.com.gerenciaprocessos.webapp.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * @author Flavio Miranda
 * 
 * - Classe Responsável por povoar a tabela de Grupo
 *
 */

@Table(name="grupo")
@Entity
public class GrupoEntity implements Serializable {
 
	private static final long serialVersionUID = 1L;
 
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="id_grupo")
	@SequenceGenerator(name="id_grupo", sequenceName="id_grupo")  	
	@Column(name="id_grupo")
	private Long codigo;
 
	@Column(name="nm_grupo")
	private String nome;
 
	@Column(name="ds_grupo")
	private String descricao;
 
	@ManyToMany
	@JoinTable(
	name="usuario_grupo",
	joinColumns=@JoinColumn(name="id_grupo", referencedColumnName="id_grupo"),
	inverseJoinColumns=@JoinColumn(name="id_usuario", referencedColumnName="id_usuario")
	)
	
	private List<UsuarioEntity> usuarios;
 
	@ManyToMany
	@JoinTable(
	name="permissao_gripo",
	joinColumns=@JoinColumn(name="id_grupo", referencedColumnName="id_grupo"),
	inverseJoinColumns=@JoinColumn(name="id_permissao", referencedColumnName="id_permissao")
	)
	private List<PermissaoEntity> permissoes;

	public Long getCodigo() {
		return codigo;
	}

	public String getNome() {
		return nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public List<UsuarioEntity> getUsuarios() {
		return usuarios;
	}

	public List<PermissaoEntity> getPermissoes() {
		return permissoes;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void setUsuarios(List<UsuarioEntity> usuarios) {
		this.usuarios = usuarios;
	}

	public void setPermissoes(List<PermissaoEntity> permissoes) {
		this.permissoes = permissoes;
	}
	
}