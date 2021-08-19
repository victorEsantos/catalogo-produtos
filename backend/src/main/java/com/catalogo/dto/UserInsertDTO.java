package com.catalogo.dto;

import com.catalogo.services.validation.UserInsertValid;
import lombok.Data;

@UserInsertValid
@Data
public class UserInsertDTO extends UserDTO{
	
	private static final long serialVersionUID = 1L;
	
	private String password;
	
	public UserInsertDTO() {
		super();
	}

}
