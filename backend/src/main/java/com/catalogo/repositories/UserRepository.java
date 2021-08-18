package com.catalogo.repositories;

import com.catalogo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	
	// metodo para buscar no banco de dados os emails dos usuarios
	
	User findByEmail(String email);

}
