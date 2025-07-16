package com.pragma.powerup.application.validation.validator;

import com.pragma.powerup.application.validation.annotation.ValidarNombreRestaurante;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NombreRestauranteValidacion implements ConstraintValidator<ValidarNombreRestaurante,String> {


    @Override
    public boolean isValid(String nombre, ConstraintValidatorContext constraintValidatorContext) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return false;
        }

        return !nombre.trim().matches("^\\d+$");
    }
}
