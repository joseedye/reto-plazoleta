package com.pragma.powerup.application.validation.validator;

import com.pragma.powerup.application.validation.annotation.ValidarEdad;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.Period;

public class EdadMayorValidacion implements ConstraintValidator <ValidarEdad, LocalDate> {


    @Override
    public boolean isValid(LocalDate fechaNacimiento, ConstraintValidatorContext constraintValidatorContext) {
        if (fechaNacimiento == null) return false;

        return Period.between(fechaNacimiento,LocalDate.now()).getYears()>=18;
    }
}
