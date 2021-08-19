package com.catalogo.services;

import com.catalogo.dto.UserDTO;
import com.catalogo.dto.UserInsertDTO;
import com.catalogo.dto.UserUpdateDTO;
import com.catalogo.entities.User;
import com.catalogo.repositories.RoleRepository;
import com.catalogo.repositories.UserRepository;
import com.catalogo.services.exceptions.DatabaseException;
import com.catalogo.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
public class UserService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository repository;

    @Autowired
    private RoleRepository roleRepository;


    @Transactional(readOnly = true)
    public Page<UserDTO> findAllPaged(PageRequest pageRequest) {
        var list = repository.findAll(pageRequest);
        return list.map(UserDTO::new);

    }

    @Transactional(readOnly = true)
    public UserDTO findById(Long id) {
        var obj = repository.findById(id);
        var entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new UserDTO(entity);
    }

    @Transactional
    public UserDTO insert(UserInsertDTO dto) {
        var entity = new User();
        copyDtoToEntity(dto, entity);
        entity.setPassword(passwordEncoder.encode(dto.getPassword()));
        entity = repository.save(entity);
        return new UserDTO(entity);
    }

    @Transactional
    public UserDTO update(Long id, UserUpdateDTO dto) {
        try {
            var entity = repository.getOne(id);
            copyDtoToEntity(dto, entity);
            entity = repository.save(entity);
            return new UserDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("id não encontrado" + id);
        }

    }

    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("id não encontrado" + id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Violação de integridade");
        }
    }

    private void copyDtoToEntity(UserDTO dto, User entity) {

        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());

        entity.getRoles().clear();

        entity.getRoles().stream().forEach(roleDto -> {
            var role = roleRepository.getOne(roleDto.getId());
            entity.getRoles().add(role);
        });
    }

}
