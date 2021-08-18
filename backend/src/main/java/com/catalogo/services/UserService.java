package com.catalogo.services;

// camada de serviço

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import com.catalogo.dto.RoleDTO;
import com.catalogo.dto.UserDTO;
import com.catalogo.dto.UserInsertDTO;
import com.catalogo.dto.UserUpdateDTO;
import com.catalogo.entities.Role;
import com.catalogo.entities.User;
import com.catalogo.repositories.RoleRepository;
import com.catalogo.repositories.UserRepository;
import com.catalogo.services.exceptions.DatabaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.catalogo.services.exceptions.ResourceNotFoundException;

@Service
public class UserService {
	
	// injetar um objeto BCrypt para transformar a senha e um codigo 
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	// injeção de dependencia para a camada de acesso a dados User
	
	@Autowired
	private UserRepository repository;
	
	// injeção de dependencia para a camada de acesso a dados Role
	
	@Autowired
	private RoleRepository roleRepository;
	
	
	@Transactional(readOnly = true)      // busca paginada
	public Page<UserDTO> findAllPaged(PageRequest pageRequest) {  // metodo para buscar todos os usuarios
		Page<User> list = repository.findAll(pageRequest);
		return list.map(x -> new UserDTO(x));
	
	}
	
	@Transactional(readOnly = true)
	public UserDTO findById(Long id) {    // metodo buscar uma usuario por id
		Optional<User> obj = repository.findById(id);
		User entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		return new UserDTO(entity);
	}
	
	@Transactional
	public UserDTO insert(UserInsertDTO dto) {  // metodo inserir uma novo usuario
		User entity = new User();
		copyDtoToEntity(dto, entity);
		entity.setPassword(passwordEncoder.encode(dto.getPassword()));  
		entity = repository.save(entity);
		return new UserDTO(entity);
	}

	@Transactional
	public UserDTO update(Long id, UserUpdateDTO dto) {   // metodo para atualizar um registro
		try {
			User entity = repository.getOne(id);            // utilizar getOne em vez do findById para não ir no banco
			copyDtoToEntity(dto, entity);                      // de dados sem nescessidade.
			entity = repository.save(entity);
			return new UserDTO(entity);
		}
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("id não encontrado" + id);
		}
		
	}
 
	public void delete(Long id) {                        // metodo para deletar um recurso
		try {
			repository.deleteById(id);
		}
		catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("id não encontrado" + id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Violação de integridade");
		}
	}       
	
	// metodo auxiliar  para receber dados do dto
	
	private void copyDtoToEntity(UserDTO dto, User entity) {
		
		entity.setFirstName(dto.getFirstName());
		entity.setLastName(dto.getLastName());
		entity.setEmail(dto.getEmail());
		
		entity.getRoles().clear();
		
		for (RoleDTO roleDto: dto.getRoles()) {
			Role role = roleRepository.getOne(roleDto.getId());
			entity.getRoles().add(role);
		}
		
	}

}
