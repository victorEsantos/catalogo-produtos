package com.catalogo.dto;

import java.io.Serializable;

import com.catalogo.entities.Category;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoryDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	private Long id;
	@ApiModelProperty(value = "Nome do produto", required = false)
	private String name;

	@Builder
	public CategoryDTO(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public CategoryDTO(Category entity) {
		this.id = entity.getId();
		this.name = entity.getName();
	}

}
