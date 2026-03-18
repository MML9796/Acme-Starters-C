
package acme.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import acme.validators.AuditReportValidator;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AuditReportValidator.class)

public @interface ValidAuditReport {

	// Standard validation properties------------------------------------------

	String message() default "";

	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};

}
