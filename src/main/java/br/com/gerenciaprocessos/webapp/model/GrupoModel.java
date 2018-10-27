package br.com.gerenciaprocessos.webapp.model;

/**
 * @author Flavio
 * 
 * - Classe Value Object que será utilizada nas interações com a view de Grupo
 *
 */
public class GrupoModel {
	
	private Long codigo;
	private String nome;	
	private String descricao;
	private Boolean checked;
 
	public GrupoModel(){
 
	}
 
	public GrupoModel(Long codigo, String nome, String descricao) {
		super();
		this.codigo = codigo;
		this.nome = nome;
		this.descricao = descricao;
	}
	public GrupoModel(Long codigo,String descricao) {
		super();
		this.codigo = codigo;	
		this.descricao = descricao;
	}

	public Long getCodigo() {
		return codigo;
	}

	public String getNome() {
		return nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public Boolean getChecked() {
		return checked;
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

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}
	
	
	
	
	
	
	
	

}
