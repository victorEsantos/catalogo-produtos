package com.catalogo.services;

import com.catalogo.dto.ProductDTO;
import com.catalogo.entities.Product;
import com.catalogo.repositories.CategoryRepository;
import com.catalogo.repositories.ProductRepository;
import com.catalogo.services.exceptions.DatabaseException;
import com.catalogo.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository repository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	
	@Transactional(readOnly = true)
	public Page<ProductDTO> findAllPaged(PageRequest pageRequest) {
		var list = repository.findAll(pageRequest);
		return list.map(ProductDTO::new);
	
	}
	
	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {
		var obj = repository.findById(id);
		var entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		return new ProductDTO(entity, entity.getCategories());
	}
	
	@Transactional
	public ProductDTO insert(ProductDTO dto) {
		var entity = new Product();
		copyDtoToEntity(dto, entity);
		entity = repository.save(entity);
		return new ProductDTO(entity);
	}

	@Transactional
	public ProductDTO update(Long id, ProductDTO dto) {
		try {
			var entity = repository.getOne(id);
			copyDtoToEntity(dto, entity);
			entity = repository.save(entity);
			return new ProductDTO(entity);
		}
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("id não encontrado" + id);
		}
		
	}
 
	public void delete(Long id) {
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
	
	private void copyDtoToEntity(ProductDTO dto, Product entity) {
		
		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		entity.setDate(dto.getDate());
		entity.setImgUrl(dto.getImgUrl());
		entity.setPrice(dto.getPrice());
		
		entity.getCategories().clear();

		dto.getCategories().stream().forEach(catDto -> {
			var category = categoryRepository.getOne(catDto.getId());
			entity.getCategories().add(category);
		});
	}

}
