package com.catalogo.repositories;

import com.catalogo.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// camada de acesso a dados role

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{

}
