package com.catalogo.api.openapi;

import com.catalogo.dto.ProductDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface ProductControllerOpenAPI {

    @ApiOperation(value = "Obtem todos produtos")
    public ResponseEntity<Page<ProductDTO>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction,
            @RequestParam(value = "orderBy", defaultValue = "name") String orderBy
    ) ;

    @ApiOperation(value = "Obtem produto pelo identificador")
    ResponseEntity<ProductDTO> findById(@PathVariable Long id);

    @ApiOperation(value = "Inserir novo produto")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "produto criado."),
            @ApiResponse(code = 400, message = "Solicitação para deletar falhou, estado inválido.")
    })
    ResponseEntity<ProductDTO> insert(@RequestBody ProductDTO dto);

    @ApiOperation(value = "Atualiza produto pelo identificador")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "produto alterado."),
            @ApiResponse(code = 400, message = "Solicitação para alterar falhou, estado inválido.")
    })
    ResponseEntity<ProductDTO> update(@PathVariable Long id, @RequestBody ProductDTO dto);

    @ApiOperation(value = "Deleta produto pelo identificador")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "produto deletado."),
            @ApiResponse(code = 400, message = "Solicitação para deletar falhou, estado inválido.")
    })
    ResponseEntity<ProductDTO> delete(@PathVariable Long id);
}
