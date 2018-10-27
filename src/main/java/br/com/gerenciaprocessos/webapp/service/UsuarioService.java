package br.com.gerenciaprocessos.webapp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import br.com.gerenciaprocessos.webapp.entity.GrupoEntity;
import br.com.gerenciaprocessos.webapp.entity.PermissaoEntity;
import br.com.gerenciaprocessos.webapp.entity.UsuarioEntity;
import br.com.gerenciaprocessos.webapp.model.UsuarioModel;
import br.com.gerenciaprocessos.webapp.model.UsuarioSecurityModel;
import br.com.gerenciaprocessos.webapp.repository.GrupoRepository;
import br.com.gerenciaprocessos.webapp.repository.PermissaoRepository;
import br.com.gerenciaprocessos.webapp.repository.UsuarioRepository;

/**
 * 
 * @author Flavio
 * 
 * - Classe responsável por Manter, autenticar, verificar permissões e grupos de usuário.
 *
 */

@Component
public class UsuarioService implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private GrupoRepository grupoRepository; 

	@Autowired
	private PermissaoRepository permissaoRepository;

	/**
	 * - Consulta usuário por login
	 * 
	 * @param login
	 * @exception BadCredentialsException
	 * @exception DisabledException
	 * 
	 * @return
	 */
	@Override
	public UserDetails loadUserByUsername(String login) 
			throws BadCredentialsException, DisabledException {

		UsuarioEntity usuarioEntity = usuarioRepository.findByLogin(login);

		if(usuarioEntity == null)
			throw new BadCredentialsException("Usuário não encontrado no sistema!");

		if(!usuarioEntity.isAtivo())
			throw new DisabledException("Usuário não está ativo no sistema!");

		return new UsuarioSecurityModel(
				usuarioEntity.getLogin(), 
				usuarioEntity.getSenha(), 
				usuarioEntity.isAtivo(), 
				this.buscarPermissoesUsuario(usuarioEntity));
	}

	/**
	 * - Consulta as permissões do Usuário
	 * 
	 * @param usuarioEntity
	 * 
	 * @return
	 */
	public List<GrantedAuthority> buscarPermissoesUsuario(UsuarioEntity usuarioEntity) {

		List<GrupoEntity> grupos = grupoRepository.findByUsuariosIn(usuarioEntity);

		return this.buscarPermissoesDosGrupos(grupos);
	}

	/**
	 * - Consulta as permissões do grupo
	 * 
	 * @param grupos
	 * 
	 * @return
	 */
	public List<GrantedAuthority> buscarPermissoesDosGrupos(List<GrupoEntity> grupos) {
		List<GrantedAuthority> authority = new ArrayList<GrantedAuthority>();

		for (GrupoEntity grupo: grupos) {

			List<PermissaoEntity> lista = permissaoRepository.findByGruposIn(grupo);

			for (PermissaoEntity permissao: lista) {
				authority.add(new SimpleGrantedAuthority(permissao.getPermissao()));
			}
		}

		return authority;
	}

	/**
	 * - Salva um novo registro de usuário
	 * 
	 * @param usuarioModel
	 * 
	 */
	public void salvarUsuario(UsuarioModel usuarioModel){

		UsuarioEntity usuarioEntity =  new UsuarioEntity();

		//Flag que indica que o usário esta ativo
		usuarioEntity.setAtivo(true);

		//Envia o Login Usuário
		usuarioEntity.setLogin(usuarioModel.getLogin());

		//Envia o Nome do usuário
		usuarioEntity.setNome(usuarioModel.getNome());

		//Criptografa e envia a senha do usuário
		usuarioEntity.setSenha(new BCryptPasswordEncoder().encode(usuarioModel.getSenha()));

		//Recupera a lista de grupos selecionados
		GrupoEntity grupoEntity = null;
		List<GrupoEntity> grupos =  new ArrayList<GrupoEntity>();
		for (Long codigoGrupo : usuarioModel.getGrupos()){

			if(codigoGrupo != null){

				//Consulta o grupo pelo código
				grupoEntity = grupoRepository.findOne(codigoGrupo);

				//Adiciona o grupo na lista
				grupos.add(grupoEntity);
			}
		}

		//Envia a lista de grupo do usuário
		usuarioEntity.setGrupos(grupos);

		//Salva o registro
		this.usuarioRepository.save(usuarioEntity);
	}	

	/*
	 * - Consulta os usuários cadastrados
	 * 
	 * @return
	 */
	public List<UsuarioModel> consultarUsuarios(){

		List<UsuarioModel> usuariosModel = new ArrayList<UsuarioModel>();

		List<UsuarioEntity> usuariosEntity = this.usuarioRepository.findAll();

		usuariosEntity.forEach(usuarioEntity ->{

			usuariosModel.add(
					new UsuarioModel(usuarioEntity.getCodigo(),
							usuarioEntity.getNome(), 
							usuarioEntity.getLogin(), 
							null, 
							usuarioEntity.isAtivo(),
							null));
		});


		return usuariosModel;
	}

	/**
	 * - Remove um usuário pelo código
	 * 
	 * @param codigoUsuario
	 * 
	 * */
	public void excluir(Long codigoUsuario){

		this.usuarioRepository.delete(codigoUsuario);
	}

	/**
	 * - Consulta um usuário pelo código
	 * 
	 * @param codigoUsuario
	 * @return
	 */
	public UsuarioModel consultarUsuario(Long codigoUsuario){

		UsuarioEntity usuarioEntity = this.usuarioRepository.findOne(codigoUsuario);

		List<Long> grupos =  new ArrayList<Long>();

		usuarioEntity.getGrupos().forEach(grupo ->{

			grupos.add(grupo.getCodigo());

		}); 

		return new UsuarioModel(
				usuarioEntity.getCodigo(),
				usuarioEntity.getNome(),
				usuarioEntity.getLogin(),
				null,
				usuarioEntity.isAtivo(),
				grupos);
	}

	/**
	 * - Edita as informações do usuário
	 * 
	 * @param usuarioModel
	 */
	
	public void alterarUsuario(UsuarioModel usuarioModel){

		UsuarioEntity usuarioEntity =  this.usuarioRepository.findOne(usuarioModel.getCodigo());

		//Flag de Usuário ativo ou inativo
		usuarioEntity.setAtivo(usuarioModel.isAtivo());

		//Envia o login
		usuarioEntity.setLogin(usuarioModel.getLogin());

		//Envia o nome
		usuarioEntity.setNome(usuarioModel.getNome());

		//Criptografa e envia a senha
		if(!StringUtils.isEmpty(usuarioModel.getSenha()))
			usuarioEntity.setSenha(new BCryptPasswordEncoder().encode(usuarioModel.getSenha()));

		//Recupela a lista de grupos selecionados
		GrupoEntity grupoEntity = null;
		List<GrupoEntity> grupos =  new ArrayList<GrupoEntity>();
		for (Long codigoGrupo : usuarioModel.getGrupos()){

			if(codigoGrupo != null){

				//Consulta o grupo por código
				grupoEntity = grupoRepository.findOne(codigoGrupo);

				//Adiciona o grupo na lista
				grupos.add(grupoEntity);
			}
		}

		//Atualiza a lista de grupo de usuário
		usuarioEntity.setGrupos(grupos);

		//Salva a auteração do registro
		this.usuarioRepository.saveAndFlush(usuarioEntity);
	}

}
