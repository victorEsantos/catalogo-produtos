package com.catalogo.api.exceptions;

import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Data
public class ValidationErro extends StandardError {

    private static final long serialVersionUID = 1L;

    private List<FieldMessage> errors = new ArrayList<>();

    public void addErro(String fieldName, String message) {
        errors.add(new FieldMessage(fieldName, message));
    }

}
