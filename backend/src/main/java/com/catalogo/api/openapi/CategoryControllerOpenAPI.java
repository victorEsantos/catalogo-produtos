package com.catalogo.api.openapi;

import com.catalogo.dto.CategoryDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface CategoryControllerOpenAPI {

    @ApiOperation(value = "Obtem todas categorias")
    ResponseEntity<Page<CategoryDTO>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction,
            @RequestParam(value = "orderBy", defaultValue = "name") String orderBy
    );

    @ApiOperation(value = "Obtem categoria pelo identificador")
    ResponseEntity<CategoryDTO> findById(@PathVariable Long id);

    @ApiOperation(value = "Inserir nova categoria")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Categoria criada."),
            @ApiResponse(code = 400, message = "Solicitação para deletar falhou, estado inválido.")
    })
    ResponseEntity<CategoryDTO> insert(@RequestBody CategoryDTO dto);

    @ApiOperation(value = "Atualiza categoria pelo identificador")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Categoria alterada."),
            @ApiResponse(code = 400, message = "Solicitação para alterar falhou, estado inválido.")
    })
    ResponseEntity<CategoryDTO> update(@PathVariable Long id, @RequestBody CategoryDTO dto);

    @ApiOperation(value = "Deleta categoria pelo identificador")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Categoria deletada."),
            @ApiResponse(code = 400, message = "Solicitação para deletar falhou, estado inválido.")
    })
    ResponseEntity<CategoryDTO> delete(@PathVariable Long id);
}
