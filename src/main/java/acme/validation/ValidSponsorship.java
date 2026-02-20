
package acme.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import acme.validators.SponsorshipValidator;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SponsorshipValidator.class)
public @interface ValidSponsorship {

	String message() default "El sponsorship no cumple con las restricciones";

	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
