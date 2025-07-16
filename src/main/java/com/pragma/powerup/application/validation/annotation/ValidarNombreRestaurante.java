package com.pragma.powerup.application.validation.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;


@Documented
@Constraint(validatedBy = com.pragma.powerup.application.validation.validator.NombreRestauranteValidacion.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidarNombreRestaurante {
    String message() default "El nombre del restaurante no puede ser solo números";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
