package com.pragma.powerup.application.validation.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = com.pragma.powerup.application.validation.validator.EdadMayorValidacion.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)

public @interface ValidarEdad {
    String message() default  "El usuario debe ser mayor de edad";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default{};


}
