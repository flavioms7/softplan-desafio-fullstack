package br.com.gerenciaprocessos.webapp.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
 * - Classe Responsável por povoar a tabela de Usuário
 *
 */

@Table(name="usuario")
@javax.persistence.Entity
public class UsuarioEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy= GenerationType.SEQUENCE, generator="id_usuario")
	@SequenceGenerator(name="id_usuario", sequenceName="id_usuario")  	
	@Column(name="id_usuario")
	private Long codigo;
	
	@Column(name="nm_usuario")
	private String	nome;
 
	@Column(name="login_usuario")
	private String login;
 
	@Column(name="senha_usuario")
	private String senha;
 
	@Column(name="status_usuario")
	private boolean ativo;
	
	@JoinTable(name = "usuario_grupo", 
	    joinColumns = {@JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")}, 
	    inverseJoinColumns = {@JoinColumn(name = "id_grupo", referencedColumnName = "id_grupo")}
	    )
	
	@ManyToMany(cascade ={ CascadeType.PERSIST, CascadeType.MERGE})	
	private List<GrupoEntity> grupos;

	public Long getCodigo() {
		return codigo;
	}

	public String getNome() {
		return nome;
	}

	public String getLogin() {
		return login;
	}

	public String getSenha() {
		return senha;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public List<GrupoEntity> getGrupos() {
		return grupos;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public void setGrupos(List<GrupoEntity> grupos) {
		this.grupos = grupos;
	}
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
