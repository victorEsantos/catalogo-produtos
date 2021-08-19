package com.catalogo.dto;

import java.io.Serializable;

import com.catalogo.entities.Role;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RoleDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String authority;

    @Builder
    public RoleDTO(Long id, String authority) {
        this.id = id;
        this.authority = authority;
    }

    public RoleDTO(Role role) {
        this.id = role.getId();
        this.authority = role.getAuthority();
    }

}
