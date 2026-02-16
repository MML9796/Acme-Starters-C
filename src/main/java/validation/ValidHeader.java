
package validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import validators.ValidHeaderValidator;

@Target({
	ElementType.FIELD, ElementType.METHOD
})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidHeaderValidator.class)
public @interface ValidHeader {

	String message() default "El encabezado debe tener entre 1 y 75 caracteres y no estar vacío";

	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
