
package validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import validators.ValidTextValidator;

@Target({
	ElementType.FIELD, ElementType.METHOD
})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidTextValidator.class)
public @interface ValidText {

	String message() default "El texto debe tener entre 1 y 255 caracteres y no estar vacío";

	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
